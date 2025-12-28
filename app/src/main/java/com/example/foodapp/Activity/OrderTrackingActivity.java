package com.example.foodapp.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.OrderTrackingAdapter;
import com.example.foodapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderTrackingActivity extends BaseActivity {

    private TextView orderIdTxt;
    private TextView totalTxt;
    private TextView addressTxt;
    private TextView statusTxt;
    private RecyclerView itemsRecyclerView;
    private ImageView backBtn;
    private OrderTrackingAdapter adapter;
    private ArrayList<Map<String, Object>> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        orderIdTxt = findViewById(R.id.orderIdTxt);
        totalTxt = findViewById(R.id.totalTxt);
        addressTxt = findViewById(R.id.addressTxt);
        statusTxt = findViewById(R.id.statusTxt);
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> finish());

        String orderId = getIntent().getStringExtra("orderId");
        String address = getIntent().getStringExtra("address");
        String status = getIntent().getStringExtra("status");
        double total = getIntent().getDoubleExtra("total", 0.0);
        ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) getIntent().getSerializableExtra("items");

        if (orderId != null) {
            String orderIdDisplay = orderId.length() > 8 ? orderId.substring(0, 8) : orderId;
            orderIdTxt.setText("Order #" + orderIdDisplay);
        }

        if (address != null) {
            addressTxt.setText(address);
        }

        if (status != null) {
            statusTxt.setText(status);
        }

        totalTxt.setText("$" + String.format("%.2f", total));

        itemsList = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            itemsList.addAll(items);
        } else if (orderId != null) {
            fetchOrderFromFirebase(orderId);
        }

        adapter = new OrderTrackingAdapter(itemsList);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        itemsRecyclerView.setAdapter(adapter);
    }

    private void fetchOrderFromFirebase(String orderId) {
        DatabaseReference orderRef = database.getReference("Orders").child(orderId);
        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Object addressObj = snapshot.child("address").getValue();
                    Object statusObj = snapshot.child("status").getValue();
                    Object totalObj = snapshot.child("total").getValue();
                    Object itemsObj = snapshot.child("items").getValue();

                    if (addressObj != null && addressTxt.getText().toString().isEmpty()) {
                        addressTxt.setText(addressObj.toString());
                    }
                    if (statusObj != null && statusTxt.getText().toString().isEmpty()) {
                        statusTxt.setText(statusObj.toString());
                    }
                    if (totalObj != null) {
                        double fetchedTotal = 0.0;
                        if (totalObj instanceof Double) {
                            fetchedTotal = (Double) totalObj;
                        } else if (totalObj instanceof Long) {
                            fetchedTotal = ((Long) totalObj).doubleValue();
                        }
                        if (fetchedTotal > 0) {
                            totalTxt.setText("$" + String.format("%.2f", fetchedTotal));
                        }
                    }
                    if (itemsObj instanceof ArrayList) {
                        itemsList.clear();
                        for (Object item : (ArrayList<?>) itemsObj) {
                            if (item instanceof Map) {
                                itemsList.add((Map<String, Object>) item);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}