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

import com.example.milkteastore.controller.Cart.CartActivity;
import com.example.milkteastore.controller.Profile.ProfileActivity;
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

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                // Đã ở MainActivity (Home), không cần mở lại
                return true;
            }

            if (itemId == R.id.menu_cart) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            }

            if (itemId == R.id.menu_profile) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
            }

            return false;
        });
    }
}
