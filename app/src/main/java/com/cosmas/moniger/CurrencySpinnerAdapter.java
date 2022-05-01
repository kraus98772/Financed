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
    private ArrayList<String> currenciesNames;
    private ArrayList<String> currenciesSymbols;

    public CurrencySpinnerAdapter(Context mContext, ArrayList<String> currenciesNames, ArrayList<String> currenciesSymbols) {
        this.mContext = mContext;
        this.currenciesNames = currenciesNames;
        this.currenciesSymbols = currenciesSymbols;
    }



    @Override
    public int getCount() {
        return currenciesNames != null ? currenciesNames.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return currenciesNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.currency_item, viewGroup, false);
        TextView currencyName = rootView.findViewById(R.id.currency_name);
        TextView currencySymbol = rootView.findViewById(R.id.currency_symbol);

        currencyName.setText(currenciesNames.get(i));
        currencySymbol.setText(currenciesSymbols.get(i));
        return rootView;
    }
}
