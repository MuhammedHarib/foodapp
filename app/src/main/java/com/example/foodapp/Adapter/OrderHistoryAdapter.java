package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Activity.OrderTrackingActivity;
import com.example.foodapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    private ArrayList<Map<String, Object>> ordersList;
    private Context context;

    public OrderHistoryAdapter(ArrayList<Map<String, Object>> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_order_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> order = ordersList.get(position);

        final String orderId = order.get("orderId") != null ? order.get("orderId").toString() : "";
        Object totalObj = order.get("total");
        double tempTotal = 0.0;

        if (totalObj instanceof Double) {
            tempTotal = (Double) totalObj;
        } else if (totalObj instanceof Long) {
            tempTotal = ((Long) totalObj).doubleValue();
        } else if (totalObj != null) {
            try {
                tempTotal = Double.parseDouble(totalObj.toString());
            } catch (NumberFormatException e) {
                tempTotal = 0.0;
            }
        }
        final double total = tempTotal; // effectively final

        final String status = order.get("status") != null ? order.get("status").toString() : "";
        final String address = order.get("address") != null ? order.get("address").toString() : "";

        String orderIdDisplay = orderId.isEmpty() ? "N/A" : (orderId.length() > 8 ? orderId.substring(0, 8) : orderId);

        holder.orderIdTxt.setText("Order #" + orderIdDisplay);
        holder.totalTxt.setText("$" + String.format("%.2f", total));
        holder.statusTxt.setText(status);
        holder.addressTxt.setText(address);

        holder.itemLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderTrackingActivity.class);
            intent.putExtra("orderId", orderId);
            intent.putExtra("total", total);
            intent.putExtra("status", status);
            intent.putExtra("address", address);

            Object itemsObj = order.get("items");
            if (itemsObj instanceof ArrayList) {
                intent.putExtra("items", (Serializable) itemsObj); // pass as Serializable
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTxt, totalTxt, statusTxt, addressTxt;
        ConstraintLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            totalTxt = itemView.findViewById(R.id.totalTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            addressTxt = itemView.findViewById(R.id.addressTxt);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
