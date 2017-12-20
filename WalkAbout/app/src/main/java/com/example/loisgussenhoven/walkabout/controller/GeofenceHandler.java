package com.example.loisgussenhoven.walkabout.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loisgussenhoven.walkabout.OnGeofenceEvent;
import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.example.loisgussenhoven.walkabout.view.activities.MainActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jaap-Jan on 20-12-2017.
 */

public class GeofenceHandler implements OnCompleteListener<Void> {

    Context context;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private enum PendingGeofenceTask {
        ADD, REMOVE, NONE
    }
    private GeofencingClient mGeofencingClient;
    private ArrayList<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    private PendingGeofenceTask mPendingGeofenceTask = PendingGeofenceTask.NONE;

    final float RADIUS = 20f;
    final long EXPIRATION = 60 * 60 * 1000;

    public GeofenceHandler(Context c, OnGeofenceEvent eventHandler) {
        context = c;
        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<>();
        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;
        // Get the geofences used. Geofence data is hard coded in this sample.
        mGeofencingClient = LocationServices.getGeofencingClient(c);
        GeofenceTransitionsIntentService.onGeofenceEvent = eventHandler;
    }

    public void populateList(List<? extends Pinpoint> newpoints) {
        for (Pinpoint item : newpoints)
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(item.toString())
                    .setCircularRegion(item.getLatitude(), item.getLongitude(), RADIUS)
                    .setExpirationDuration(EXPIRATION)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
    }

    public void start() {
        if (!checkPermissions()) {
            Log.d("Shit", "Lacking permissions");
        } else {
            mPendingGeofenceTask = PendingGeofenceTask.ADD;
            performPendingGeofenceTask();
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    @SuppressLint("MissingPermission")
    private void addGeofences() {
        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                .addOnCompleteListener(this);
    }

    private void removeGeofences() {
        mGeofencingClient.removeGeofences(getGeofencePendingIntent()).addOnCompleteListener(this);
    }

    private void performPendingGeofenceTask() {
        if (mPendingGeofenceTask == PendingGeofenceTask.ADD) {
            addGeofences();
        } else if (mPendingGeofenceTask == PendingGeofenceTask.REMOVE) {
            removeGeofences();
        }
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        mPendingGeofenceTask = PendingGeofenceTask.NONE;
        if (task.isSuccessful()) {

        } else {
            Log.w("Shit", "Task unsuccessful");
        }
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
}
