package com.example.loisgussenhoven.walkabout.model;

import com.example.loisgussenhoven.walkabout.controller.DataController;

import java.util.List;

/**
 * Created by Jaap-Jan on 14-12-2017.
 */

public class BlindWallPoint extends Pinpoint {
    private String author;
    private int year;

    public BlindWallPoint(int id, String nameNL, String nameEng, String informationNL, String informationEng, float longitude, float latitude, boolean visited, String author, int year) {
        super(id, nameNL, nameEng, informationNL, informationEng, longitude, latitude, visited);
        this.author = author;
        this.year = year;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public List<String> getImages() {
        return DataController.getInstance().getImagesByBlindWallId(getId());
    }
}
