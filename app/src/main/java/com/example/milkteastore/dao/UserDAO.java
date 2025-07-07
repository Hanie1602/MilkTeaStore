package com.example.milkteastore.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.milkteastore.model.MilkTeaDatabaseHelper;
import com.example.milkteastore.model.User;

public class UserDAO {
    private MilkTeaDatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new MilkTeaDatabaseHelper(context);
    }

    public User login(String inputUsernameOrEmail, String inputPasswordHash) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM User WHERE (Username = ? OR Email = ?) AND PasswordHash = ?";
        Cursor cursor = db.rawQuery(query, new String[]{inputUsernameOrEmail, inputUsernameOrEmail, inputPasswordHash});

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"));
            user.firstName = cursor.getString(cursor.getColumnIndexOrThrow("FirstName"));
            user.lastName = cursor.getString(cursor.getColumnIndexOrThrow("LastName"));
            user.username = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
            user.email = cursor.getString(cursor.getColumnIndexOrThrow("Email"));
            user.passwordHash = cursor.getString(cursor.getColumnIndexOrThrow("PasswordHash"));
            user.role = cursor.getString(cursor.getColumnIndexOrThrow("Role"));
        }

        cursor.close();
        db.close();
        return user;
    }

    public boolean isUsernameOrEmailExists(String username, String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Username = ? OR Email = ?", new String[]{username, email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean register(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FirstName", user.firstName);
        values.put("LastName", user.lastName);
        values.put("Username", user.username);
        values.put("Email", user.email);
        values.put("PasswordHash", user.passwordHash);
        values.put("Role", "Customer");
        values.put("CreatedTime", String.valueOf(System.currentTimeMillis()));
        long result = db.insert("User", null, values);
        return result != -1;
    }

}
