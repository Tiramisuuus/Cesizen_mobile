package com.example.cesizen_mobile.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cesizen_mobile.R; // ✅ IMPORT DU BON PACKAGE R

import java.util.List;

public class RessourceAdapter extends BaseAdapter {
    private Context context;
    private List<Resource> resources;

    public RessourceAdapter(Context context, List<Resource> resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return resources.size();
    }

    @Override
    public Object getItem(int i) {
        return resources.get(i);
    }

    @Override
    public long getItemId(int i) {
        return resources.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource, parent, false);
        TextView title = view.findViewById(R.id.resourceTitle);
        TextView desc = view.findViewById(R.id.resourceDesc);

        Resource res = resources.get(i);
        title.setText(res.getName());
        desc.setText(res.getDescription());

        return view;
    }
}
