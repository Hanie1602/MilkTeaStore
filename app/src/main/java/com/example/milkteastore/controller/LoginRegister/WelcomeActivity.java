package com.example.milkteastore.controller.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.R;

public class WelcomeActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.milkteastore.R.layout.activity_welcome);

        // Gọi hàm chèn dữ liệu nếu bảng rỗng
        com.example.milkteastore.model.DatabaseSeeder.seedIfNeeded(this);

        btnLogin = findViewById(R.id.btnWelcomeLogin);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
