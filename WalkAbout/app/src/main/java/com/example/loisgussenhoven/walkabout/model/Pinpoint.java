package com.example.loisgussenhoven.walkabout.model;

import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public abstract class Pinpoint {
    private String name, information;
    private float longitude, latitude;
    private boolean visited;

    public Pinpoint(String name, String information, float longitude, float latitude, boolean visited) {
        this.name = name;
        this.information = information;
        this.longitude = longitude;
        this.latitude = latitude;
        this.visited = visited;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
