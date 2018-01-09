package com.example.loisgussenhoven.walkabout.view.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.model.MapsData;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    ImageButton IB_BW;
    ImageButton IB_HK;
    TextView name;
    Button BTN_start;
    ImageButton BTN_info;

    Boolean selectedBlindwalls = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MapsData data = loadRoute();
        if (data != null) {
            DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case DialogInterface.BUTTON_POSITIVE:
                            startRoute(data);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            deleteFile("route.bin");
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.route_in_progress))
                    .setPositiveButton(getString(R.string.yes), onClick)
                    .setNegativeButton(getString(R.string.no), onClick).show();
        }

        List spinnerArray = new ArrayList<String>();
        spinnerArray.add("Nederlands");
        spinnerArray.add("English");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinner);
        sItems.setAdapter(adapter);
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setLanguage(i != 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        name = findViewById(R.id.AM_TV_Name);

        IB_BW = findViewById(R.id.AM_IB_RouteBW);
        IB_HK = findViewById(R.id.AM_IB_RouteHK);

        IB_HK.setColorFilter(Color.argb(150, 0, 0, 0));
        IB_HK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IB_HK.setColorFilter(Color.argb(0, 0, 0, 0));
                IB_BW.setColorFilter(Color.argb(150, 0, 0, 0));
                name.setText("Historische kilometer");
                selectedBlindwalls = false;
            }
        });

        IB_BW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IB_HK.setColorFilter(Color.argb(150, 0, 0, 0));
                IB_BW.setColorFilter(Color.argb(0, 0, 0, 0));
                name.setText("Blind Walls");
                selectedBlindwalls = true;
            }
        });
        BTN_start = findViewById(R.id.AM_BTN_Start);
        BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRoute(null);
            }
        });

        BTN_info = findViewById(R.id.AM_IB_Info);
        BTN_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRoute(null);
            }
        });
    }

    private void startRoute(MapsData data) {
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        i.putExtra("RouteType", selectedBlindwalls);
        i.putExtra("Data", data);
        startActivity(i);
    }

    private MapsData loadRoute() {
        FileInputStream file;
        ObjectInputStream stream;

        MapsData activity = null;
        try {
            file = openFileInput("route.bin");
            stream = new ObjectInputStream(file);
            activity = (MapsData) stream.readObject();
            stream.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }
}


