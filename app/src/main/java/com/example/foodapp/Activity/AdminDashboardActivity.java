package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.R;
import com.google.android.material.button.MaterialButton;

public class AdminDashboardActivity extends AppCompatActivity {

    private MaterialButton btnAddCategory;
    private MaterialButton btnAddFood;
    private MaterialButton btnViewOrders;
    private MaterialButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnAddCategory = findViewById(R.id.btnAddCategory);
        btnAddFood = findViewById(R.id.btnAddFood);
        btnViewOrders = findViewById(R.id.btnViewOrders);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AddCategoryActivity.class);
            startActivity(intent);
        });

        btnAddFood.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AddFoodActivity.class);
            startActivity(intent);
        });

        btnViewOrders.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminOrdersActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}