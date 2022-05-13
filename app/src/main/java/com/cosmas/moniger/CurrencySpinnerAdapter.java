package com.cosmas.moniger;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class CurrencySpinnerAdapter extends BaseAdapter {

    private final String[] currencyNames;
    private final String[] currencySymbols;

    public CurrencySpinnerAdapter(String[] currencyNames, String[] currencySymbols) {
        this.currencyNames = currencyNames;
        this.currencySymbols = currencySymbols;
    }

    @Override
    public int getCount() {
        return currencyNames != null ? currencyNames.length : 0;
    }

    @Override
    public Object getItem(int i) {
        return currencyNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.currency_item, viewGroup, false);
        TextView currencyName = rootView.findViewById(R.id.currency_name);
        TextView currencySymbol = rootView.findViewById(R.id.currency_symbol);

        currencyName.setText(currencyNames[i]);
        currencySymbol.setText(currencySymbols[i]);
        return rootView;
    }
}
