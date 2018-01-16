package com.example.loisgussenhoven.walkabout.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.loisgussenhoven.walkabout.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author CoenB95
 */

public class PinpointAdapter extends BaseAdapter {

	private SharedPreferences preferences;
	private List<Pinpoint> pinpoints;

	public PinpointAdapter(Context context, Collection<? extends Pinpoint> pinpoints) {
		this.pinpoints = new ArrayList<>(pinpoints);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Override
	public int getCount() {
		return pinpoints.size();
	}

	@Override
	public Object getItem(int position) {
		return pinpoints.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pinpoint_list_item, parent, false);

		Pinpoint pinpoint = pinpoints.get(position);
		boolean isEnglish = preferences.getString("language", "en").equals("en");

		TextView name = convertView.findViewById(R.id.pinpoint_list_item_text);
		name.setText(isEnglish ? pinpoint.getNameEng() : pinpoint.getNameNL());

		return convertView;
	}
}
