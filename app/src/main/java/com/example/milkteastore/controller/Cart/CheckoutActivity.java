package com.example.milkteastore.controller.Cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.MainActivity;
import com.example.milkteastore.dao.OrderDAO;
import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.utils.CartManager;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Lấy thông tin người dùng
        TextView tvName = findViewById(R.id.tvName);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvPhone = findViewById(R.id.tvPhone);

        SharedPreferences prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        int currentUserId = prefs.getInt("USER_ID", -1);
        String name = prefs.getString("USER_NAME", "Unknown User");
        String address = prefs.getString("USER_ADDRESS", "No address set");
        String phone = prefs.getString("USER_PHONE", "No phone number");

        tvName.setText(name);
        tvAddress.setText(address);
        tvPhone.setText(phone);

        // Nhận giỏ hàng và tổng tiền từ CartActivity
        ArrayList<CartItem> cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("CART_LIST");
        double totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);

        TextView tvTotalPayment = findViewById(R.id.tvTotalPayment);
        tvTotalPayment.setText("Total Payment\n₱" + totalAmount);

        // Hiển thị sản phẩm
        LinearLayout productContainer = findViewById(R.id.productSummaryLayout);
        for (CartItem item : cartItems) {
            TextView tvItem = new TextView(this);
            String itemText = item.getQuantity() + " x " + item.getName()
                    + " (" + item.getSize() + ") - ₱" + item.getPrice();
            tvItem.setText(itemText);
            tvItem.setTextColor(Color.BLACK);
            tvItem.setTextSize(16);
            productContainer.addView(tvItem);
        }

        // Đặt hàng khi nhấn
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(view -> {
            OrderDAO orderDAO = new OrderDAO(CheckoutActivity.this);
            long newOrderId = orderDAO.insertOrder(currentUserId, "ORD" + System.currentTimeMillis(), totalAmount, cartItems);

            if (newOrderId != -1) {
                Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                CartManager.getInstance().clearCartForUser(currentUserId);

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
