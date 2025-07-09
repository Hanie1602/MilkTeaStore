package com.example.milkteastore.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.model.MilkTeaDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDAO {
    private MilkTeaDatabaseHelper dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new MilkTeaDatabaseHelper(context);
    }

    public long insertOrder(int userId, String code, double totalPrice, List<CartItem> cartItems) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        long orderId = -1;

        try {
            // Insert vào bảng Order
            ContentValues orderValues = new ContentValues();
            orderValues.put("UserId", userId);
            orderValues.put("Code", code);
            orderValues.put("OrderDate", getCurrentTime());
            orderValues.put("TotalPrice", totalPrice);
            orderValues.put("Status", "Pending");
            orderValues.put("CreatedTime", getCurrentTime());

            orderId = db.insert("`Order`", null, orderValues);

            // Insert từng sản phẩm vào bảng OrderProduct
            for (CartItem item : cartItems) {
                ContentValues productValues = new ContentValues();
                productValues.put("OrderId", orderId);
                productValues.put("ProductId", item.getProductId()); // giả sử dùng imageId là ProductId
                productValues.put("Quantity", item.getQuantity());
                productValues.put("Price", Double.parseDouble(item.getPrice()));

                db.insert("OrderProduct", null, productValues);
            }

            // Insert vào bảng Payment
            ContentValues paymentValues = new ContentValues();
            paymentValues.put("OrderId", orderId);
            paymentValues.put("UserId", userId);
            paymentValues.put("FullName", "User " + userId); // bạn có thể truyền vào từ SharedPreferences
            paymentValues.put("ShippingMethod", "Standard");
            paymentValues.put("OrderPrice", totalPrice);
            paymentValues.put("PaymentStatus", "Unpaid");
            paymentValues.put("PaymentMethod", "Cash on Delivery");
            paymentValues.put("PayTime", "");
            paymentValues.put("CreateTime", getCurrentTime());

            db.insert("Payment", null, paymentValues);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            orderId = -1;
        } finally {
            db.endTransaction();
            db.close();
        }

        return orderId;
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }
}
