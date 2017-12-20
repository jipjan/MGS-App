package com.example.loisgussenhoven.walkabout.controller;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaap-Jan on 20-12-2017.
 */

public class GeofenceHandler implements OnCompleteListener<Void> {

    Context context;

    List<Geofence> points;
    PendingIntent pendingGeofenceIntent;
    GeofencingClient geofencingClient;

    final float RADIUS = 50f;
    final long EXPIRATION = 60 * 60 * 1000;

    public GeofenceHandler(Context c) {
        context = c;
        points = new ArrayList<>();
        geofencingClient = LocationServices.getGeofencingClient(c);
    }

    public void setPoints(List<? extends Pinpoint> newpoints) {
        points = new ArrayList<>();
        for (Pinpoint item : newpoints)
            points.add(new Geofence.Builder()
                    .setRequestId(item.getName())
                    .setCircularRegion(item.getLatitude(), item.getLongitude(), RADIUS)
                    .setExpirationDuration(EXPIRATION)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
    }

    @SuppressLint("MissingPermission")
    public void addFences() {
        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent()).addOnCompleteListener(this);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(points);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (pendingGeofenceIntent != null) {
            return pendingGeofenceIntent;
        }
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        Log.d("Geofences added", "Success");
    }
}
