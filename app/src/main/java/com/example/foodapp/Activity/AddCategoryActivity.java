package com.example.foodapp.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import com.example.foodapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import java.util.HashMap;

public class AddCategoryActivity extends BaseActivity {

    private TextInputEditText categoryNameInput, categoryImageInput;
    private MaterialButton addCategoryButton;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryNameInput = findViewById(R.id.categoryNameInput);
        categoryImageInput = findViewById(R.id.categoryImageInput); // Added for Image URL
        addCategoryButton = findViewById(R.id.addCategoryButton);
        backBtn = findViewById(R.id.backBtn);

        // Modern Back Press handling to avoid errors
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        backBtn.setOnClickListener(v -> finish());
        addCategoryButton.setOnClickListener(v -> addCategory());
    }

    private void addCategory() {
        String name = categoryNameInput.getText().toString().trim();
        String imagePath = categoryImageInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(imagePath)) {
            Toast.makeText(this, "Please fill Name and Image URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // MATCHING FIREBASE: Using "Category" (Singular)
        DatabaseReference categoriesRef = database.getReference("Category");

        // FIX: Generate a numeric ID to prevent app crashes on the user side
        int numericId = (int) (System.currentTimeMillis() % 100000);

        HashMap<String, Object> map = new HashMap<>();
        map.put("Id", numericId); // Saved as Number
        map.put("Name", name);     // Key matches screenshot
        map.put("ImagePath", imagePath); // Key matches screenshot

        // Use the numericId as the node name (0, 1, 2...) instead of "-OhWF..."
        categoriesRef.child(String.valueOf(numericId)).setValue(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AddCategoryActivity.this, "Category Added with ID: " + numericId, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddCategoryActivity.this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}