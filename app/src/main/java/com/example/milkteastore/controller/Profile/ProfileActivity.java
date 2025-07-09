package com.example.milkteastore.controller.Profile;

import android.content.Intent;
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
    private LinearLayout rowProfile, rowCommunity, rowSchedule, rowLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ view
        imgAvatar = findViewById(R.id.imgAvatar);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

        rowProfile = findViewById(R.id.rowProfile);
        rowCommunity = findViewById(R.id.rowCommunity);
        rowSchedule = findViewById(R.id.rowSchedule);
        rowLogout = findViewById(R.id.rowLogout);

        // Gán dữ liệu tạm (bạn có thể lấy từ SharedPreferences hoặc Intent)
        tvName.setText("Khoi Dev");
        tvEmail.setText("giakhoi.dev@gmail.com");

        // Xử lý sự kiện click
        rowProfile.setOnClickListener(view -> {
            // Mở Hồ sơ
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
        });

        rowCommunity.setOnClickListener(view -> {
            // Mở cộng đồng
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
        });

        rowSchedule.setOnClickListener(view -> {
            // Mở lịch trình
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
        });

        rowLogout.setOnClickListener(view -> {
            // Xử lý đăng xuất
            // Ví dụ: clear session, chuyển về màn Login
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish(); // kết thúc màn hiện tại
        });
    }
}
