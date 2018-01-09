package com.example.loisgussenhoven.walkabout.model;

import com.example.loisgussenhoven.walkabout.view.activities.BaseActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public abstract class Pinpoint implements Serializable {
    private int id;
    private String nameNL, nameEng, informationNL, informationEng;
    private float longitude, latitude;
    private boolean visited;

    public Pinpoint(int id, String nameNL, String nameEng, String informationNL, String informationEng, float longitude, float latitude, boolean visited) {
        this.id = id;
        this.nameNL = nameNL;
        this.nameEng = nameEng;
        this.informationNL = informationNL;
        this.informationEng = informationEng;
        this.longitude = longitude;
        this.latitude = latitude;
        this.visited = visited;
    }

    public String getNameNL() {
        return nameNL;
    }

    public String getNameEng() {
        return nameEng;
    }

    public String getInformationNL() {
        return informationNL;
    }

    public String getInformationEng() {
        return informationEng;
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
        return BaseActivity.english ? getNameEng() : getNameNL();
    }
}

