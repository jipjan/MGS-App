package com.example.loisgussenhoven.walkabout.view.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List spinnerArray = new ArrayList<String>();
        spinnerArray.add("Nederlands");
        spinnerArray.add("English");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        String selected = sItems.getSelectedItem().toString();
        config = new Configuration(getResources().getConfiguration());
        if (selected.equals("English")) {
            config.locale = Locale.ENGLISH;
        }
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

            }
        });

        IB_BW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IB_HK.setColorFilter(Color.argb(150, 0, 0, 0));
                IB_BW.setColorFilter(Color.argb(0, 0, 0, 0));
                name.setText("Blind Walls");

            }
        });
        BTN_start = findViewById(R.id.AM_BTN_Start);
        BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: map van juiste route ophalen
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

        BTN_info = findViewById(R.id.AM_IB_Info);
        BTN_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}


