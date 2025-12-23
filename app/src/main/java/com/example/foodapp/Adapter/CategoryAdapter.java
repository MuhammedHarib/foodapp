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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.foodapp.Activity.ListFoodActivity;
import com.example.foodapp.Domain.Category;
import com.example.foodapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final ArrayList<Category> items;
    private final Context context;

    public CategoryAdapter(Context context, ArrayList<Category> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = items.get(position);

        holder.titleTxt.setText(category.getName());

        // Load image safely with Glide
        String imageUrl = category.getImagePath();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)   // gray placeholder
                    .error(R.drawable.error_image)        // red error image
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.pic);
        } else {
            holder.pic.setImageResource(R.drawable.error_image);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListFoodActivity.class);
            intent.putExtra("CategoryId", category.getId());
            intent.putExtra("CategoryName", category.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.catNameTxt);
            pic = itemView.findViewById(R.id.imagecat);
        }
    }
}
