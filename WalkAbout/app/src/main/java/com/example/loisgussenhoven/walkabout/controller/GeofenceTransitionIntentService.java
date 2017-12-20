package com.example.loisgussenhoven.walkabout.controller;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.loisgussenhoven.walkabout.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Jaap-Jan on 20-12-2017.
 */

public class GeofenceTransitionIntentService extends IntentService {
    public GeofenceTransitionIntentService() {
        super("GeofenceService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        ;
    }
}
