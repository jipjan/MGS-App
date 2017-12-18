package com.example.loisgussenhoven.walkabout.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.controller.DataController;
import com.example.loisgussenhoven.walkabout.controller.RouteController;
import com.example.loisgussenhoven.walkabout.controller.json.Directions;
import com.example.loisgussenhoven.walkabout.controller.json.Leg;
import com.example.loisgussenhoven.walkabout.model.BlindWallPoint;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.example.loisgussenhoven.walkabout.model.RoutePoint;
import com.example.loisgussenhoven.walkabout.view.RoutePointListAdapter;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, Response.Listener<Directions>, Response.ErrorListener {

    private GoogleMap map;
    private RoutePointListAdapter adapter;

    private boolean blindwalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        blindwalls = getIntent().getBooleanExtra("RouteType", true);

        RecyclerView list = findViewById(R.id.route_points_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter = new RoutePointListAdapter());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        if (blindwalls)
            generateBlindWallsRoute();
        else
            addRoutePoints();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        } else {
            whenHasPermissions();
        }

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        //googleMap.getUiSettings().setZoomControlsEnabled(true);

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
                controller.getDirections(points, MapsActivity.this, MapsActivity.this);
            }
        });
    }

    private List<LatLng> pointsToLatLng(List<Pinpoint> pinpoints) {
        List<LatLng> toReturn = new ArrayList<>();
        for (Pinpoint p : pinpoints)
            toReturn.add(new LatLng(p.getLatitude(), p.getLongitude()));
        return toReturn;
    }

    private void addRoutePoints() {
        List<RoutePoint> points = new DataController(this).allRoutePoints();
        adapter.setItems(points);

        List latPoints = new ArrayList();
        for (RoutePoint p : points) {
            LatLng point = pinpointToLatLng(p);
            map.addMarker(new MarkerOptions().position(point).title(p.getName()));
            latPoints.add(point);
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(points.get(0).getLatitude(), points.get(0).getLongitude())));
    }

    private LatLng pinpointToLatLng(Pinpoint p) {
        return new LatLng(p.getLatitude(), p.getLongitude());
    }

    private void generateBlindWallsRoute() {
        List<BlindWallPoint> points = new DataController(this).allBlindWallPoints();
        for (BlindWallPoint p : points){
            LatLng point = new LatLng(p.getLatitude(), p.getLongitude());
            map.addMarker(new MarkerOptions().position(point).title(p.getName()));
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(points.get(0).getLatitude(), points.get(0).getLongitude())));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error", error.getMessage());
    }

    @Override
    public void onResponse(Directions response) {
        Log.d("Success", "Getting directions success");
        List<LatLng> directions = new ArrayList<>();
        for (Leg l : response.routes.get(0).legs){
            directions.add(new LatLng(l.startLocation.lat, l.startLocation.lng));
            directions.add(new LatLng(l.endLocation.lat, l.endLocation.lng));
        }
        addDirectionLines(directions);
    }

    private void addDirectionLines(List<LatLng> points) {
        PolylineOptions polyLines = new PolylineOptions();
        polyLines.addAll(points);
        polyLines.width(8f);
        polyLines.color(Color.RED);
        polyLines.geodesic(true);
        map.addPolyline(polyLines);
    }
}