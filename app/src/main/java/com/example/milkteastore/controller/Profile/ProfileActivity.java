package com.example.milkteastore.controller.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.LoginRegister.LoginActivity;
import com.example.milkteastore.controller.LoginRegister.WelcomeActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private TextView tvName, tvEmail;
    private LinearLayout rowProfile, rowOrderProduct, rowLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ view
        imgAvatar = findViewById(R.id.imgAvatar);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

        rowProfile = findViewById(R.id.rowProfile);
        rowOrderProduct = findViewById(R.id.rowOrderProduct);
        rowLogout = findViewById(R.id.rowLogout);

//        Button icon
        // Cập nhật tiêu đề cho từng item
        ((TextView) rowProfile.findViewById(R.id.tvTitle)).setText("Profile");
        ((TextView) rowOrderProduct.findViewById(R.id.tvTitle)).setText("History Order");
        ((TextView) rowLogout.findViewById(R.id.tvTitle)).setText("Logout");

        ((ImageView) rowProfile.findViewById(R.id.imgIcon)).setImageResource(R.drawable.ic_profile);
        ((ImageView) rowOrderProduct.findViewById(R.id.imgIcon)).setImageResource(R.drawable.ic_profile);
        ((ImageView) rowLogout.findViewById(R.id.imgIcon)).setImageResource(R.drawable.ic_logout);


        // Gán dữ liệu tạm (bạn có thể lấy từ SharedPreferences hoặc Intent)
        SharedPreferences prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        String name = prefs.getString("USER_NAME", "Unknown User");
        String email = prefs.getString("USER_EMAIL", "unknown@example.com");

        tvName.setText(name);
        tvEmail.setText(email);


        // Xử lý sự kiện click
        rowProfile.setOnClickListener(view -> {
            // Mở Hồ sơ
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
        });

        rowOrderProduct.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, OrderHistoryActivity.class));
        });


        rowLogout.setOnClickListener(view -> {
            // Không khai báo lại prefs — dùng biến đã có
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear(); // Xóa toàn bộ thông tin
            editor.apply();

            // Quay về màn Login
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


    }
}
