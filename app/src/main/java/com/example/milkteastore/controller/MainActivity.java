package com.example.milkteastore.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.milkteastore.R;
import com.example.milkteastore.dao.CategoryDAO;
import com.example.milkteastore.dao.ProductDAO;
import com.example.milkteastore.model.Category;
import com.example.milkteastore.model.Product;

import java.util.ArrayList;
import java.util.List;
import com.example.milkteastore.controller.Cart.CartActivity;
import com.example.milkteastore.controller.Home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.rootLayout);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Chỉ thêm nếu bảng đang rỗng
        if (isCategoryTableEmpty()) {
            insertSampleCategories();
        }

        if (isProductTableEmpty()) {
            insertSampleProducs();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                return true;
            }

            if (itemId == R.id.menu_cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            }

            return false;
        });

    }

    private void insertSampleCategories() {
        CategoryDAO categoryDAO = new CategoryDAO(this);
        String now = "2025-07-08";

        categoryDAO.insertCategory(new Category(1, "Milk Tea", "Popular milk tea drinks", now, now, null));
        categoryDAO.insertCategory(new Category(2, "Coffee", "Various types of coffee", now, now, null));
        categoryDAO.insertCategory(new Category(3, "Fruit Tea", "Refreshing fruit-based tea", now, now, null));
    }

    private void insertSampleProducs() {
        ProductDAO repo = new ProductDAO(this);
        String time = "2025-07-08";

        List<Product> items = new ArrayList<>();
        items.add(new Product(1, 1, "Classic Milk Tea", "Black tea with milk and boba", 2.99, "M", 50, "", time, time, null));
        items.add(new Product(2, 1, "Matcha Milk Tea", "Japanese green tea with milk", 3.49, "L", 40, "matchamilktea", time, time, null));
        items.add(new Product(3, 1, "Thai Milk Tea", "Sweet Thai-style milk tea", 3.29, "M", 45, "thaimilktea", time, time, null));
        items.add(new Product(4, 1, "Chocolate Milk Tea", "Rich chocolate flavor", 3.59, "L", 30, "chocolatemilktea", time, time, null));
        items.add(new Product(5, 1, "Strawberry Milk Tea", "Strawberry flavor with milk", 3.19, "M", 35, "strawberrymilktea", time, time, null));
        items.add(new Product(6, 1, "Taro Milk Tea", "Taro root with creamy taste", 3.39, "M", 40, "taromilktea", time, time, null));
        items.add(new Product(7, 2, "Vietnamese Iced Coffee", "Strong drip coffee with condensed milk", 2.49, "S", 50, "vietnameseicedcoffee", time, time, null));
        items.add(new Product(8, 2, "Black Coffee", "No sugar, pure coffee", 1.99, "S", 60, "blackcoffee", time, time, null));
        items.add(new Product(9, 2, "Latte", "Milk coffee with foam", 3.09, "M", 40, "latte", time, time, null));
        items.add(new Product(10, 2, "Egg Coffee", "Coffee topped with whipped egg cream", 3.99, "L", 25, "eggcoffee", time, time, null));
        items.add(new Product(11, 3, "Peach Tea", "Peach-flavored refreshing tea", 2.69, "M", 50, "peachtea", time, time, null));
        items.add(new Product(12, 3, "Lemon Tea", "Lemon-infused black tea", 2.39, "M", 50, "lemontea", time, time, null));
        items.add(new Product(13, 3, "Jasmine Tea", "Light and fragrant tea", 2.19, "S", 40, "jasminetea", time, time, null));
        items.add(new Product(14, 3, "Rose Tea", "Elegant rose-scented tea", 2.59, "M", 30, "rosetea", time, time, null));
        items.add(new Product(15, 3, "Oolong Tea", "Traditional oolong tea", 2.89, "M", 35, "oolongtea", time, time, null));

        for (Product item : items) {
            repo.insertProduct(item);
        }
    }

    private boolean isCategoryTableEmpty() {
        CategoryDAO categoryDAO = new CategoryDAO(this);
        return categoryDAO.getAllCategories().isEmpty();
    }

    private boolean isProductTableEmpty() {
        ProductDAO productDAO = new ProductDAO(this);
        return productDAO.getAllProducts().isEmpty();
    }


}
