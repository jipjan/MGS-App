package com.example.loisgussenhoven.walkabout.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.loisgussenhoven.walkabout.controller.json.Directions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class RouteController {

    private static final String BASE_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String API_KEY = "AIzaSyA5kN2wxpBXQQ55yVjUdItOAdGFO9dZZGY";

    private RequestQueue queue;

    public RouteController(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getDirections(LatLng start, LatLng dest, Response.Listener<Directions> onSucces, Response.ErrorListener onFail) {
        String requestUrl = BASE_DIRECTION_URL + "origin=" + start.latitude + "," + start.longitude + "&destination=" + dest.latitude + "," + dest.longitude + "&key=" + API_KEY;
        GsonRequest<Directions> request = new GsonRequest<>(Request.Method.GET, requestUrl, Directions.class, onSucces, onFail);
        queue.add(request);
    }
}
