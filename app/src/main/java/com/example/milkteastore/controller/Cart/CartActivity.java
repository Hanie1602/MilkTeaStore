package com.example.milkteastore.controller.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.Adapter.CartAdapter;
import com.example.milkteastore.R;
import com.example.milkteastore.controller.LoginRegister.LoginActivity;
import com.example.milkteastore.controller.LoginRegister.WelcomeActivity;
import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.utils.CartManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartActivity extends AppCompatActivity {

    Button btnCheckout;
    RecyclerView rvCart;
    TextView tvSubtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        btnCheckout = findViewById(R.id.btnCheckout);

        int currentUserId = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getInt("USER_ID", -1);
        List<CartItem> cartItems = CartManager.getInstance()
                .getCartItems()
                .stream()
                .filter(item -> item.getUserId() == currentUserId)
                .collect(Collectors.toList());

        CartAdapter adapter = new CartAdapter(this, cartItems, this::updateSubtotal);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(adapter);

        updateSubtotal();

        // ✅ Di chuyển intent vào đây — chỉ chạy khi nhấn nút "Checkout"
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("TOTAL_AMOUNT", CartManager.getInstance().calculateTotal(currentUserId));
            intent.putExtra("CART_LIST", new ArrayList<>(cartItems));
            startActivity(intent);
        });
    }

    private void updateSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : CartManager.getInstance().getCartItems()) {
            subtotal += Double.parseDouble(item.getPrice()) * item.getQuantity();
        }
        tvSubtotal.setText(String.format("$%.2f", subtotal));
    }
}

