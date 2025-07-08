package com.example.milkteastore.controller.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.R;
import com.example.milkteastore.controller.MainActivity;
import com.example.milkteastore.dao.UserDAO;
import com.example.milkteastore.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> loginUser());

        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    private void loginUser() {
        String inputEmailOrUsername = etEmail.getText().toString().trim();
        String inputPassword = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(inputEmailOrUsername) || TextUtils.isEmpty(inputPassword)) {
            Toast.makeText(this, "Please enter Username/Email and Password", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordHash = hashPassword(inputPassword);
        UserDAO userDAO = new UserDAO(this);
        User loggedInUser = userDAO.login(inputEmailOrUsername, passwordHash);

        if (loggedInUser != null) {
            Toast.makeText(this, "Login successful! Welcome " + loggedInUser.firstName, Toast.LENGTH_SHORT).show();

            // 👉 Chuyển đến MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userId", loggedInUser.id); // tuỳ theo dữ liệu bạn cần
            startActivity(intent);
            finish(); // kết thúc login activity
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }


    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
