package com.example.loisgussenhoven.walkabout.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.GeomagneticField;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.controller.DataController;
import com.example.loisgussenhoven.walkabout.controller.GeofenceTransitionIntentService;
import com.example.loisgussenhoven.walkabout.controller.RouteController;
import com.example.loisgussenhoven.walkabout.controller.json.Directions;
import com.example.loisgussenhoven.walkabout.controller.json.Leg;
import com.example.loisgussenhoven.walkabout.controller.json.Polyline;
import com.example.loisgussenhoven.walkabout.model.BlindWallPoint;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.example.loisgussenhoven.walkabout.model.RoutePoint;
import com.example.loisgussenhoven.walkabout.view.OnClickListener;
import com.example.loisgussenhoven.walkabout.view.RoutePointListAdapter;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, Response.Listener<Directions>, Response.ErrorListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private RoutePointListAdapter adapter;
    private HashMap<String, Pinpoint> selectedPoints;
    private boolean blindwalls;
    private GeofencingClient geofence;
    private PendingIntent geofencePending;

    private final int GEOFENCE_RADIUS = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        blindwalls = getIntent().getBooleanExtra("RouteType", true);

        final RecyclerView list = findViewById(R.id.route_points_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter = new RoutePointListAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(MapsActivity.this, InfoPinPointActivity.class);
                t.putExtra("POI", (Pinpoint) adapter.getItems().get(list.indexOfChild(view)));
                startActivity(t);
            }
        }));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        if (blindwalls)
            addBlindWallsPoints();
        else
            addRoutePoints();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        } else {
            whenHasPermissions();
        }

        googleMap.setOnMarkerClickListener(this);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 0) {
            whenHasPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void whenHasPermissions() {
        map.setMyLocationEnabled(true);
        LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location loc) {
                RouteController controller = new RouteController(MapsActivity.this);
                List<LatLng> points = pointsToLatLng(adapter.getItems());
                geofence = LocationServices.getGeofencingClient(MapsActivity.this);
                geofence.addGeofences(buildFences(points), getGeofencePending()).addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Geo", "Success");
                    }
                }).addOnFailureListener(MapsActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Geo", "Fail");
                    }
                });
                controller.getDirections(points, MapsActivity.this, MapsActivity.this);
            }
        });
    }

    private List<LatLng> pointsToLatLng(List<? extends Pinpoint> pinpoints) {
        List<LatLng> toReturn = new ArrayList<>();
        for (Pinpoint p : pinpoints)
            toReturn.add(new LatLng(p.getLatitude(), p.getLongitude()));
        return toReturn;
    }

    private void addRoutePoints() {
        setPoints(new DataController(this).allRoutePoints());
    }

    private void addBlindWallsPoints() {
        setPoints(new DataController(this).allBlindWallPoints());
    }

    private void setPoints(List<? extends Pinpoint> points) {
        selectedPoints = new HashMap<>();
        for (Pinpoint p : points)
            selectedPoints.put(p.getName(), p);

        adapter.setItems(points);
        List<LatLng> latPoints = new ArrayList<>();
        for (Pinpoint p : points) {
            LatLng point = pinpointToLatLng(p);
            map.addMarker(new MarkerOptions().position(point).title(p.getName()));
            latPoints.add(point);
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(points.get(0).getLatitude(), points.get(0).getLongitude())));
    }

    private LatLng pinpointToLatLng(Pinpoint p) {
        return new LatLng(p.getLatitude(), p.getLongitude());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error", error.getMessage());
    }

    @Override
    public void onResponse(Directions response) {
        List<LatLng> directions = decodePoly(response.routes.get(0).overviewPolyline.points);
        map.addPolyline(new PolylineOptions()
                .addAll(directions)
                .width(12f)
                .color(Color.GREEN)
                .geodesic(true)
        );
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }
        return poly;
    }

    private GeofencingRequest buildFences(List<LatLng> input) {
        List<Geofence> fenceList = new ArrayList<>();
        for (LatLng item : input) {
            fenceList.add(new Geofence.Builder().setRequestId(item.toString()).setCircularRegion(item.latitude, item.longitude, GEOFENCE_RADIUS).setExpirationDuration(60 * 60 * 1000).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT).build());
        }

        GeofencingRequest.Builder requestBuilder = new GeofencingRequest.Builder();
        requestBuilder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        requestBuilder.addGeofences(fenceList);
        return requestBuilder.build();
    }

    private PendingIntent getGeofencePending() {
        if (geofencePending == null) {
            Intent it = new Intent(MapsActivity.this, GeofenceTransitionIntentService.class);
            geofencePending = PendingIntent.getService(MapsActivity.this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return geofencePending;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent info = new Intent(MapsActivity.this, InfoPinPointActivity.class);
        info.putExtra("Pinpoint", selectedPoints.get(marker.getTitle()));
        startActivity(info);
        return false;
    }
}