package com.example.loisgussenhoven.walkabout.view.activities;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.List;

public class InfoPinPointActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pin_point);

        Picasso.with(this).setLoggingEnabled(true);

        Pinpoint pinpoint = (Pinpoint) getIntent().getSerializableExtra("Pinpoint");

        final ImageView image = findViewById(R.id.AIP_IV);
        final List<String> images = pinpoint.getImages();
        if (images != null && images.size() > 0) {
            final String url = images.get(0);
            if (url.startsWith("img")) {
                int id = getResId(url);
                image.setImageResource(id);
            }
            else
                Picasso.with(this).load(url).into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        if (images.size() > 1)
                            Picasso.with(InfoPinPointActivity.this).load(images.get(1)).into(image);
                    }
                });
        }

        TextView name = findViewById(R.id.AIP_TV_Name);
        TextView author = findViewById(R.id.AIP_TV_Author);
        TextView info = findViewById(R.id.AIP_TV_Info);
        TextView year = findViewById(R.id.AIP_TV_Year);

        if (english) {
            name.setText(pinpoint.getNameEng());
            info.setText(pinpoint.getInformationEng());
        } else {
            name.setText(pinpoint.getNameNL());
            info.setText(pinpoint.getInformationNL());
        }

        author.setText(pinpoint.getAuthor());
        year.setText(pinpoint.getYear() == 0 ? "" : ""+pinpoint.getYear());

        info.setMovementMethod(new ScrollingMovementMethod());
    }

    private int getResId(String resName) {
        try {
            return getResources().getIdentifier(resName, "drawable", getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
