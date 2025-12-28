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

public class AddCategoryActivity extends BaseActivity {

    private TextInputEditText categoryNameInput;
    private MaterialButton addCategoryButton;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryNameInput = findViewById(R.id.categoryNameInput);
        addCategoryButton = findViewById(R.id.addCategoryButton);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> finish());

        addCategoryButton.setOnClickListener(v -> addCategory());
    }

    private void addCategory() {
        String categoryName = categoryNameInput.getText().toString().trim();

        if (TextUtils.isEmpty(categoryName)) {
            Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to "Categories" node in Firebase
        DatabaseReference categoriesRef = database.getReference("Categories");
        String pushId = categoriesRef.push().getKey();

        categoriesRef.child(pushId).child("name").setValue(categoryName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(AddCategoryActivity.this, "Category added", Toast.LENGTH_SHORT).show();
                    categoryNameInput.setText("");
                } else {
                    Toast.makeText(AddCategoryActivity.this, "Failed to add category", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish(); // Directly close activity
    }

}
