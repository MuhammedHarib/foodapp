package com.example.foodapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.OrderHistoryAdapter;
import com.example.foodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderHistoryActivity extends BaseActivity {

    private RecyclerView ordersRecyclerView;
    private TextView emptyTxt;
    private ImageView backBtn;
    private OrderHistoryAdapter adapter;
    private ArrayList<Map<String, Object>> ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        emptyTxt = findViewById(R.id.emptyTxt);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> finish());

        ordersList = new ArrayList<>();
        adapter = new OrderHistoryAdapter(ordersList, this);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ordersRecyclerView.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            emptyTxt.setVisibility(View.VISIBLE);
            emptyTxt.setText("No orders yet");
            ordersRecyclerView.setVisibility(View.GONE);
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        DatabaseReference ordersRef = database.getReference("Orders");
        Query query = ordersRef.orderByChild("userId").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ordersList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                        Map<String, Object> orderMap = new HashMap<>();
                        orderMap.put("orderId", orderSnapshot.getKey());
                        Object userIdObj = orderSnapshot.child("userId").getValue();
                        Object totalObj = orderSnapshot.child("total").getValue();
                        Object statusObj = orderSnapshot.child("status").getValue();
                        Object addressObj = orderSnapshot.child("address").getValue();
                        Object itemsObj = orderSnapshot.child("items").getValue();
                        
                        orderMap.put("userId", userIdObj != null ? userIdObj.toString() : "");
                        if (totalObj instanceof Double) {
                            orderMap.put("total", (Double) totalObj);
                        } else if (totalObj instanceof Long) {
                            orderMap.put("total", ((Long) totalObj).doubleValue());
                        } else {
                            orderMap.put("total", 0.0);
                        }
                        orderMap.put("status", statusObj != null ? statusObj.toString() : "");
                        orderMap.put("address", addressObj != null ? addressObj.toString() : "");
                        if (itemsObj instanceof ArrayList) {
                            orderMap.put("items", itemsObj);
                        }
                        ordersList.add(orderMap);
                    }
                }

                if (ordersList.isEmpty()) {
                    emptyTxt.setVisibility(View.VISIBLE);
                    emptyTxt.setText("No orders yet");
                    ordersRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyTxt.setVisibility(View.GONE);
                    ordersRecyclerView.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                emptyTxt.setVisibility(View.VISIBLE);
                emptyTxt.setText("No orders yet");
                ordersRecyclerView.setVisibility(View.GONE);
            }
        });
    }
}
