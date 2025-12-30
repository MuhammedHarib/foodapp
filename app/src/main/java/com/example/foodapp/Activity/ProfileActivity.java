package com.example.foodapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "loginPrefs";
    private static final String KEY_USERNAME = "username"; // ✅ Username key

    private TextView profiletxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profiletxt = findViewById(R.id.profiletxt);

        // 🔐 Load username from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString(KEY_USERNAME, "User"); // default "User"
        profiletxt.setText(username);

        // 🔙 Back button
        ImageView backBtn = findViewById(R.id.imageView13);
        backBtn.setOnClickListener(v -> finish());

        // 📦 My Orders → OrderHistoryActivity
        findViewById(R.id.cardMyOrders).setOnClickListener(v -> {
            startActivity(new Intent(
                    ProfileActivity.this,
                    OrderHistoryActivity.class
            ));
        });

        // 📍 Track Order → OrderTrackingActivity
        findViewById(R.id.cardTrackOrder).setOnClickListener(v -> {
            startActivity(new Intent(
                    ProfileActivity.this,
                    OrderTrackingActivity.class
            ));
        });

        // 🚪 Logout
        findViewById(R.id.btnLogout).setOnClickListener(v -> logoutUser());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ✅ Update username in case it changed
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString(KEY_USERNAME, "User");
        profiletxt.setText(username);
    }

    private void logoutUser() {

        // 1️⃣ Clear SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().clear().apply();

        // 2️⃣ Firebase sign out
        FirebaseAuth.getInstance().signOut();

        // 3️⃣ Go to LoginActivity (clear back stack)
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
