package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.AdminOrderDetail;
import com.example.foodapp.Domain.Order;
import com.example.foodapp.R;

import java.util.List;

public class AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersAdapter.ViewHolder> {

    private Context context;
    private List<Order> orderList;

    public AdminOrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public AdminOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Ensure this layout exists in your res/layout folder
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_admin_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Display shorter ID for UI cleanliness
        String orderIdDisplay = (order.getOrderId() != null && order.getOrderId().length() > 8)
                ? order.getOrderId().substring(0, 8)
                : order.getOrderId();

        holder.orderIdTxt.setText("Order ID: #" + orderIdDisplay);
        holder.orderTotalTxt.setText("Total: $" + String.format("%.2f", order.getTotal()));
        holder.orderStatusTxt.setText("Status: " + order.getStatus());
        holder.orderAddressTxt.setText("Address: " + order.getAddress());

        holder.itemView.setOnClickListener(v -> {
            // FIX: Pointing to AdminOrderDetail instead of AdminOrdersActivity
            Intent intent = new Intent(context, AdminOrderDetail.class);
            intent.putExtra("orderId", order.getOrderId());
            intent.putExtra("total", order.getTotal());
            intent.putExtra("status", order.getStatus());
            intent.putExtra("address", order.getAddress());
            intent.putExtra("items", order.getItems()); // ArrayList<Map<String, Object>> is Serializable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTxt, orderTotalTxt, orderStatusTxt, orderAddressTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Verify these IDs match your viewholder_admin_order.xml
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            orderTotalTxt = itemView.findViewById(R.id.orderTotalTxt);
            orderStatusTxt = itemView.findViewById(R.id.orderStatusTxt);
            orderAddressTxt = itemView.findViewById(R.id.orderAddressTxt);
        }
    }
}