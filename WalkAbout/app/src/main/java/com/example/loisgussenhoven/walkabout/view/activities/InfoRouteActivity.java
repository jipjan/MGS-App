package com.example.loisgussenhoven.walkabout.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.loisgussenhoven.walkabout.R;

public class InfoRouteActivity extends BaseActivity {

    public static final String EXTRA_DESCRITION = "extra-description";
    public static final String EXTRA_TITLE = "extra-title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String description = getIntent().getStringExtra(EXTRA_DESCRITION);
        TextView titleView = findViewById(R.id.AIR_TV_Name);
        TextView descriptionView = findViewById(R.id.AIR_TV_Info);
        titleView.setText(title);
        descriptionView.setText(description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
