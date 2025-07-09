package com.example.milkteastore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.milkteastore.model.MilkTeaDatabaseHelper;
import com.example.milkteastore.model.Topping;

import java.util.ArrayList;
import java.util.List;

public class ToppingDAO {
    private MilkTeaDatabaseHelper dbHelper;

    public ToppingDAO(Context context) {
        dbHelper = new MilkTeaDatabaseHelper(context);
    }

    // Lấy toàn bộ topping chưa bị xóa
    public List<Topping> getAllToppings() {
        List<Topping> toppings = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Topping WHERE DeletedTime IS NULL", null);

        if (cursor.moveToFirst()) {
            do {
                Topping topping = new Topping(
                        cursor.getInt(cursor.getColumnIndexOrThrow("Id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Name")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("Price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Image")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DeletedTime"))
                );
                toppings.add(topping);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toppings;
    }

    // Thêm topping mới
    public long insertTopping(Topping topping) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", topping.getName());
        values.put("Price", topping.getPrice());
        values.put("Image", topping.getImage());
        values.put("DeletedTime", topping.getDeletedTime());

        long result = db.insert("Topping", null, values);
        db.close();
        return result;
    }

}
