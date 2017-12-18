package com.example.loisgussenhoven.walkabout.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loisgussenhoven.walkabout.R;

/**
 * Created by Jaap-Jan on 15-12-2017.
 */

public class RoutePointListViewHolder extends RecyclerView.ViewHolder {
    public TextView pointName;

    public RoutePointListViewHolder(View itemView) {
        super(itemView);
        pointName = itemView.findViewById(R.id.pinpoint_list_item_text);
    }
}
