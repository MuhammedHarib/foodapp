package com.example.foodapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.databinding.ActivityIntroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;

    private static final String PREFS_NAME = "loginPrefs";
    private static final String KEY_REMEMBER_ME = "rememberMe";
    
    private static final int TAP_COUNT_THRESHOLD = 5;
    private static final long TAP_TIMEOUT_MS = 2000;
    
    private int tapCount = 0;
    private long lastTapTime = 0;
    private Handler tapHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goBtn.setOnClickListener(v -> {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            SharedPreferences prefs =
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            boolean remembered = prefs.getBoolean(KEY_REMEMBER_ME, false);

            Intent intent;

            // ✅ FIXED LOGIC
            if (remembered && auth.getCurrentUser() != null) {
                intent = new Intent(IntroActivity.this, MainActivity.class);
            } else {
                auth.signOut(); // 🔴 VERY IMPORTANT
                intent = new Intent(IntroActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        });

        setupAdminLoginTrigger();
    }

    private void setupAdminLoginTrigger() {
        binding.imageView4.setOnClickListener(v -> {
            long currentTime = System.currentTimeMillis();
            
            if (currentTime - lastTapTime > TAP_TIMEOUT_MS) {
                tapCount = 0;
            }
            
            tapCount++;
            lastTapTime = currentTime;
            
            if (tapCount >= TAP_COUNT_THRESHOLD) {
                tapCount = 0;
                Intent intent = new Intent(IntroActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            } else {
                tapHandler.postDelayed(() -> tapCount = 0, TAP_TIMEOUT_MS);
            }
        });
    }
}
