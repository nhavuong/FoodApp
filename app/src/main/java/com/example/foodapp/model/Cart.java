package com.example.foodapp.model;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    public static int amount = 0;
    public static Map<String, Integer> quantity = new HashMap<>();
    public static ArrayList<Food> cart = new ArrayList<>();
}
