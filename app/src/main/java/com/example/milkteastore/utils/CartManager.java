package com.example.milkteastore.utils;

import com.example.milkteastore.model.CartItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(CartItem newItem) {
        for (CartItem item : cartItems) {
            if (item.getUserId() == newItem.getUserId() &&
                    item.getName().equals(newItem.getName()) &&
                    item.getSize().equals(newItem.getSize())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity()); // Tăng số lượng
                return;
            }
        }
        cartItems.add(newItem); // Nếu chưa có, thêm mới
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void clearCartForUser(int userId) {
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getUserId() == userId) {
                iterator.remove();
            }
        }
    }

    public double calculateTotal(int userId) {
        double total = 0.0;
        for (CartItem item : cartItems) {
            if (item.getUserId() == userId) {
                total += Double.parseDouble(item.getPrice()) * item.getQuantity();
            }
        }
        return total;
    }
}
