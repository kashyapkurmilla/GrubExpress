package com.example.grubexpress.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grubexpress.Domain.Foods;
import com.example.grubexpress.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;


import java.util.ArrayList;

public class BestFoodsAdapter extends RecyclerView.Adapter<BestFoodsAdapter.ViewHolder> {
    private ArrayList<Foods> items;
    private Context context;

    public BestFoodsAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_best_deal, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods food = items.get(position);
        holder.titleTxt.setText(food.getTitle());
        holder.priceTxt.setText("Rs " + food.getPrice());
        holder.timeTxt.setText(food.getTimeValue() + " min");
        holder.starTxt.setText(String.valueOf(food.getStar()));

//         Load image using Glide library
        Glide.with(context)
                .load(food.getImagePath())
                .centerCrop()
                .transform(new CenterCrop(), new RoundedCorners(30)) // Adjust corner radius as needed
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder pattern for better performance
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, timeTxt, starTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.Titletxt);
            priceTxt = itemView.findViewById(R.id.pricetxt);
            timeTxt = itemView.findViewById(R.id.timetxt);
            starTxt = itemView.findViewById(R.id.starText);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
