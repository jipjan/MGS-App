package com.example.loisgussenhoven.walkabout.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public abstract class Pinpoint implements Serializable {
    private int id;
    private String name, information;
    private float longitude, latitude;
    private boolean visited;

    public Pinpoint(int id, String name, String information, float longitude, float latitude, boolean visited) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.longitude = longitude;
        this.latitude = latitude;
        this.visited = visited;
    }

    public String getAuthor() {
        return "";
    }

    public int getYear() {
        return 0;
    }

    public abstract List<String> getImages();

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return getName();
    }
}
