package com.cosmas.moniger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CurrencySpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> currencies;

    public CurrencySpinnerAdapter(Context mContext, ArrayList<String> currencies) {
        this.mContext = mContext;
        this.currencies = currencies;
    }

    @Override
    public int getCount() {
        return currencies != null ? currencies.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return currencies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.currency_item, viewGroup, false);
        TextView currencyName = rootView.findViewById(R.id.currency_name);

        currencyName.setText(currencies.get(i));
        return rootView;
    }
}
