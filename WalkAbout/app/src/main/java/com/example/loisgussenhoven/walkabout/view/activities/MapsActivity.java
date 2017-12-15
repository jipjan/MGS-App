package com.example.loisgussenhoven.walkabout.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.controller.DataController;
import com.example.loisgussenhoven.walkabout.controller.RouteController;
import com.example.loisgussenhoven.walkabout.controller.json.Directions;
import com.example.loisgussenhoven.walkabout.model.BlindWallPoint;
import com.example.loisgussenhoven.walkabout.model.RoutePoint;
import com.example.loisgussenhoven.walkabout.view.RoutePointListAdapter;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Response.Listener<Directions>, Response.ErrorListener {

    private GoogleMap map;
    private RoutePointListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        } else {
            whenHasPermissions();
        }

        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        //googleMap.getUiSettings().setZoomControlsEnabled(true);

        addRoutePoints(googleMap);
        //generateBlindWallsRoute(googleMap);

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
                controller.getDirections(new LatLng(loc.getLatitude(), loc.getLongitude()), new LatLng(51.5839d, 4.77735d), MapsActivity.this, MapsActivity.this);
            }
        });
    }

    private void addRoutePoints(GoogleMap googleMap) {
        List<RoutePoint> points = new DataController(this).allRoutePoints();
        adapter.setItems(points);
        for (RoutePoint p : points) {
            LatLng point = new LatLng(p.getLatitude(), p.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(point).title(p.getName()));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(points.get(0).getLatitude(), points.get(0).getLongitude())));
    }

    private void generateBlindWallsRoute(GoogleMap googleMap) {
        List<BlindWallPoint> points = new DataController(this).allBlindWallPoints();
        for (BlindWallPoint p : points){
            LatLng point = new LatLng(p.getLatitude(), p.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(point).title(p.getName()));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(points.get(0).getLatitude(), points.get(0).getLongitude())));
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Directions response) {

    }
}