package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.model.AddingResponse;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.SessionManagement;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailsActivity extends AppCompatActivity {

    ImageView imageView, checkCart;
    TextView itemName, itemPrice, itemDescription, itemCount;

    int id, cat_id, is_recommend, quantity;
    String name, imageUrl, description;
    Double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        Intent intent = getIntent();

        itemCount = findViewById(R.id.count);
        itemCount.setText(String.valueOf(Cart.amount));
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

        Glide.with(getApplicationContext()).load(RetrofitClient.BASE_URL + imageUrl).into(imageView);
        itemName.setText(name);
        itemPrice.setText("$ "+price);
        itemDescription.setText(description);

        checkCart = (ImageView)findViewById(R.id.imageView4);
        checkCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext() ,CartListActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        itemCount.setText(String.valueOf(Cart.amount));
    }

    //https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click

    public void addFood(final View view)
    {
        SessionManagement sessionManagement = new SessionManagement(view.getContext());
        int userId = sessionManagement.getSession();

        if(userId != -1){
            Call<AddingResponse> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class).addToCart(userId, id);
            call.enqueue(new Callback<AddingResponse>() {
                @Override
                public void onResponse(Call<AddingResponse> call, Response<AddingResponse> response) {
                    if(response.isSuccessful()){
                        boolean existed = false;
                        for (Food f :
                                Cart.cart) {
                            if (f.getFood_name().equals(name)){
                                existed = true;
                                f.setQuantity(f.getQuantity()+1);
                                Cart.amount++;
                                break;
                            }
                        }
                        if(!existed){
                            Food food = new Food(id,name,description,price,imageUrl,cat_id,is_recommend, 1);
                            Cart.cart.add(food);
                            Cart.amount++;
                        }
                        itemCount.setText(String.valueOf(Cart.amount));
//                        Toast.makeText(view.getContext(), "food added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(view.getContext(), "food not added", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddingResponse> call, Throwable t) {
                    Toast.makeText(view.getContext(), "food not added", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(view.getContext(), "need to login", Toast.LENGTH_SHORT).show();
        }

        itemCount.setText(String.valueOf(Cart.amount));
    }
}
