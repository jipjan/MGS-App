package com.example.loisgussenhoven.walkabout.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.controller.DataController;
import com.example.loisgussenhoven.walkabout.controller.GeofenceHandler;
import com.example.loisgussenhoven.walkabout.controller.PinpointObserver;
import com.example.loisgussenhoven.walkabout.controller.RouteController;
import com.example.loisgussenhoven.walkabout.controller.json.Directions;
import com.example.loisgussenhoven.walkabout.model.MapsData;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, Response.Listener<Directions>,
        Response.ErrorListener, GoogleMap.OnMarkerClickListener, PinpointObserver.OnNearbyPinpointListener,
        PinpointObserver.OnSkipPinpointListener {

    private GoogleMap map;
    private ListView list;

    private MapsData data;

    private GeofenceHandler geofence;
    private HashMap<String, Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        data = (MapsData) getIntent().getSerializableExtra("Data");

        if (data == null) {
            data = new MapsData();
            data.isBlindWalls = getIntent().getBooleanExtra("RouteType", true);
        }

        PinpointObserver.addOnNearbyPinpointListener(this);
        PinpointObserver.addOnSkipPinpointListener(this);

        geofence = new GeofenceHandler(this);

        list = findViewById(R.id.route_points_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pinpoint item = (Pinpoint) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MapsActivity.this, InfoPinPointActivity.class);
                intent.putExtra("Pinpoint", item);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        if (data.isBlindWalls)
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
            if (grantResults[1] == -1) {
                Toast.makeText(MapsActivity.this, getString(R.string.no_loc_permissions), Toast.LENGTH_LONG).show();
                onBackPressed();
            }
            else
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

                List<Pinpoint> notVisited = new ArrayList<>();
                for (Pinpoint p : data.currentPoints)
                    if (!p.isVisited()) notVisited.add(p);
                List<LatLng> points = pointsToLatLng(notVisited);
                geofence.populateList(data.currentPoints);
                geofence.start();
                if (loc != null) {
                    LatLng location = new LatLng(loc.getLatitude(), loc.getLongitude());
                    points.add(0, location);
                    map.moveCamera(CameraUpdateFactory.newLatLng(location));
                } else {
                    Toast.makeText(MapsActivity.this, getString(R.string.last_loc_not_found), Toast.LENGTH_LONG).show();
                }
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
        setPoints(DataController.makeInstance(this).allRoutePoints());
    }

    private void addBlindWallsPoints() {
        setPoints(DataController.makeInstance(this).allBlindWallPoints());
    }

    private void setPoints(List<? extends Pinpoint> points) {
        data.currentPoints = points;
        data.selectedPoints = new HashMap<>();
        for (Pinpoint p : points) {
            data.selectedPoints.put(p.toString(), p);
        }
        ArrayAdapter<? extends Pinpoint> adapter = new ArrayAdapter<>(MapsActivity.this, R.layout.pinpoint_list_item, R.id.pinpoint_list_item_text, data.currentPoints);
        list.setAdapter(adapter);

        List<LatLng> latPoints = new ArrayList<>();
        markers = new HashMap<>();
        for (Pinpoint p : points) {
            LatLng point = pinpointToLatLng(p);
            Marker m = map.addMarker(new MarkerOptions().position(point).title(p.toString()));
            if (p.isVisited())
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            markers.put(p.toString(), m);
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
        if (response.routes.size() == 0) return;
        data.directionPoints = response.routes.get(0).overviewPolyline.points;
        List<LatLng> decoded = decodePoly(data.directionPoints);
        map.addPolyline(new PolylineOptions()
                .addAll(decoded)
                .width(12f)
                .color(Color.BLUE)
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        openPinPointInfo(marker.getTitle());
        saveRoute();
        return false;
    }

    public void openPinPointInfo(String name) {
        Intent info = new Intent(MapsActivity.this, InfoPinPointActivity.class);
        info.putExtra("Pinpoint", data.selectedPoints.get(name));
        startActivity(info);
    }

    @Override
    public void onNearbyPinpoint(final String pinpointId) {
        markPinpointAsVisited(pinpointId);
        openPinPointInfo(pinpointId);
    }

    @Override
    public void onSkipPinpoint(String pinpointId) {
        markPinpointAsVisited(pinpointId);
    }

    private void markPinpointAsVisited(final String pinpointId) {
        data.selectedPoints.get(pinpointId).setVisited(true);
        geofence.removeGeofence(pinpointId);
        runOnUiThread(() -> {
                markers.get(pinpointId).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        });
    }

    private void saveRoute() {
        FileOutputStream file;
        ObjectOutputStream stream;
        try {
            file = openFileOutput("route.bin", Context.MODE_PRIVATE);
            stream = new ObjectOutputStream(file);
            stream.writeObject(data);
            stream.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}