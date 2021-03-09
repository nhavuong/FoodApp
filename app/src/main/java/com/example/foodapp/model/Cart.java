package com.example.foodapp.model;

import java.util.ArrayList;

public class Cart {
    public static int amount = 0;
    public static double totalPrice;
    public static double totalTax;

    public static ArrayList<Food> cart = new ArrayList<>();

    public static double getTotalPrice(){
        double total = 0.0;
        for(Food food : Cart.cart){
            total += (Double)food.getFood_price() * food.getQuantity();
        }
        totalTax = Math.round((0.075 * total)*100)/100D;
        totalPrice = Math.round((totalTax + total)*100)/100D;
        return totalPrice;
    }

    public static double getTotalTax(){
        return totalTax;
    }

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
