package com.example.loisgussenhoven.walkabout.model;

/**
 * Created by Jaap-Jan on 14-12-2017.
 */

public class RoutePoint extends Pinpoint {

    private int nmr;

    public RoutePoint(int nmr, String name, String information, float longitude, float latitude, boolean visited) {
        super(name, information, longitude, latitude, visited);
        this.nmr = nmr;
    }

    public int getNmr() {
        return nmr;
    }
}
