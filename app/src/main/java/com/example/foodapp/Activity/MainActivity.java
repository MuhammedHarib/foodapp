package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.SliderAdapter;
import com.example.foodapp.Domain.Category;
import com.example.foodapp.Domain.SliderItems;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBanner();
        initCategory();
        setupBottomMenu();
    }

    /* -------------------- BANNER -------------------- */
    private void initBanner() {
        DatabaseReference myRef = database.getReference("Banners");
        binding.progressBarBanner.setVisibility(View.VISIBLE);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SliderItems> banners = new ArrayList<>();

                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        SliderItems item = ds.getValue(SliderItems.class);
                        if (item != null) banners.add(item);
                    }
                }

                if (!banners.isEmpty()) {
                    SliderAdapter adapter = new SliderAdapter(banners);
                    binding.viewPager2.setAdapter(adapter);
                }

                binding.progressBarBanner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarBanner.setVisibility(View.GONE);
            }
        });
    }

    /* -------------------- CATEGORY -------------------- */
    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Category category = ds.getValue(Category.class);
                        if (category != null) list.add(category);
                    }
                }

                if (!list.isEmpty()) {
                    binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                    binding.categoryView.setNestedScrollingEnabled(false);
                    binding.categoryView.setAdapter(new CategoryAdapter(MainActivity.this, list));
                }

                binding.progressBarCategory.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarCategory.setVisibility(View.GONE);
            }
        });
    }

    /* -------------------- BOTTOM MENU -------------------- */
    private void setupBottomMenu() {
        binding.bottomMenu.setItemSelected(R.id.Home, true);

        binding.bottomMenu.setOnItemSelectedListener(id -> {
            if (id == R.id.favorites) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            } else if (id == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            } else if (id == R.id.profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }
}
