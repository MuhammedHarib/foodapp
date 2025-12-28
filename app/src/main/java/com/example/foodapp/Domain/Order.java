package com.example.foodapp.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Order implements Serializable {
    private String orderId;
    private String userId;
    private ArrayList<Map<String, Object>> items; // each item: title, quantity, price
    private double total;
    private String address;
    private String status;

    public Order() {} // Required for Firebase

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public ArrayList<Map<String, Object>> getItems() { return items; }
    public void setItems(ArrayList<Map<String, Object>> items) { this.items = items; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
