package com.example.cesizen_mobile.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cesizen_mobile.R;

import java.util.List;

public class TrackerAdapter extends BaseAdapter {
    private Context context;
    private List<EmotionTracker> trackerList;

    public TrackerAdapter(Context context, List<EmotionTracker> trackerList) {
        this.context = context;
        this.trackerList = trackerList;
    }

    @Override
    public int getCount() {
        return trackerList.size();
    }

    @Override
    public Object getItem(int i) {
        return trackerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return trackerList.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tracker, parent, false);

        TextView name = view.findViewById(R.id.trackerName);
        TextView desc = view.findViewById(R.id.trackerDesc);
        TextView date = view.findViewById(R.id.trackerDate);

        EmotionTracker tracker = trackerList.get(i);
        name.setText(tracker.getName());
        desc.setText(tracker.getDescription());
        date.setText("Le " + tracker.getCreatedAt());

        return view;
    }
}
