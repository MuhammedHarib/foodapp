package com.example.foodapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.Map;

public class OrderTrackingAdapter extends RecyclerView.Adapter<OrderTrackingAdapter.ViewHolder> {

    private ArrayList<Map<String, Object>> itemsList;

    public OrderTrackingAdapter(ArrayList<Map<String, Object>> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_tracking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> item = itemsList.get(position);
        
        String title = item.get("title") != null ? item.get("title").toString() : "";
        Object quantityObj = item.get("quantity");
        int quantity = 0;
        if (quantityObj instanceof Integer) {
            quantity = (Integer) quantityObj;
        } else if (quantityObj instanceof Long) {
            quantity = ((Long) quantityObj).intValue();
        }
        
        Object priceObj = item.get("price");
        double price = 0.0;
        if (priceObj instanceof Double) {
            price = (Double) priceObj;
        } else if (priceObj instanceof Long) {
            price = ((Long) priceObj).doubleValue();
        } else if (priceObj != null) {
            try {
                price = Double.parseDouble(priceObj.toString());
            } catch (NumberFormatException e) {
                price = 0.0;
            }
        }

        holder.titleTxt.setText(title);
        holder.quantityTxt.setText("Qty: " + quantity);
        holder.priceTxt.setText("$" + String.format("%.2f", price));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, quantityTxt, priceTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
        }
    }
}
