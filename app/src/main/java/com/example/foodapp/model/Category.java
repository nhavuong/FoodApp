
package com.example.foodapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("cat_id")
    @Expose
    private int cat_id;

    @SerializedName("cat_name")
    @Expose
    private String cat_name;

    @SerializedName("cat_img")
    @Expose
    private String cat_img;
//    @SerializedName("rating")
//    @Expose
//    private String rating;
//    @SerializedName("deliveryTime")
//    @Expose
//    private String deliveryTime;
//    @SerializedName("deliveryCharges")
//    @Expose
//    private String deliveryCharges;
//    @SerializedName("price")
//    @Expose
//    private String price;
//    @SerializedName("note")
//    @Expose
//    private String note;


    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }

//    public String getRating() {
//        return rating;
//    }

//    public void setRating(String rating) {
//        this.rating = rating;
//    }
//
//    public String getDeliveryTime() {
//        return deliveryTime;
//    }
//
//    public void setDeliveryTime(String deliveryTime) {
//        this.deliveryTime = deliveryTime;
//    }
//
//    public String getDeliveryCharges() {
//        return deliveryCharges;
//    }

//    public void setDeliveryCharges(String deliveryCharges) {
//        this.deliveryCharges = deliveryCharges;
//    }

//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }

}
