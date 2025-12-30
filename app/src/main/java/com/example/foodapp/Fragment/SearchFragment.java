package com.example.foodapp.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Domain.FoodItem;
import com.example.foodapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FoodAdapter adapter;
    private final ArrayList<FoodItem> fullList = new ArrayList<>();
    private final ArrayList<FoodItem> displayList = new ArrayList<>();
    private TextInputEditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchEditText = view.findViewById(R.id.searchEditText);
        RecyclerView recyclerView = view.findViewById(R.id.searchResultsRecycler);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new FoodAdapter(requireContext(), displayList);
        recyclerView.setAdapter(adapter);

        // 🔥 Force keyboard & focus
        searchEditText.requestFocus();
        searchEditText.post(() -> {
            if (getActivity() != null) {
                getActivity().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                );
            }
        });

        loadFoodFromFirebase();
        setupSearchListener();

        return view;
    }

    // 🔽 Load all food items once
    private void loadFoodFromFirebase() {
        FirebaseDatabase.getInstance()
                .getReference("FoodList")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        fullList.clear();
                        displayList.clear();

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            FoodItem item = ds.getValue(FoodItem.class);
                            if (item != null && item.getTitle() != null) {
                                fullList.add(item);
                            }
                        }

                        // Show all initially
                        displayList.addAll(fullList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
    }

    // 🔍 Search listener
    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFood(s.toString().trim());
            }
        });
    }

    // 🧠 Filter logic (Title-based)
    private void filterFood(String query) {

        displayList.clear();

        if (query.isEmpty()) {
            displayList.addAll(fullList);
        } else {
            for (FoodItem item : fullList) {
                if (item.getTitle() != null &&
                        item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    displayList.add(item);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}
