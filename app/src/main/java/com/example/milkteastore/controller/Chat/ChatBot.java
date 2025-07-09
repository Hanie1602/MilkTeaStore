package com.example.milkteastore.controller.Chat;

public class ChatBot {
    public static String getReply(String userMessage) {
        userMessage = userMessage.toLowerCase();

        if (userMessage.contains("hello") || userMessage.contains("hi") ||
                userMessage.contains("hey") || userMessage.contains("good morning") ||
                userMessage.contains("good afternoon")) {
            return "Hi there! 👋 How can I help you today?";
        } else if (userMessage.contains("milk tea") || userMessage.contains("menu") ||
                userMessage.contains("products") || userMessage.contains("drink")) {
            return "You can check out all our milk teas, coffees, and fruit teas in the Products section! 🍹";
        } else if (userMessage.contains("price") || userMessage.contains("how much") ||
                userMessage.contains("cost")) {
            return "Our drinks range from 0.40 to 3.99 depending on the type and size.";
        } else if (userMessage.contains("open") || userMessage.contains("time") ||
                userMessage.contains("hours") || userMessage.contains("working hours")) {
            return "We’re open daily from 8:00 AM to 10:00 PM, including weekends!";
        } else if (userMessage.contains("delivery") || userMessage.contains("shipping") ||
                userMessage.contains("order to home")) {
            return "We currently offer in-store pickup only. Delivery service is coming soon!";
        } else if (userMessage.contains("best seller") || userMessage.contains("recommendation")) {
            return "Our best-sellers are Classic Milk Tea, Matcha Milk Tea, and Vietnamese Iced Coffee!";
        } else {
            return "Thank you for your message! We'll get back to you as soon as possible. 🧋";
        }
    }
}
