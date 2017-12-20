package com.example.loisgussenhoven.walkabout.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.squareup.picasso.Picasso;

public class InfoPinPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pin_point);

        Pinpoint pinpoint = (Pinpoint) getIntent().getSerializableExtra("Pinpoint");

        ImageView image = findViewById(R.id.AIP_IV);
        String url = pinpoint.getImages().get(0);
        Picasso.with(this).load(url).into(image);

        TextView name = findViewById(R.id.AIP_TV_Name);
        TextView author = findViewById(R.id.AIP_TV_Author);
        TextView info = findViewById(R.id.AIP_TV_Info);
        TextView year = findViewById(R.id.AIP_TV_Year);

        name.setText(pinpoint.getName());
        author.setText(pinpoint.getAuthor());
        info.setText(pinpoint.getInformation());
        year.setText(pinpoint.getYear() == 0 ? "" : ""+pinpoint.getYear());

        info.setMovementMethod(new ScrollingMovementMethod());
    }
}
