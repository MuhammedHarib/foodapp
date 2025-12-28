package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginActivity extends AppCompatActivity {

    private static final String ADMIN_EMAIL = "admin@foodapp.com";
    private static final String ADMIN_PASSWORD = "admin123";

    private TextInputEditText adminEmailInput;
    private TextInputEditText adminPasswordInput;
    private MaterialButton adminLoginButton;
    private TextView adminErrorTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminEmailInput = findViewById(R.id.adminEmailInput);
        adminPasswordInput = findViewById(R.id.adminPasswordInput);
        adminLoginButton = findViewById(R.id.adminLoginButton);
        adminErrorTxt = findViewById(R.id.adminErrorTxt);

        adminLoginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = adminEmailInput.getText().toString().trim();
        String password = adminPasswordInput.getText().toString().trim();

        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            adminErrorTxt.setVisibility(TextView.GONE);
            Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            adminErrorTxt.setText("Invalid credentials");
            adminErrorTxt.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}