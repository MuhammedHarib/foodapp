package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private final ArrayList<Foods> items;
    private Context context;

    public FoodListAdapter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.viewholder_list_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Foods food = items.get(position);

        holder.titletxt.setText(food.getTitle());
        holder.timetxt.setText(food.getTimeValue() + " min");
        holder.pricetxt.setText("$" + food.getPrice());
        holder.ratingtxt.setText("" + food.getStar());

        Glide.with(context)
                .load(food.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(50))
                .into(holder.pic);

        // OPEN DETAIL ACTIVITY
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", food);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titletxt, ratingtxt, pricetxt, timetxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titletxt = itemView.findViewById(R.id.titlletxt);
            ratingtxt = itemView.findViewById(R.id.ratingtxt);
            pricetxt = itemView.findViewById(R.id.pricetxt);
            timetxt = itemView.findViewById(R.id.timetxt);
            pic = itemView.findViewById(R.id.img);
        }
    }
}
