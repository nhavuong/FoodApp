package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.adapter.MenuAdapter;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.Menu;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvName, tvDescription, tvPrice;

    int cat_id, food_id, is_recommend;
    String name, description, imageUrl;
    Double price;

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
        food_id = intent.getIntExtra("food_id",0);
        name = intent.getStringExtra("food_name");
        price = intent.getDoubleExtra("food_price",0.0);
        description = intent.getStringExtra("food_description");
        cat_id = intent.getIntExtra("cat_id", 0);


        // Capture the layout's TextView and set the string as its text
        tvName = findViewById(R.id.food_name);
        tvDescription = findViewById(R.id.food_description);
        tvPrice = findViewById(R.id.food_price);

        populateMenu(Menu.menu);

    }

    private void populateMenu(List<Food> menuList) {

        recyclerView = findViewById(R.id.recycler_menu);
        menuAdapter = new MenuAdapter(menuList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuAdapter);

    }

    // maybe I can just call the function by Nina, instead of making a new one here.
//    public void addFood(View view)
//    {
//        Food food = new Food(food_id,name,description,price,imageUrl,cat_id,is_recommend);
//        Cart.cart.add(food);
//    }

}
