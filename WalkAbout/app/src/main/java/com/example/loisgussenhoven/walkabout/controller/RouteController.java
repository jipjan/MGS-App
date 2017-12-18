package com.example.loisgussenhoven.walkabout.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.loisgussenhoven.walkabout.controller.json.Directions;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

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

    public void getDirections(List<LatLng> points, Response.Listener<Directions> onSucces, Response.ErrorListener onFail) {
        if (points.size() < 2) return;
        for (List<LatLng> subPoints : listOfParts(points, 25))
            batchDirectionRequest(subPoints, onSucces, onFail);
    }

    private void batchDirectionRequest(List<LatLng> points, Response.Listener<Directions> onSucces, Response.ErrorListener onFail) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(BASE_DIRECTION_URL);
        urlBuilder.append("origin="+ latLngToString(points.get(0)));
        urlBuilder.append("&destination=" + latLngToString(points.get(points.size() - 1)));
        if (points.size() > 2) {
            urlBuilder.append("&waypoints=optimize:true|");
            urlBuilder.append(latLngToString(points.get(1)));
            for (int i = 2; i < points.size() - 1; i++)
                urlBuilder.append("|" + latLngToString(points.get(i)));
        }
        urlBuilder.append("&mode=walking");
        urlBuilder.append("&key=" + API_KEY);

        GsonRequest<Directions> request = new GsonRequest<>(Request.Method.GET, urlBuilder.toString(), Directions.class, onSucces, onFail);
        queue.add(request);
    }

    private static <T> List<List<T>> listOfParts(List<T> list, int partSize) {
        List<List<T>> toReturn = new ArrayList<>();
        for (int i = 0; i < list.size(); i += partSize) {
            if (i == 0)
                toReturn.add(new ArrayList<>(list.subList(i, Math.min(list.size(), i + partSize))));
            else
                toReturn.add(new ArrayList<>(list.subList(i - 1, Math.min(list.size(), i + partSize - 1))));
        }
        return toReturn;
    }

    private static String latLngToString(LatLng p) {
        return p.latitude + "," + p.longitude;
    }
}
