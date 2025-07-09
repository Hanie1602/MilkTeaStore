package com.example.milkteastore.model;

public class Topping {
    private int id;
    private String name;
    private String image;
    private double price;
    private String deletedTime;

    public Topping(int id, String name, double price, String image, String deletedTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.deletedTime = deletedTime;
    }

    // Getter - Setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getDeletedTime() {
        return deletedTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDeletedTime(String deletedTime) {
        this.deletedTime = deletedTime;
    }
}
