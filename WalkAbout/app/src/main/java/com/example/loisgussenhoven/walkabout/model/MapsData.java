package com.example.loisgussenhoven.walkabout.model;

import com.example.loisgussenhoven.walkabout.controller.GeofenceHandler;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaap-Jan on 9-1-2018.
 */

public class MapsData implements Serializable {
    public boolean isBlindWalls;
    public List<LatLng> directions;
    public GeofenceHandler geofence;
    public List<? extends Pinpoint> currentPoints;
    public HashMap<String, Pinpoint> selectedPoints;
    public HashMap<String, Marker> markers;
}
