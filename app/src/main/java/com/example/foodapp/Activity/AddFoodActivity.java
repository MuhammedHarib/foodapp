package com.example.foodapp.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class AddFoodActivity extends BaseActivity {

    private TextInputEditText foodNameInput;
    private TextInputEditText foodPriceInput;
    private TextInputEditText foodImageUrlInput;
    private MaterialButton addFoodButton;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodNameInput = findViewById(R.id.foodNameInput);
        foodPriceInput = findViewById(R.id.foodPriceInput);
        foodImageUrlInput = findViewById(R.id.foodImageUrlInput);
        addFoodButton = findViewById(R.id.addFoodButton);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> finish());

        addFoodButton.setOnClickListener(v -> addFood());
    }

    private void addFood() {
        String name = foodNameInput.getText().toString().trim();
        String priceStr = foodPriceInput.getText().toString().trim();
        String imageUrl = foodImageUrlInput.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter food name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(priceStr)) {
            Toast.makeText(this, "Please enter price", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(imageUrl)) {
            Toast.makeText(this, "Please enter image URL", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference foodsRef = database.getReference("Foods");
        String pushId = foodsRef.push().getKey();

        Map<String, Object> foodMap = new HashMap<>();
        foodMap.put("name", name);
        foodMap.put("price", price);
        foodMap.put("imageUrl", imageUrl);
        foodMap.put("categoryId", 0);

        foodsRef.child(pushId).setValue(foodMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(AddFoodActivity.this, "Food added", Toast.LENGTH_SHORT).show();
                    foodNameInput.setText("");
                    foodPriceInput.setText("");
                    foodImageUrlInput.setText("");
                } else {
                    Toast.makeText(AddFoodActivity.this, "Failed to add food", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
