package com.example.foodapp.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapter.AdminOrderDetailAdapter;
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.Map;

public class AdminOrderDetail extends AppCompatActivity {

    private TextView orderIdDetail, orderStatusDetail, orderAddressDetail, orderTotalDetail;
    private RecyclerView recyclerViewOrderItems;
    private ImageView backBtnDetail;
    private AdminOrderDetailAdapter adapter;
    private ArrayList<Map<String, Object>> itemsList;

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

        // Get data from Intent
        String orderId = getIntent().getStringExtra("orderId");
        String status = getIntent().getStringExtra("status");
        String address = getIntent().getStringExtra("address");
        double total = getIntent().getDoubleExtra("total", 0.0);
        itemsList = (ArrayList<Map<String, Object>>) getIntent().getSerializableExtra("items");

        orderIdDetail.setText("Order ID: " + orderId);
        orderStatusDetail.setText("Status: " + status);
        orderAddressDetail.setText("Address: " + address);
        orderTotalDetail.setText("Total: $" + String.format("%.2f", total));

        recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminOrderDetailAdapter(this, itemsList != null ? itemsList : new ArrayList<>());
        recyclerViewOrderItems.setAdapter(adapter);

        backBtnDetail.setOnClickListener(v -> finish());
    }
}
