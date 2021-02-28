package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;

public class FoodDetailsActivity extends AppCompatActivity {

    ImageView imageView, checkCart;
    TextView itemName, itemPrice, itemDescription, itemCount;
    //RatingBar ratingBar;

    int id, cat_id, is_recommend;
    String name, rating, imageUrl, description;
    Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        Intent intent = getIntent();

        itemCount = findViewById(R.id.count);
        itemCount.setText(String.valueOf(Cart.cart.size()));
        id = intent.getIntExtra("food_id",0);
        name = intent.getStringExtra("food_name");
        price = intent.getDoubleExtra("food_price",0.0);
        //rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("food_img");
        description = intent.getStringExtra("food_description");
        cat_id = intent.getIntExtra("cat_id",0);
        is_recommend = intent.getIntExtra("is_recommend",0);

        imageView = findViewById(R.id.imageView5);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.price);
        itemDescription = findViewById(R.id.description);
        //itemRating = findViewById(R.id.rating);
        //ratingBar = findViewById(R.id.ratingBar);

        String BASE_URL = "http://foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/";
        String BASE_URL2 = "http://foodapp-env.eba-idm3cpsj.us-east-2.elasticbeanstalk.com/";

        Glide.with(getApplicationContext()).load(BASE_URL2 + imageUrl).into(imageView);
        itemName.setText(name);
        itemPrice.setText("$ "+price);
        itemDescription.setText(description);
        //itemRating.setText(rating);
        //ratingBar.setRating(Float.parseFloat(rating));

        checkCart = (ImageView)findViewById(R.id.imageView4);
        checkCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext() ,CartListActivity.class);
                startActivity(i);
            }
        });

    }

    //https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click

    public void addFood(View view)
    {
        Food food = new Food(id,name,description,price,imageUrl,cat_id,is_recommend);
        Cart.cart.add(food);
        itemCount.setText(String.valueOf(Cart.cart.size()));
    }
}
