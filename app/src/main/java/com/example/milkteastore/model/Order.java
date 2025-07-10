package com.example.milkteastore.model;

public class Order {
    public int id, userId;
    public String code, orderDate, status, createdTime;
    public double totalPrice;

    public Order(int id, int userId, String code, String orderDate, double totalPrice, String status, String createdTime) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdTime = createdTime;
    }
}
