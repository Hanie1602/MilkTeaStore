package com.example.milkteastore.controller.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.milkteastore.R;
import com.example.milkteastore.dao.ProductDAO;
import com.example.milkteastore.databinding.ActivityMainBinding;
import com.example.milkteastore.model.Product;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProductDAO productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        if (binding == null) {
            // Xử lý lỗi inflate nếu có
            return;
        }
        setContentView(binding.getRoot());

        // Khởi tạo repository để lấy dữ liệu từ DB
        productRepository = new ProductDAO(this);

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<Product> products = productRepository.getAllProducts();

        // Đặt listener cho card Wintermelon (sản phẩm đầu tiên)
        if (products != null && !products.isEmpty()) {
            Product wintermelon = products.get(0); // Lấy sản phẩm đầu tiên làm ví dụ
            if (binding.cardWintermelon != null) {
                binding.cardWintermelon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                        intent.putExtra("ITEM_ID", wintermelon.getId());
                        intent.putExtra("ITEM_NAME", wintermelon.getName());
                        intent.putExtra("ITEM_PRICE", String.valueOf(wintermelon.getPrice()));
                        intent.putExtra("ITEM_IMAGE", R.drawable.milktea_1);
                        intent.putExtra("ITEM_DESCRIPTION", wintermelon.getDescription());
                        intent.putExtra("ITEM_SIZE", wintermelon.getSize());
                        intent.putExtra("ITEM_QUANTITY", wintermelon.getQuantity());
                        startActivity(intent);
                    }
                });
            }
        }

        // Đặt listener cho card Java Chip (sản phẩm thứ hai)
        if (products != null && products.size() > 1) {
            Product javaChip = products.get(1); // Lấy sản phẩm thứ hai làm ví dụ
            if (binding.cardJavaChip != null) {
                binding.cardJavaChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                        intent.putExtra("ITEM_ID", javaChip.getId());
                        intent.putExtra("ITEM_NAME", javaChip.getName());
                        intent.putExtra("ITEM_PRICE", String.valueOf(javaChip.getPrice()));
                        intent.putExtra("ITEM_IMAGE", R.drawable.milktea_1);
                        intent.putExtra("ITEM_DESCRIPTION", javaChip.getDescription());
                        intent.putExtra("ITEM_SIZE", javaChip.getSize());
                        intent.putExtra("ITEM_QUANTITY", javaChip.getQuantity());
                        startActivity(intent);
                    }
                });
            }
        }

        // Đặt listener cho icon cửa hàng
        if (binding.ivShopIcon != null) {
            binding.ivShopIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double latitude = 10.8506;
                    double longitude = 106.7544;

                    Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Cửa hàng MilkTea)");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude)));
                    }
                }
            });
        }
    }
}