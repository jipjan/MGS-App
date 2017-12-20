package com.example.loisgussenhoven.walkabout.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.loisgussenhoven.walkabout.R;

public class InfoRouteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean blind = getIntent().getBooleanExtra("RouteType", true);
        TextView title = findViewById(R.id.AIR_TV_Name);
        TextView desc = findViewById(R.id.AIR_TV_Info);

        if (blind) {
            title.setText("Blind Walls Route");
            desc.setText(getResources().getText(R.string.desc_blind_walls));
        } else {
            title.setText("Historische Route");
            desc.setText(getResources().getText(R.string.desc_historische_route));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
