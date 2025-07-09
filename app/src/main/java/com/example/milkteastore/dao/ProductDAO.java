package com.example.milkteastore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.milkteastore.model.Product;
import com.example.milkteastore.model.MilkTeaDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private MilkTeaDatabaseHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new MilkTeaDatabaseHelper(context);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE DeletedTime IS NULL", null);

        if (cursor.moveToFirst()) {
            do {
                Product item = new Product(
                        cursor.getInt(cursor.getColumnIndexOrThrow("Id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Description")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("Price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Size")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("Quantity")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Image")),
                        cursor.getString(cursor.getColumnIndexOrThrow("CreatedTime")),
                        cursor.getString(cursor.getColumnIndexOrThrow("UpdatedTime")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DeletedTime"))
                );
                products.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }

    // Add more methods like insert, update, delete as needed
    public long insertProduct(Product item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CategoryId", item.getCategoryId());
        values.put("Name", item.getName());
        values.put("Description", item.getDescription());
        values.put("Price", item.getPrice());
        values.put("Size", item.getSize());
        values.put("Quantity", item.getQuantity());
        values.put("Image", item.getImage());
        values.put("CreatedTime", item.getCreatedTime());
        values.put("UpdatedTime", item.getUpdatedTime());
        values.put("DeletedTime", item.getDeletedTime());
        long result = db.insert("Product", null, values);
        db.close();
        return result;
    }


}