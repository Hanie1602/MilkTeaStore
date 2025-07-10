package com.example.milkteastore.model;

import java.io.Serializable;
import java.util.List;

public class CartItem implements Serializable {
    private int userId;
    private int productId;
    private String name;
    private String price;
    private int image;
    private String size;
    private int quantity;
    private List<Topping> toppings;

    public CartItem(int userId, int productId, String name, String price, int image, String size, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.size = size;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }
}
