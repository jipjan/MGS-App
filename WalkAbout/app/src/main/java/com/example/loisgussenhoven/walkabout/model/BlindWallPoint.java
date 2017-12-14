package com.example.loisgussenhoven.walkabout.model;

import java.util.List;

/**
 * Created by Jaap-Jan on 14-12-2017.
 */

public class BlindWallPoint extends Pinpoint {
    private String author;
    private int year;
    private List<Integer> images;

    public BlindWallPoint(String name, String information, float longitude, float latitude, boolean visited) {
        super(name, information, longitude, latitude, visited);
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
}
