package com.example.milkteastore.controller.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.Adapter.ProductAdapter;
import com.example.milkteastore.Adapter.RecommendationAdapter;
import com.example.milkteastore.R;
import com.example.milkteastore.controller.Cart.CartActivity;
import com.example.milkteastore.controller.Profile.ProfileActivity;
import com.example.milkteastore.dao.CategoryDAO;
import com.example.milkteastore.dao.ProductDAO;
import com.example.milkteastore.databinding.ActivityMainBinding;
import com.example.milkteastore.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProductDAO productRepository;
    private CategoryDAO categoryDAO;
    private List<Product> allProducts;
    private List<Product> filteredProducts;
    private List<Product> recommendedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        if (binding == null) return;
        setContentView(binding.getRoot());

        View rootView = findViewById(R.id.rootLayout);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        productRepository = new ProductDAO(this);
        categoryDAO = new CategoryDAO(this);

        // Lấy toàn bộ sản phẩm
        allProducts = productRepository.getAllProducts();
        if (allProducts == null) allProducts = new ArrayList<>();
        filteredProducts = new ArrayList<>(allProducts);
        recommendedProducts = new ArrayList<>();

        // Cài đặt RecyclerView cho Most Popular
        RecyclerView rvMostPopular = binding.rvMostPopular;
        rvMostPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ProductAdapter mostPopularAdapter = new ProductAdapter(this, filteredProducts);
        rvMostPopular.setAdapter(mostPopularAdapter);

        // Cài đặt RecyclerView cho Recommendation
        RecyclerView rvRecommendation = binding.rvRecommendation;
        rvRecommendation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(getTopProducts(5));
        rvRecommendation.setAdapter(recommendationAdapter);

        // Thiết lập tính năng tìm kiếm
        EditText etSearch = binding.etSearch;
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Lọc danh sách khi nhập
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });

        // Đặt listener cho icon cửa hàng
        if (binding.ivShopIcon != null) {
            binding.ivShopIcon.setOnClickListener(v -> {
                double latitude = 10.8506;
                double longitude = 106.7544;
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Cửa hàng MilkTea)"));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude)));
                }
            });
        }

        // Cài đặt BottomNavigationView
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                return true;
            }

            if (itemId == R.id.menu_cart) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                return true;
            }

            if (itemId == R.id.menu_profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            }

            return false;
        });
    }

    private void filterProducts(String query) {
        filteredProducts.clear();
        if (query.isEmpty()) {
            filteredProducts.addAll(allProducts);
        } else {
            String searchQuery = query.toLowerCase();
            for (Product product : allProducts) {
                if (product != null && product.getName() != null && product.getName().toLowerCase().contains(searchQuery)) {
                    filteredProducts.add(product);
                }
            }
        }
        RecyclerView rvMostPopular = binding.rvMostPopular;
        ProductAdapter adapter = (ProductAdapter) rvMostPopular.getAdapter();
        if (adapter != null) {
            adapter.updateData(filteredProducts);
        }
    }

    private List<Product> getTopProducts(int count) {
        List<Product> topProducts = new ArrayList<>(allProducts);
        Collections.sort(topProducts, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p2.getPrice(), p1.getPrice());
            }
        });
        return topProducts.size() > count ? topProducts.subList(0, count) : topProducts;
    }
}