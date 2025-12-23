package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ViewholderFavoriteBinding;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    ArrayList<Foods> list;
    Context context;

    public FavoriteAdapter(ArrayList<Foods> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderFavoriteBinding binding =
                ViewholderFavoriteBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods food = list.get(position);

        holder.binding.titleTxt.setText(food.getTitle());
        Glide.with(context).load(food.getImagePath()).into(holder.binding.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", food);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderFavoriteBinding binding;
        ViewHolder(ViewholderFavoriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
