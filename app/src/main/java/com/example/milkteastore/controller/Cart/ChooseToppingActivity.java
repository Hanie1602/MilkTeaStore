package com.example.milkteastore.controller.Cart;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteastore.model.CartItem;
import com.example.milkteastore.model.MilkTeaDatabaseHelper;
import com.example.milkteastore.model.Topping;

import java.util.ArrayList;
import java.util.List;

import com.example.milkteastore.R;
import com.example.milkteastore.utils.CartManager;

public class ChooseToppingActivity extends AppCompatActivity {

    private LinearLayout toppingContainer;
    private Button btnConfirm;
    private int productId;
    private int cartIndex;

    private List<Topping> selectedToppings = new ArrayList<>();
    private MilkTeaDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topping);

        toppingContainer = findViewById(R.id.toppingContainer);
        btnConfirm = findViewById(R.id.btnConfirm);
        dbHelper = new MilkTeaDatabaseHelper(this);

        productId = getIntent().getIntExtra("productId", -1);
        cartIndex = getIntent().getIntExtra("cartIndex", -1);

        loadToppingsFromDB();

        btnConfirm.setOnClickListener(v -> {
            if (cartIndex >= 0) {
                CartItem cartItem = CartManager.getInstance().getCartItems().get(cartIndex);
                cartItem.setToppings(new ArrayList<>(selectedToppings));
            }

            Toast.makeText(this, "Toppings added!", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại giỏ hàng
        });

    }

    private void loadToppingsFromDB() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Topping WHERE DeletedTime IS NULL", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("Price"));

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(name + " ($" + price + ")");
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedToppings.add(new Topping(id, name, price));
                } else {
                    selectedToppings.removeIf(t -> t.getId() == id);
                }
            });

            toppingContainer.addView(checkBox);
        }
        cursor.close();
    }
}

