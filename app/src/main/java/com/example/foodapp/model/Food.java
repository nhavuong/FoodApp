package com.example.foodapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("food_id")
    @Expose
    private int food_id;

    @SerializedName("food_name")
    @Expose
    private String food_name;

    @SerializedName("food_description")
    @Expose
    private String food_description;

    @SerializedName("food_price")
    @Expose
    private double food_price;

    @SerializedName("food_img")
    @Expose
    private String food_img;

    @SerializedName("cat_id")
    @Expose
    private int cat_id;

    @SerializedName("is_recommend")
    @Expose
    private int is_recommend;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    public Food(int food_id, String food_name, String food_description, double food_price, String food_img, int cat_id, int is_recommend, int quantity) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_description = food_description;
        this.food_price = food_price;
        this.food_img = food_img;
        this.cat_id = cat_id;
        this.is_recommend = is_recommend;
        this.quantity = quantity;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String Food_name) {
        this.food_name = food_name;
    }

    public String getFood_description() {
        return food_description;
    }

    public void setFood_description(String Food_description) {
        this.food_description = food_description;
    }

    public double getFood_price() {
        return food_price;
    }

    public void setFood_price(double food_price) {
        this.food_price = food_price;
    }

    public String getFood_img() {
        return food_img;
    }

    public void setFood_img(String Food_img) {
        this.food_img = food_img;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(int is_recommend) {
        this.is_recommend = is_recommend;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
