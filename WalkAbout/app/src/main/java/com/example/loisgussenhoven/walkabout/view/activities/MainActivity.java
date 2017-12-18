package com.example.loisgussenhoven.walkabout.view.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loisgussenhoven.walkabout.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends BaseActivity {

    ImageButton IB_BW;
    ImageButton IB_HK;
    Configuration config;
    TextView name;
    Button BTN_start;
    ImageButton BTN_info;

    Boolean selectedBlindwalls = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if (i == 0)
                    locale = "nl";
                else
                    locale = "en";
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
                //TODO: map van juiste route ophalen
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("RouteType", selectedBlindwalls);
                startActivity(i);
            }
        });

        BTN_info = findViewById(R.id.AM_IB_Info);
        BTN_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(i);
            }
        });

    }
}


