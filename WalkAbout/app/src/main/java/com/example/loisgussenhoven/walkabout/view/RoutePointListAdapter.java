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
    List<? extends Pinpoint> items = new ArrayList<>();
    public List<? extends Pinpoint> getItems() {
        return items;
    }

    private View.OnClickListener mListener;

    public RoutePointListAdapter(View.OnClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<? extends Pinpoint> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RoutePointListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinpoint_list_item, parent, false);
        RoutePointListViewHolder holder = new RoutePointListViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RoutePointListViewHolder holder, int position) {
        Pinpoint p = items.get(position);
        holder.pointName.setText(p.getName().trim());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
