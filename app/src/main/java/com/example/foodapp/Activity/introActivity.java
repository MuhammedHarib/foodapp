package com.example.foodapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.databinding.ActivityIntroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;

    private static final String PREFS_NAME = "loginPrefs";
    private static final String KEY_REMEMBER_ME = "rememberMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 👉 NORMAL USER FLOW (GO BUTTON)
        binding.goBtn.setOnClickListener(v -> {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            SharedPreferences prefs =
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            boolean remembered = prefs.getBoolean(KEY_REMEMBER_ME, false);

            Intent intent;

            if (remembered && auth.getCurrentUser() != null) {
                intent = new Intent(IntroActivity.this, MainActivity.class);
            } else {
                auth.signOut(); // important
                intent = new Intent(IntroActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        });

        // 🔐 ADMIN LOGIN — INSTANT ON LOGO CLICK
        binding.logo.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });
    }
}
