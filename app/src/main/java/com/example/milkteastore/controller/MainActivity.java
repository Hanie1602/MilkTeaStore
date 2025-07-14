package com.example.milkteastore.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.Cart.CartFragment;
import com.example.milkteastore.controller.Home.HomeFragment;
import com.example.milkteastore.controller.Profile.ProfileFragment;
import com.example.milkteastore.utils.CartManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.menu_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.menu_cart) {
                selectedFragment = new CartFragment();
            } else if (itemId == R.id.menu_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });

        updateCartBadge();
    }

    public void updateCartBadge() {
        int cartCount = CartManager.getInstance().getTotalItemCount(); // ➜ Tổng số sản phẩm

        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.menu_cart);
        if (cartCount > 0) {
            badge.setVisible(true);
            badge.setNumber(cartCount);
        } else {
            badge.setVisible(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}
