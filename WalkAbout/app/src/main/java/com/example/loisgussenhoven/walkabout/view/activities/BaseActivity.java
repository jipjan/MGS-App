package com.example.loisgussenhoven.walkabout.view.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;

import com.example.loisgussenhoven.walkabout.R;

import java.util.Locale;

/**
 * Created by Jaap-Jan on 18-12-2017.
 */

public class BaseActivity extends AppCompatActivity {

    @NonNull
    String locale = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getApplicationContext().getResources();
        DisplayMetrics display = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(locale));
        res.updateConfiguration(conf, display);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
