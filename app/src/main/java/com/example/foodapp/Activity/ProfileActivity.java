package com.example.foodapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "loginPrefs";
    private static final String KEY_REMEMBER_ME = "rememberMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 🔙 Back arrow
        ImageView backBtn = findViewById(R.id.imageView13);
        backBtn.setOnClickListener(v -> finish());

        // 🚪 Logout button
        findViewById(R.id.btnLogout).setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {

        // 1️⃣ Clear SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().clear().apply();

        // 2️⃣ Firebase sign out
        FirebaseAuth.getInstance().signOut();

        // 3️⃣ Go to LoginActivity and clear back stack
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
