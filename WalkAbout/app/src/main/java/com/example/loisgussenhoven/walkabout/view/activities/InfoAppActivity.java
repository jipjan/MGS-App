package com.example.loisgussenhoven.walkabout.view.activities;

import android.os.Bundle;

import com.example.loisgussenhoven.walkabout.R;

public class InfoAppActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showOptionsMenu = false;
        setContentView(R.layout.activity_info_app);
    }
}
