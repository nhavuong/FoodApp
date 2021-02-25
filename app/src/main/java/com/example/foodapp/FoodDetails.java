package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FoodDetails extends AppCompatActivity {

    ImageView imageView;
    TextView itemName, itemPrice, itemDescription;
    //RatingBar ratingBar;

    int id;
    String name, rating, imageUrl, description;
    Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        Intent intent = getIntent();

        name = intent.getStringExtra("food_name");
        price = intent.getDoubleExtra("food_price",0.0);
        //rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("food_img");
        description = intent.getStringExtra("food_description");

        imageView = findViewById(R.id.imageView5);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemDescription = findViewById(R.id.description);
        //itemRating = findViewById(R.id.rating);
        //ratingBar = findViewById(R.id.ratingBar);

        String BASE_URL = "http://foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/";

        Glide.with(getApplicationContext()).load(BASE_URL + imageUrl).into(imageView);
        itemName.setText(name);
        itemPrice.setText("$ "+price);
        itemDescription.setText(description);
        //itemRating.setText(rating);
        //ratingBar.setRating(Float.parseFloat(rating));

    }
}
