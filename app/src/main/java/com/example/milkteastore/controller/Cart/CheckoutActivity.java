package com.example.milkteastore.controller.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.MainActivity;

public class CheckoutActivity extends AppCompatActivity {

    private Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Ánh xạ nút đặt hàng
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(v -> {
            // Hiển thị thông báo đặt hàng thành công
            Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_SHORT).show();

            // TODO: Xử lý lưu đơn hàng, gọi API hoặc ghi vào SQLite tại đây

            // Chuyển về màn hình chính sau khi đặt hàng
            Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Đóng CheckoutActivity
        });
    }
}
