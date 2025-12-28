package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.Adapter.CartAdapter;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {

    private ActivityCartBinding binding;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        calculateCart();
        initCartList();
    }

    private void initCartList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        binding.cartView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartView.setAdapter(new CartAdapter(
                managmentCart.getListCart(),
                managmentCart,
                this::calculateCart
        ));
    }

    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 10;
        double tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100.0;
        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100.0) / 100.0;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);

        // Pass total to checkout
        binding.CheckOutBtn.setOnClickListener(v -> {
            if (managmentCart.getListCart().isEmpty()) return;
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("total", total); // Pass total to checkout
            startActivity(intent);
        });
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }
}
