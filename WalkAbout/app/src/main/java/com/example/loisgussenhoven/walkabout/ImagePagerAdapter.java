package com.example.loisgussenhoven.walkabout;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by Jaap-Jan on 12-12-2017.
 */

public class ImagePagerAdapter extends PagerAdapter {

    List<?> items;

    public ImagePagerAdapter(List<?> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // Not implemented, prolly not necessary for our use case.
        return true;
    }
}
