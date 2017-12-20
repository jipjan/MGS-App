package com.example.loisgussenhoven.walkabout.model;

import com.example.loisgussenhoven.walkabout.controller.DataController;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jaap-Jan on 14-12-2017.
 */

public class RoutePoint extends Pinpoint {

    public RoutePoint(int id, String nameNL, String nameEng, String informationNL, String informationEng, float longitude, float latitude, boolean visited) {
        super(id, nameNL, nameEng, informationNL, informationEng, longitude, latitude, visited);
    }

    @Override
    public List<String> getImages() {
        return DataController.getInstance().getImagesByPointId(getId());
    }
}
