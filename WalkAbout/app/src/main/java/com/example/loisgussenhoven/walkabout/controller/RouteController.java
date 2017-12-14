package com.example.loisgussenhoven.walkabout.controller;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class RouteController {

    private static final String BASE_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String API_KEY = "AIzaSyA5kN2wxpBXQQ55yVjUdItOAdGFO9dZZGY";

    public static void getDirections(LatLng start, LatLng dest) {
        String requestUrl = BASE_DIRECTION_URL + "origin=" + start.latitude + "," + start.longitude + "&destination=" + dest.latitude + "," + dest.longitude + "&key=" + API_KEY;
        ;
    }
}
