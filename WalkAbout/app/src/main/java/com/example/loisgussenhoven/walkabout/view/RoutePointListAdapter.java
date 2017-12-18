package com.example.loisgussenhoven.walkabout.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loisgussenhoven.walkabout.R;
import com.example.loisgussenhoven.walkabout.model.Pinpoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaap-Jan on 15-12-2017.
 */

public class RoutePointListAdapter<T extends Pinpoint> extends RecyclerView.Adapter<RoutePointListViewHolder> {

    // Holy shit waarom is Java zo ongelooflijk ruk en moet je hier generics voor gebruiken en is er geen readonly collection ofzo =.="
    List<T> items = new ArrayList<>();

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RoutePointListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinpoint_list_item, parent, false);
        return new RoutePointListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoutePointListViewHolder holder, int position) {
        T p = items.get(position);
        if (p.getName().equals(""))
            holder.pointName.setText(p.getInformation());
        else
            holder.pointName.setText(p.getName().trim());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
