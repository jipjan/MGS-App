package com.example.loisgussenhoven.walkabout.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.loisgussenhoven.walkabout.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

         @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Language Spinner
                 List spinnerArray =  new ArrayList<String>();
                 spinnerArray.add("Nederlands");
                 spinnerArray.add("English");

                 ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                         this, android.R.layout.simple_spinner_item, spinnerArray);

                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 Spinner sItems = findViewById(R.id.spinner);
                 sItems.setAdapter(adapter);

                 String selected = sItems.getSelectedItem().toString();
                 if (selected.equals("English")) {
                     //TODO: change app language
             }
             //Language Spinner
             }
        }




