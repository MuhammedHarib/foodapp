package com.example.foodapp.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import com.example.foodapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import java.util.HashMap;
import java.util.Map;

public class AddFoodActivity extends BaseActivity {

    private TextInputEditText foodNameInput, foodPriceInput, foodImageUrlInput,
            foodDescriptionInput, foodStarInput, foodCategoryIdInput;
    private Button addFoodButton;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodNameInput = findViewById(R.id.foodNameInput);
        foodPriceInput = findViewById(R.id.foodPriceInput);
        foodImageUrlInput = findViewById(R.id.foodImageUrlInput);
        foodDescriptionInput = findViewById(R.id.foodDescriptionInput);
        foodStarInput = findViewById(R.id.foodStarInput);
        foodCategoryIdInput = findViewById(R.id.foodCategoryIdInput);
        addFoodButton = findViewById(R.id.addFoodButton);
        backBtn = findViewById(R.id.backBtn);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        backBtn.setOnClickListener(v -> finish());
        addFoodButton.setOnClickListener(v -> addFood());
    }

    private void addFood() {
        String title = foodNameInput.getText().toString().trim();
        String priceStr = foodPriceInput.getText().toString().trim();
        String imageUrl = foodImageUrlInput.getText().toString().trim();
        String categoryIdStr = foodCategoryIdInput.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(categoryIdStr)) {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use "Foods" node
        DatabaseReference ref = database.getReference("Foods");

        // FIX: Create a numeric ID instead of a "weird string"
        // This takes the current time and shortens it to a valid integer range
        int numericId = (int) (System.currentTimeMillis() % 100000000);

        Map<String, Object> map = new HashMap<>();
        map.put("Title", title); //
        map.put("Description", foodDescriptionInput.getText().toString().trim()); //
        map.put("Price", Double.parseDouble(priceStr)); // Save as Number
        map.put("ImagePath", imageUrl); //
        map.put("Star", Double.parseDouble(foodStarInput.getText().toString().isEmpty() ? "0" : foodStarInput.getText().toString())); //

        // CRITICAL FIX: Save CategoryId as a Number, not a String
        map.put("CategoryId", Integer.parseInt(categoryIdStr));
        map.put("Id", numericId); // Save ID as a Number
        map.put("BestFood", true);

        // Use the numericId as the key name so the node is "12345" instead of "-OhWF..."
        ref.child(String.valueOf(numericId)).setValue(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Added! Use ID: " + numericId, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}