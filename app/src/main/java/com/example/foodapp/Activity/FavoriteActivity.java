package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.Adapter.FavoriteAdapter;
import com.example.foodapp.Helper.ManagmentFavorite;
import com.example.foodapp.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends BaseActivity {

    private ActivityFavoriteBinding binding;
    private ManagmentFavorite managmentFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 🔙 Back button → return to MainActivity
        binding.backBtn.setOnClickListener(v -> finish());

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
