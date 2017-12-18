package com.example.loisgussenhoven.walkabout.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;

import org.w3c.dom.Text;

public class InfoActivity extends BaseActivity {

    Pinpoint infoItem;

    ImageView image;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showOptionsMenu = false;
        setContentView(R.layout.activity_info);

        infoItem = (Pinpoint) getIntent().getExtras().get("POI");

        image = findViewById(R.id.info_imageview);
        info = findViewById(R.id.info_textview);



        info.setText(infoItem.getInformation());
    }
}
