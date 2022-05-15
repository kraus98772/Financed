package com.cosmas.moniger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CategorySpinnerAdapter extends BaseAdapter {

    private String[] categories = {"Food", "Drinks", "Services", "Bills", "Income", "Other"};

    public CategorySpinnerAdapter() {
    }

    @Override
    public int getCount() {
        return categories != null ? categories.length : 0;
    }

    @Override
    public Object getItem(int i) {
        return categories[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        TextView category = rootView.findViewById(R.id.category_name);

        category.setText(categories[i]);
        return rootView;
    }
}
