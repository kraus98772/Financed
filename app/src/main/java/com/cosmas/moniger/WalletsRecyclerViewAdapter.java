package com.cosmas.moniger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WalletsRecyclerViewAdapter extends RecyclerView.Adapter<WalletsRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "WalletsRecyclerViewAdapter";
    private Context mContext;
    private ArrayList<String> walletsNames = new ArrayList<>();
    private ArrayList<Integer> walletsImages = new ArrayList<>();


    public WalletsRecyclerViewAdapter(Context mcontext, ArrayList<String> walletsNames, ArrayList<Integer> walletsImages) {
        this.mContext = mcontext;
        this.walletsNames = walletsNames;
        this.walletsImages = walletsImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.walletName.setText(walletsNames.get(position));
        holder.walletImage.setImageResource(walletsImages.get(position));
        holder.walletView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WalletActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return walletsNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView walletImage;
        TextView walletName;
        CardView walletView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            walletImage = itemView.findViewById(R.id.wallet_image);
            walletName = itemView.findViewById(R.id.wallet_name);
            walletView = itemView.findViewById(R.id.wallet_card_view);
        }
    }
}
