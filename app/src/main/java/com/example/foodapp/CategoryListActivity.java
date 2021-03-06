package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.adapter.MenuAdapter;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.Menu;
import com.example.foodapp.model.SignUpResponse;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvName, tvDescription, tvPrice;

    int cat_id;


    // the adapter is going to add contents raw by raw in this activity.
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_food_details);
        // new layout here.
        setContentView(R.layout.new_category_recycler_items);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        cat_id = intent.getIntExtra("cat_id", 0);

        // data for adding a food.
//        food_id = intent.getIntExtra("food_id",0);
//        name = intent.getStringExtra("food_name");
//        price = intent.getDoubleExtra("food_price",0.0);
//        description = intent.getStringExtra("food_description");
//        imageUrl = intent.getStringExtra("food_img");
//        is_recommend = intent.getIntExtra("is_recommend", 0);


        // Capture the layout's TextView and set the string as its text
//        tvName = findViewById(R.id.food_name);
//        tvDescription = findViewById(R.id.food_description);
//        tvPrice = findViewById(R.id.food_price);
        recyclerView = findViewById(R.id.recycler_menu);
        // put all data
        Call<List<Food>> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class)
                .getCategoryFood(cat_id);

        call.enqueue(new Callback<List<Food>>() {
             @Override
             public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                 populateMenu(response.body());
             }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

            }
        });

    }

    private void populateMenu(List<Food> menuList) {

//        recyclerView = findViewById(R.id.recycler_menu);
        // divide line between foods
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        menuAdapter = new MenuAdapter(menuList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuAdapter);

    }



}
