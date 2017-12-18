package com.example.loisgussenhoven.walkabout.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.loisgussenhoven.walkabout.R;

/**
 * Created by Jaap-Jan on 18-12-2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
