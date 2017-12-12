package com.example.loisgussenhoven.walkabout.controller;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.loisgussenhoven.walkabout.model.Pinpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class DataController extends SQLiteOpenHelper {

    private List<Pinpoint> session;
    private HashMap<String, String> routes;


    public DataController() {

    }


    public String getRouteInformation(String name) {
        return routes.get(name);
    }

    public void setNewSession() {
        session = new ArrayList<Pinpoint>();
    }

    public void addPinpointToSession(Pinpoint p) {
        session.add(p);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
