package com.example.milkteastore.controller.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.Cart.CartActivity;
import com.example.milkteastore.controller.Cart.CheckoutActivity;
import com.example.milkteastore.databinding.ActivityDetailsBinding;
import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.utils.CartManager;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy dữ liệu từ Intent
        int itemId = getIntent().getIntExtra("ITEM_ID", -1);
        String itemName = getIntent().getStringExtra("ITEM_NAME");
        String itemPrice = getIntent().getStringExtra("ITEM_PRICE");
        int itemImage = getIntent().getIntExtra("ITEM_IMAGE", R.drawable.milktea_1);
        String itemDescription = getIntent().getStringExtra("ITEM_DESCRIPTION");
        String itemSize = getIntent().getStringExtra("ITEM_SIZE");
        int itemQuantity = getIntent().getIntExtra("ITEM_QUANTITY", 0);

        // Đặt giá trị mặc định nếu null
        if (itemName == null) itemName = "Unknown Item";
        if (itemPrice == null) itemPrice = "Unknown Price";
        if (itemDescription == null) itemDescription = "No description available";
        if (itemSize == null) itemSize = "Unknown Size";

        // Cập nhật giao diện
        binding.tvItemName.setText(itemName);
        binding.tvItemPrice.setText("₱ " + itemPrice);
        binding.tvItemDescription.setText(itemDescription);
        binding.imgItem.setImageResource(itemImage);
        binding.tvItemSize.setText("Size: " + itemSize);
        binding.tvItemQuantity.setText("Available: " + itemQuantity);

        // Fix lỗi bằng cách tạo biến final mới
        final String finalName = itemName;
        final String finalPrice = itemPrice;
        final int finalImage = itemImage;
        final String finalSize = itemSize;

// Button click
        binding.btnAddToCart.setOnClickListener(v -> {
            int userId = getSharedPreferences("USER_SESSION", MODE_PRIVATE).getInt("USER_ID", -1);

            if (userId == -1) {
                Toast.makeText(this, "You must be logged in to add items to cart", Toast.LENGTH_SHORT).show();
                return;
            }

            CartItem item = new CartItem(userId, itemId, finalName, finalPrice, finalImage, finalSize, 1);


            CartManager.getInstance().addToCart(item);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });


    }
}