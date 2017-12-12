package com.example.loisgussenhoven.walkabout.model;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class Route {
    private String name, information;

    public Route(String name, String information) {
        this.name = name;
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }
}
