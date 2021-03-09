package com.example.foodapp.model;

import java.util.ArrayList;

public class Cart {
    public static int amount = 0;
    public static ArrayList<Food> cart = new ArrayList<>();

    public static int getAmount() {
        return amount;
    }

    public static void setAmount(int amount) {
        Cart.amount = amount;
    }

    public static ArrayList<Food> getCart() {
        return cart;
    }

    public static void setCart(ArrayList<Food> cart) {
        Cart.cart = cart;
    }
}
