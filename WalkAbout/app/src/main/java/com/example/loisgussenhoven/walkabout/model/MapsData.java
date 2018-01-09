package com.example.loisgussenhoven.walkabout.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaap-Jan on 9-1-2018.
 */

public class MapsData implements Serializable {
    public boolean isBlindWalls;
    public String directionPoints;
    public List<? extends Pinpoint> currentPoints;
    public HashMap<String, Pinpoint> selectedPoints;
}
