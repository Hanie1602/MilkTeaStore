package com.example.milkteastore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.milkteastore.model.Category;
import com.example.milkteastore.model.MilkTeaDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private MilkTeaDatabaseHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new MilkTeaDatabaseHelper(context);
    }

    // Lấy toàn bộ Category chưa bị xóa
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category WHERE DeletedTime IS NULL", null);

        if (cursor.moveToFirst()) {
            do {
                Category item = new Category(
                        cursor.getInt(cursor.getColumnIndexOrThrow("Id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("CategoryName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("CreatedTime")),
                        cursor.getString(cursor.getColumnIndexOrThrow("UpdatedTime")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DeletedTime"))
                );
                categories.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categories;
    }

    // Thêm 1 Category mới
    public long insertCategory(Category item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CategoryName", item.getCategoryName());
        values.put("Description", item.getDescription());
        values.put("CreatedTime", item.getCreatedTime());
        values.put("UpdatedTime", item.getUpdatedTime());
        values.put("DeletedTime", item.getDeletedTime());

        long result = db.insert("Category", null, values);
        db.close();
        return result;
    }
}
