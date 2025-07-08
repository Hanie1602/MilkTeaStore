package com.example.milkteastore.controller.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.milkteastore.R;
import com.example.milkteastore.dao.ProductRepository;
import com.example.milkteastore.databinding.ActivityMainBinding;
import com.example.milkteastore.model.MenuItem;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo repository để lấy dữ liệu từ DB
        productRepository = new ProductRepository(this);

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<MenuItem> products = productRepository.getAllProducts();

        // Đặt listener cho card Wintermelon (sản phẩm đầu tiên)
        if (!products.isEmpty()) {
            MenuItem wintermelon = products.get(0); // Lấy sản phẩm đầu tiên làm ví dụ
            binding.cardWintermelon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                    intent.putExtra("ITEM_ID", wintermelon.getId()); // Truyền ID để lấy chi tiết
                    intent.putExtra("ITEM_NAME", wintermelon.getName());
                    intent.putExtra("ITEM_PRICE", String.valueOf(wintermelon.getPrice()));
                    intent.putExtra("ITEM_IMAGE", R.drawable.milktea_1); // Tạm thời dùng placeholder
                    intent.putExtra("ITEM_DESCRIPTION", wintermelon.getDescription());
                    intent.putExtra("ITEM_SIZE", wintermelon.getSize());
                    intent.putExtra("ITEM_QUANTITY", wintermelon.getQuantity());
                    startActivity(intent);
                }
            });
        }

        // Đặt listener cho card Java Chip (sản phẩm thứ hai)
        if (products.size() > 1) {
            MenuItem javaChip = products.get(1); // Lấy sản phẩm thứ hai làm ví dụ
            binding.cardJavaChip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                    intent.putExtra("ITEM_ID", javaChip.getId()); // Truyền ID để lấy chi tiết
                    intent.putExtra("ITEM_NAME", javaChip.getName());
                    intent.putExtra("ITEM_PRICE", String.valueOf(javaChip.getPrice()));
                    intent.putExtra("ITEM_IMAGE", R.drawable.milktea_1); // Tạm thời dùng placeholder
                    intent.putExtra("ITEM_DESCRIPTION", javaChip.getDescription());
                    intent.putExtra("ITEM_SIZE", javaChip.getSize());
                    intent.putExtra("ITEM_QUANTITY", javaChip.getQuantity());
                    startActivity(intent);
                }
            });
        }
    }
}