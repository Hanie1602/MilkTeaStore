package com.example.milkteastore.controller.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.Adapter.CartAdapter;
import com.example.milkteastore.R;
import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.model.Topping;
import com.example.milkteastore.utils.CartManager;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartActivity extends AppCompatActivity {

    private Button btnCheckout;
    private RecyclerView rvCart;
    private TextView tvSubtotal;
    private CartAdapter adapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        int currentUserId = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
                .getInt("USER_ID", -1);

        cartItems = CartManager.getInstance()
                .getCartItems()
                .stream()
                .filter(item -> item.getUserId() == currentUserId)
                .collect(Collectors.toList());

        adapter = new CartAdapter(this, cartItems, this::updateSubtotal);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(adapter);

        updateSubtotal();

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("TOTAL_AMOUNT", CartManager.getInstance().calculateTotal(currentUserId));
            intent.putExtra("CART_LIST", new ArrayList<>(cartItems));
            startActivity(intent);
        });
    }

    private void updateSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            double basePrice = Double.parseDouble(item.getPrice());
            double toppingTotal = 0.0;
            if (item.getToppings() != null) {
                for (Topping topping : item.getToppings()) {
                    toppingTotal += topping.getPrice();
                }
            }
            subtotal += (basePrice + toppingTotal) * item.getQuantity();
        }
        tvSubtotal.setText(String.format("$%.2f", subtotal));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Cập nhật lại giao diện
        updateSubtotal();               // Cập nhật lại tổng tiền
    }
}
