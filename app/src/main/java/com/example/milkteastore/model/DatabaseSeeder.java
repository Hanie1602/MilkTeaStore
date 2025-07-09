package com.example.milkteastore.model;

import android.content.Context;

import com.example.milkteastore.dao.CategoryDAO;
import com.example.milkteastore.dao.ProductDAO;
import com.example.milkteastore.dao.ToppingDAO;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {
    public static void seedIfNeeded(Context context) {
        String time = "2025-07-08";

        CategoryDAO categoryDAO = new CategoryDAO(context);
        if (categoryDAO.getAllCategories().isEmpty()) {
            categoryDAO.insertCategory(new Category(1, "Milk Tea", "Popular milk tea drinks", time, time, null));
            categoryDAO.insertCategory(new Category(2, "Coffee", "Various types of coffee", time, time, null));
            categoryDAO.insertCategory(new Category(3, "Fruit Tea", "Refreshing fruit-based tea", time, time, null));
        }

        ProductDAO productDAO = new ProductDAO(context);
        if (productDAO.getAllProducts().isEmpty()) {
            List<Product> products = new ArrayList<>();
            products.add(new Product(1, 1, "Classic Milk Tea", "Black tea with milk and boba", 2.99, "M", 50, "", time, time, null));
            products.add(new Product(2, 1, "Matcha Milk Tea", "Japanese green tea with milk", 3.49, "L", 40, "matchamilktea", time, time, null));
            products.add(new Product(3, 1, "Thai Milk Tea", "Sweet Thai-style milk tea", 3.29, "M", 45, "thaimilktea", time, time, null));
            products.add(new Product(4, 1, "Chocolate Milk Tea", "Rich chocolate flavor", 3.59, "L", 30, "chocolatemilktea", time, time, null));
            products.add(new Product(5, 1, "Strawberry Milk Tea", "Strawberry flavor with milk", 3.19, "M", 35, "strawberrymilktea", time, time, null));
            products.add(new Product(6, 1, "Taro Milk Tea", "Taro root with creamy taste", 3.39, "M", 40, "taromilktea", time, time, null));
            products.add(new Product(7, 2, "Vietnamese Iced Coffee", "Strong drip coffee with condensed milk", 2.49, "S", 50, "vietnameseicedcoffee", time, time, null));
            products.add(new Product(8, 2, "Black Coffee", "No sugar, pure coffee", 1.99, "S", 60, "blackcoffee", time, time, null));
            products.add(new Product(9, 2, "Latte", "Milk coffee with foam", 3.09, "M", 40, "latte", time, time, null));
            products.add(new Product(10, 2, "Egg Coffee", "Coffee topped with whipped egg cream", 3.99, "L", 25, "eggcoffee", time, time, null));
            products.add(new Product(11, 3, "Peach Tea", "Peach-flavored refreshing tea", 2.69, "M", 50, "peachtea", time, time, null));
            products.add(new Product(12, 3, "Lemon Tea", "Lemon-infused black tea", 2.39, "M", 50, "lemontea", time, time, null));
            products.add(new Product(13, 3, "Jasmine Tea", "Light and fragrant tea", 2.19, "S", 40, "jasminetea", time, time, null));
            products.add(new Product(14, 3, "Rose Tea", "Elegant rose-scented tea", 2.59, "M", 30, "rosetea", time, time, null));
            products.add(new Product(15, 3, "Oolong Tea", "Traditional oolong tea", 2.89, "M", 35, "oolongtea", time, time, null));

            for (Product p : products) {
                productDAO.insertProduct(p);
            }
        }

        ToppingDAO toppingDAO = new ToppingDAO(context);
        if (toppingDAO.getAllToppings().isEmpty()) {
            toppingDAO.insertTopping(new Topping(1, "Boba", 0.50, "boba", null));
            toppingDAO.insertTopping(new Topping(2, "Grass Jelly", 0.40, "grassjelly", null));
            toppingDAO.insertTopping(new Topping(3, "Cheese Foam", 0.70, "cheesefoam", null));
            toppingDAO.insertTopping(new Topping(4, "Egg Pudding", 0.60, "eggpudding", null));
        }
    }

}
