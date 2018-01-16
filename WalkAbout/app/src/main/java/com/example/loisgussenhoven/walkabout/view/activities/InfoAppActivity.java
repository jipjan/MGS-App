package com.example.loisgussenhoven.walkabout.view.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.loisgussenhoven.walkabout.R;

public class InfoAppActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideOptionsMenu(true);
        setContentView(R.layout.activity_info_app);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
