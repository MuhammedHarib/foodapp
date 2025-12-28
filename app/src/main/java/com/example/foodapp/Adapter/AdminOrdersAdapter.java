package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.AdminOrdersActivity; // replace with detail activity if you have one
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
        View view = LayoutInflater.from(context)
                .inflate(R.layout.viewholder_admin_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Truncate order ID to first 8 chars
        String orderIdDisplay = order.getOrderId() != null && order.getOrderId().length() > 8
                ? order.getOrderId().substring(0, 8)
                : order.getOrderId();

        holder.orderIdTxt.setText("Order ID: " + orderIdDisplay);
        holder.orderTotalTxt.setText("Total: $" + String.format("%.2f", order.getTotal()));
        holder.orderStatusTxt.setText("Status: " + order.getStatus());
        holder.orderAddressTxt.setText("Address: " + order.getAddress());

        // Click to open detail (currently points to same activity, you can replace with detail activity)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminOrdersActivity.class); // replace if you create AdminOrderDetailActivity
            intent.putExtra("orderId", order.getOrderId());
            intent.putExtra("total", order.getTotal());
            intent.putExtra("status", order.getStatus());
            intent.putExtra("address", order.getAddress());
            intent.putExtra("items", order.getItems()); // Serializable
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
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            orderTotalTxt = itemView.findViewById(R.id.orderTotalTxt);
            orderStatusTxt = itemView.findViewById(R.id.orderStatusTxt);
            orderAddressTxt = itemView.findViewById(R.id.orderAddressTxt);
        }
    }
}
