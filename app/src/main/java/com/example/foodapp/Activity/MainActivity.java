package com.example.foodapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Adapter.SliderAdapter;
import com.example.foodapp.Domain.Category;
import com.example.foodapp.Domain.SliderItems;
import com.example.foodapp.Fragment.SearchFragment;
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
    private static final String PREFS_NAME = "loginPrefs";
    private static final String KEY_USERNAME = "username";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔐 Auth check
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 👤 Username
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        binding.profilename.setText(prefs.getString(KEY_USERNAME, "User"));

        // 👤 Profile image click
        binding.imageView6.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        // 🔍 Search bar click → open SearchFragment
        binding.editTextText.setFocusable(false);
        binding.editTextText.setOnClickListener(v -> openSearchFragment());

        initBanner();
        initCategory();
        setupBottomMenu();

        // 🔙 Back handling (fragment first, then exit)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    binding.fragmentContainer.setVisibility(View.GONE);
                } else {
                    finish();
                }
            }
        });
    }

    // 🔍 Open search fragment
    private void openSearchFragment() {
        binding.fragmentContainer.setVisibility(View.VISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
        );
        ft.replace(R.id.fragmentContainer, new SearchFragment());
        ft.addToBackStack("search");
        ft.commit();
    }

    // 🎞 Banner slider
    private void initBanner() {
        DatabaseReference ref = database.getReference("Banners");
        binding.progressBarBanner.setVisibility(View.VISIBLE);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SliderItems> list = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SliderItems item = ds.getValue(SliderItems.class);
                    if (item != null) list.add(item);
                }
                binding.viewPager2.setAdapter(new SliderAdapter(list));
                binding.progressBarBanner.setVisibility(View.GONE);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarBanner.setVisibility(View.GONE);
            }
        });
    }

    // 🍔 Categories
    private void initCategory() {
        DatabaseReference ref = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Category> list = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Category c = ds.getValue(Category.class);
                    if (c != null) list.add(c);
                }
                binding.categoryView.setLayoutManager(
                        new GridLayoutManager(MainActivity.this, 3));
                binding.categoryView.setAdapter(
                        new CategoryAdapter(MainActivity.this, list));
                binding.progressBarCategory.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarCategory.setVisibility(View.GONE);
            }
        });



    }

    // ⬇ Bottom Navigation (FIXED)
    private void setupBottomMenu() {

        // Highlight Home
        binding.bottomMenu.setItemSelected(R.id.Home, true);

        binding.bottomMenu.setOnItemSelectedListener(id -> {

            // Prevent reloading Home
            if (id == R.id.Home) return;

            if (id == R.id.favorites) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            }
            else if (id == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
            else if (id == R.id.profile) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ✅ Always reset bottom menu to Home when returning
        binding.bottomMenu.setItemSelected(R.id.Home, true);

        // ✅ Ensure search fragment is hidden if user returned
        binding.fragmentContainer.setVisibility(View.GONE);
    }
}
