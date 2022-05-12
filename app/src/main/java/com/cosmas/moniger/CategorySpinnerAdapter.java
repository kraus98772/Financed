package com.cosmas.moniger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CategorySpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> categories;

    public CategorySpinnerAdapter(Context mContext, ArrayList<String> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories != null ? categories.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        TextView categoryView = viewGroup.findViewById(R.id.category);

        categoryView.setText(categories.get(i));
        return rootView;
    }
}
