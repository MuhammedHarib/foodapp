package com.example.foodapp.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.Adapter.AdminOrderDetailAdapter;
import com.example.foodapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Map;

public class AdminOrderDetail extends AppCompatActivity {

    private TextView orderIdDetail, orderStatusDetail, orderAddressDetail, orderTotalDetail;
    private RecyclerView recyclerViewOrderItems;
    private ImageView backBtnDetail;
    private Button updateStatusBtn;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_detail);

        orderIdDetail = findViewById(R.id.orderIdDetail);
        orderStatusDetail = findViewById(R.id.orderStatusDetail);
        orderAddressDetail = findViewById(R.id.orderAddressDetail);
        orderTotalDetail = findViewById(R.id.orderTotalDetail);
        recyclerViewOrderItems = findViewById(R.id.recyclerViewOrderItems);
        backBtnDetail = findViewById(R.id.backBtnDetail);
        updateStatusBtn = findViewById(R.id.updateStatusBtn); // Add to XML

        orderId = getIntent().getStringExtra("orderId");
        double total = getIntent().getDoubleExtra("total", 0.0);
        ArrayList<Map<String, Object>> itemsList = (ArrayList<Map<String, Object>>) getIntent().getSerializableExtra("items");

        orderIdDetail.setText("Order ID: " + orderId);
        orderStatusDetail.setText("Status: " + getIntent().getStringExtra("status"));
        orderAddressDetail.setText("Address: " + getIntent().getStringExtra("address"));
        orderTotalDetail.setText("Total: $" + String.format("%.2f", total));

        recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrderItems.setAdapter(new AdminOrderDetailAdapter(this, itemsList != null ? itemsList : new ArrayList<>()));

        // Update Status Logic
        updateStatusBtn.setOnClickListener(v -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders").child(orderId);
            ref.child("status").setValue("Delivered").addOnSuccessListener(aVoid -> {
                orderStatusDetail.setText("Status: Delivered");
                Toast.makeText(this, "Order Updated!", Toast.LENGTH_SHORT).show();
            });
        });

        // FIX: Back navigation
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        backBtnDetail.setOnClickListener(v -> finish());
    }
}