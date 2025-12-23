package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.Adapter.FavoriteAdapter;
import com.example.foodapp.Helper.ManagmentFavorite;
import com.example.foodapp.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends BaseActivity {

    ActivityFavoriteBinding binding;
    ManagmentFavorite managmentFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentFavorite = new ManagmentFavorite(this);

        if (managmentFavorite.getFavoriteList().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
        }

        binding.favoriteView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        binding.favoriteView.setAdapter(
                new FavoriteAdapter(managmentFavorite.getFavoriteList())
        );
    }
}
