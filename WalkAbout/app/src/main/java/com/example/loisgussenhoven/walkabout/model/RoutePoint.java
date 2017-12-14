package com.example.loisgussenhoven.walkabout.model;

/**
 * Created by Jaap-Jan on 14-12-2017.
 */

public class RoutePoint extends Pinpoint {

    public RoutePoint(int id, String name, String information, float longitude, float latitude, boolean visited) {
        super(id, name, information, longitude, latitude, visited);
    }
}
