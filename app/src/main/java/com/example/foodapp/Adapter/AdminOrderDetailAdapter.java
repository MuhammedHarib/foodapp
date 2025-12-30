package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.Map;

public class AdminOrderDetailAdapter extends RecyclerView.Adapter<AdminOrderDetailAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Map<String, Object>> itemsList;

    public AdminOrderDetailAdapter(Context context, ArrayList<Map<String, Object>> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public AdminOrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_admin_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderDetailAdapter.ViewHolder holder, int position) {
        Map<String, Object> item = itemsList.get(position);

        String title = item.get("title") != null ? item.get("title").toString() : "N/A";
        String quantity = item.get("numberInCart") != null ? item.get("numberInCart").toString() : "0";
        String price = item.get("price") != null ? item.get("price").toString() : "0.0";

        holder.titleTxt.setText(title);
        holder.quantityTxt.setText("Qty: " + quantity);
        holder.priceTxt.setText("$" + price);
    }

    @Override
    public int getItemCount() {
        return itemsList != null ? itemsList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, quantityTxt, priceTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.orderItemTitle);
            quantityTxt = itemView.findViewById(R.id.orderItemQuantity);
            priceTxt = itemView.findViewById(R.id.orderItemPrice);
        }
    }
}