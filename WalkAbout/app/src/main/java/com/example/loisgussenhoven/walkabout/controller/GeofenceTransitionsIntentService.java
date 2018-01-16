package com.example.loisgussenhoven.walkabout.controller;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by Jaap-Jan on 20-12-2017.
 */

public class GeofenceTransitionsIntentService extends IntentService {

    private static final String TAG = "GeofenceTransitionsIS";

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public GeofenceTransitionsIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d("Fencing", "Spaced 'm");
            return;
        }

        int transType = geofencingEvent.getGeofenceTransition();

        List<Geofence> triggered = geofencingEvent.getTriggeringGeofences();

        if (transType == 1) {
            for (Geofence g : triggered) {
                PinpointObserver.notifyNearbyPinpoint(g.getRequestId());
            }
        } else if (transType == 2) {
            // er uit

        }
    }
}

