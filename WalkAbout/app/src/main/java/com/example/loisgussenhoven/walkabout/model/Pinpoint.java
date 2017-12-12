package com.example.loisgussenhoven.walkabout.model;

import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class Pinpoint {
    private String name, information, author;
    private int year;
    private List<Integer> images;
    private long longitude, latitude;
    private boolean hasBeenVisitedOrSkipped;

    public Pinpoint(String name, String information, String author, int year, List<Integer> images, long longitude, long latitude, boolean hasBeenVisitedOrSkipped) {
        this.name = name;
        this.information = information;
        this.author = author;
        this.year = year;
        this.images = images;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hasBeenVisitedOrSkipped = hasBeenVisitedOrSkipped;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public List<Integer> getImages() {
        return images;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public boolean isHasBeenVisitedOrSkipped() {
        return hasBeenVisitedOrSkipped;
    }

    public void setHasBeenVisitedOrSkipped(boolean hasBeenVisitedOrSkipped) {
        this.hasBeenVisitedOrSkipped = hasBeenVisitedOrSkipped;
    }
}
