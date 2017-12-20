package com.example.loisgussenhoven.walkabout;

/**
 * Created by Jaap-Jan on 20-12-2017.
 */

public interface OnGeofenceEvent {
    void onEnter(String name);
    void onExit(String name);
}
