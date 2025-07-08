package com.example.milkteastore.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private MilkTeaDatabaseHelper dbHelper;

    public ProductRepository(Context context) {
        dbHelper = new MilkTeaDatabaseHelper(context);
    }

    public List<MenuItem> getAllProducts() {
        List<MenuItem> products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE DeletedTime IS NULL", null);

        if (cursor.moveToFirst()) {
            do {
                MenuItem item = new MenuItem(
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
}