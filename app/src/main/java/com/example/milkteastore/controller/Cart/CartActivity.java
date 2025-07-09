package com.example.milkteastore.controller.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.LoginRegister.LoginActivity;
import com.example.milkteastore.controller.LoginRegister.WelcomeActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

public class CartActivity extends AppCompatActivity {

    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // 1. Tìm itemContainer
        LinearLayout itemContainer = findViewById(R.id.itemContainer);

        // 2. Inflate item layout và add vào container (giả sử có 2 sản phẩm mẫu)
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < 2; i++) {
            View itemView = inflater.inflate(R.layout.cart_item, itemContainer, false);

            // Gán dữ liệu mẫu
            ImageView imgProduct = itemView.findViewById(R.id.imgProduct);
            TextView tvName = itemView.findViewById(R.id.tvName);
            TextView tvDesc = itemView.findViewById(R.id.tvDesc);
            TextView tvPrice = itemView.findViewById(R.id.tvPrice);
            TextView tvQuantity = itemView.findViewById(R.id.tvQuantity);

            // Có thể load ảnh từ res hoặc từ URL (nếu sau này có Glide hoặc Picasso)
            imgProduct.setImageResource(R.drawable.matcha);
            tvName.setText("Matcha Milk Tea");
            tvDesc.setText("Size: M");
            tvPrice.setText("$3.50");
            tvQuantity.setText("1");

            // Thêm view vào container
            itemContainer.addView(itemView);
        }

        btnCheckout = findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });
    }
}
