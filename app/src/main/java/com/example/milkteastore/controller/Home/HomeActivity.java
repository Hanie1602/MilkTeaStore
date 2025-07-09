package com.example.milkteastore.controller.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteastore.Adapter.ProductAdapter;
import com.example.milkteastore.Adapter.RecommendationAdapter; // Thêm adapter mới
import com.example.milkteastore.R;
import com.example.milkteastore.dao.CategoryDAO;
import com.example.milkteastore.dao.ProductDAO;
import com.example.milkteastore.databinding.ActivityMainBinding;
import com.example.milkteastore.model.Category;
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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        if (binding == null) return;
        setContentView(binding.getRoot());

        productRepository = new ProductDAO(this);
        categoryDAO = new CategoryDAO(this);

        // Lấy toàn bộ sản phẩm
        allProducts = productRepository.getAllProducts();
        filteredProducts = new ArrayList<>(allProducts);
        recommendedProducts = new ArrayList<>();

        // Cài đặt RecyclerView cho Most Popular
        RecyclerView rvMostPopular = binding.rvMostPopular;
        rvMostPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ProductAdapter mostPopularAdapter = new ProductAdapter(filteredProducts);
        rvMostPopular.setAdapter(mostPopularAdapter);

        // Cài đặt RecyclerView cho Recommendation
        RecyclerView rvRecommendation = binding.rvRecommendation;
        rvRecommendation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecommendationAdapter recommendationAdapter = new RecommendationAdapter(getTopProducts(5)); // Lấy top 5 sản phẩm
        rvRecommendation.setAdapter(recommendationAdapter);

        // Lấy danh mục và gắn listener
        List<Category> categories = categoryDAO.getAllCategories();
        setupCategoryListeners(categories);

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
    }

    private void setupCategoryListeners(List<Category> categories) {
        LinearLayout llCategories = binding.llCategories;
        llCategories.removeAllViews();

        for (Category category : categories) {
            LinearLayout categoryItem = createCategoryItem(category);
            int finalI = category.getId();
            categoryItem.setOnClickListener(v -> filterProductsByCategory(finalI));
            llCategories.addView(categoryItem);
        }
    }

    private LinearLayout createCategoryItem(Category category) {
        LinearLayout item = new LinearLayout(this);
        item.setLayoutParams(new LinearLayout.LayoutParams(72, 72));
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);

        androidx.cardview.widget.CardView cardView = new androidx.cardview.widget.CardView(this);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(48, 48));
        cardView.setRadius(24);
        cardView.setCardElevation(4);
        cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));

        android.widget.ImageView imageView = new android.widget.ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(32, 32));
        imageView.setScaleType(android.widget.ImageView.ScaleType.CENTER);
        if ("Milk Tea".equals(category.getCategoryName())) imageView.setImageResource(R.drawable.ic_milk_tea);
        else if ("Fruit Tea".equals(category.getCategoryName())) imageView.setImageResource(R.drawable.ic_fruit_tea);
        else if ("Coffee".equals(category.getCategoryName())) imageView.setImageResource(R.drawable.ic_coffee);
        imageView.setContentDescription(category.getCategoryName() + " Icon");
        cardView.addView(imageView);

        android.widget.TextView textView = new android.widget.TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(category.getCategoryName());
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setPadding(0, 4, 0, 0);

        item.addView(cardView);
        item.addView(textView);
        return item;
    }

    private void filterProductsByCategory(int categoryId) {
        filteredProducts.clear();
        for (Product product : allProducts) {
            if (product.getCategoryId() == categoryId) {
                filteredProducts.add(product);
            }
        }
        RecyclerView rvMostPopular = binding.rvMostPopular;
        ProductAdapter adapter = (ProductAdapter) rvMostPopular.getAdapter();
        if (adapter != null) adapter.updateData(filteredProducts);
    }

    private List<Product> getTopProducts(int count) {
        List<Product> topProducts = new ArrayList<>(allProducts);
        Collections.sort(topProducts, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p2.getPrice(), p1.getPrice()); // Sắp xếp giảm dần theo giá
            }
        });
        return topProducts.size() > count ? topProducts.subList(0, count) : topProducts;
    }
}