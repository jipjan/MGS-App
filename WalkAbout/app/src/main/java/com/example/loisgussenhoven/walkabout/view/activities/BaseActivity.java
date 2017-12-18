package com.example.loisgussenhoven.walkabout.view.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.loisgussenhoven.walkabout.R;

import java.util.Locale;

/**
 * Created by Jaap-Jan on 18-12-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @NonNull
    static boolean english;

    boolean showOptionsMenu = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getApplicationContext().getResources();
        DisplayMetrics display = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(english ? "en" : "nl"));
        res.updateConfiguration(conf, display);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showOptionsMenu)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_help:
                Intent i = new Intent(this, InfoActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    void setLanguage(boolean english) {
        if (this.english != english) {
            this.english = english;
            recreate();
        }
    }
}
