package com.example.grubexpress.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.grubexpress.Domain.Category;
import com.example.grubexpress.Domain.Foods;
import com.example.grubexpress.R;

import java.util.ArrayList;

public class CategoryAdapater extends RecyclerView.Adapter<CategoryAdapater.ViewHolder> {
    private ArrayList<Category> items;
    private Context context;

    public CategoryAdapater(ArrayList<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.titleTxt.setText(items.get(position).getName());

        switch(position)
        {
            case 0:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_0_background);
                break;
            }
            case 1:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_1_background);
                break;
            }
            case 2:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_2_background);
                break;
            }
            case 3:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_3_background);
                break;
            }
            case 4:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_4_background);
                break;
            }
            case 5:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_5_background);
                break;
            }
            case 6:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_6_background);
                break;
            }
            case 7:
            {
                holder.pic.setBackgroundResource(R.drawable.cat_7_background);
                break;
            }
        }


        int drawableResourceId = context.getResources().getIdentifier(items.get(position).getImagePath(), "drawable", context.getPackageName());

//         Load image using Glide library
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder pattern for better performance
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.catNameText);
            pic = itemView.findViewById(R.id.imgcat);
        }
    }
}
