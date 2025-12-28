package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends BaseActivity {

    private TextInputEditText addressEdt;
    private MaterialButton placeOrderBtn;
    private ProgressBar progressBar;
    private ImageView backBtn;

    private double total; // Total passed from CartActivity
    private ManagmentCart managmentCart;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        addressEdt = findViewById(R.id.addressEdt);
        placeOrderBtn = findViewById(R.id.placeOrderBtn);
        progressBar = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.backBtn);

        managmentCart = new ManagmentCart(this);
        database = FirebaseDatabase.getInstance().getReference();

        // Get total from CartActivity
        total = getIntent().getDoubleExtra("total", 0);

        backBtn.setOnClickListener(v -> finish());

        placeOrderBtn.setOnClickListener(v -> placeOrder());
    }

    private void placeOrder() {
        String address = addressEdt.getText().toString().trim();

        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter delivery address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(ProgressBar.VISIBLE);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String orderId = database.child("Orders").push().getKey();

        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("userId", userId);
        orderMap.put("total", total);
        orderMap.put("address", address);
        orderMap.put("status", "Pending");

        database.child("Orders").child(orderId).setValue(orderMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                progressBar.setVisibility(ProgressBar.GONE);

                if (error == null) {
                    managmentCart.clearCart();
                    Toast.makeText(CheckoutActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CheckoutActivity.this, OrderHistoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CheckoutActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
