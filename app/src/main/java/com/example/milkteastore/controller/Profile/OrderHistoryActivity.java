package com.example.milkteastore.controller.Profile;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.Adapter.OrderAdapter;
import com.example.milkteastore.R;
import com.example.milkteastore.model.MilkTeaDatabaseHelper;
import com.example.milkteastore.model.Order;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    ListView lvOrderHistory;
    ArrayList<Order> orderList;
    OrderAdapter adapter;
    MilkTeaDatabaseHelper dbHelper;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        lvOrderHistory = findViewById(R.id.lvOrderHistory);
        dbHelper = new MilkTeaDatabaseHelper(this);
        orderList = new ArrayList<>();

        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        userId = prefs.getInt("USER_ID", -1);

        if (userId == -1) {
            Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadOrders();
    }

    private void loadOrders() {
        orderList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM `Order` WHERE UserId = ?", new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String code = cursor.getString(2);
            String orderDate = cursor.getString(3);
            double totalPrice = cursor.getDouble(4);
            String status = cursor.getString(5);
            String createdTime = cursor.getString(6);

            orderList.add(new Order(id, userId, code, orderDate, totalPrice, status, createdTime));
        }
        cursor.close();

        adapter = new OrderAdapter(this, orderList);
        lvOrderHistory.setAdapter(adapter);
    }
}
