package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.SliderAdapter;
import com.example.foodapp.Domain.Category;
import com.example.foodapp.Domain.SliderItems;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
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

        // 🔐 LOGIN GUARD
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Profile click (top section)
        binding.imageView6.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class))
        );

        binding.textView2.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class))
        );

        initBanner();
        initCategory();
        setupBottomMenu();
    }

    // ✅ FIX: prevent crash when returning from other activities
    @Override
    protected void onResume() {
        super.onResume();
        if (binding != null) {
            binding.bottomMenu.setItemSelected(R.id.Home, true);
        }
    }

    private void initBanner() {
        DatabaseReference myRef = database.getReference("Banners");
        binding.progressBarBanner.setVisibility(View.VISIBLE);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SliderItems> banners = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    SliderItems item = ds.getValue(SliderItems.class);
                    if (item != null) banners.add(item);
                }

                if (!banners.isEmpty()) {
                    binding.viewPager2.setAdapter(new SliderAdapter(banners));
                }
                binding.progressBarBanner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarBanner.setVisibility(View.GONE);
            }
        });
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Category category = ds.getValue(Category.class);
                    if (category != null) list.add(category);
                }

                if (!list.isEmpty()) {
                    binding.categoryView.setLayoutManager(
                            new GridLayoutManager(MainActivity.this, 3)
                    );
                    binding.categoryView.setAdapter(
                            new CategoryAdapter(MainActivity.this, list)
                    );
                }
                binding.progressBarCategory.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarCategory.setVisibility(View.GONE);
            }
        });
    }

    private void setupBottomMenu() {

        // ✅ Always highlight Home in MainActivity
        binding.bottomMenu.setItemSelected(R.id.Home, true);

        binding.bottomMenu.setOnItemSelectedListener(id -> {

            // ❌ Do nothing if Home is clicked again
            if (id == R.id.Home) {
                return;
            }

            if (id == R.id.favorites) {
                startActivity(new Intent(this, FavoriteActivity.class));
            }
            else if (id == R.id.cart) {
                startActivity(new Intent(this, CartActivity.class));
            }
            else if (id == R.id.profile) {
                startActivity(new Intent(this, ProfileActivity.class));
            }
        });
    }
}
