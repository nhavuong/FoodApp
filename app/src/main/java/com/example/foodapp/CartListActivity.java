package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListActivity extends AppCompatActivity {

    private static final String TAG = "CARTLISTACTIVITY";

    RecyclerView recycle_cart;
    TextView total_price;
    Button btn_order;

    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);


        recycle_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        total_price = (TextView)findViewById(R.id.total_price);

        recycle_cart = findViewById(R.id.recycler_cart);
        total_price = findViewById(R.id.total_price);
        btn_order = findViewById(R.id.btn_order);

        populateCartList(Cart.cart);

    }

    private void populateCartList(List<Food> foodList){
        recycle_cart = findViewById(R.id.recycler_cart);
        cartAdapter = new CartAdapter(foodList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycle_cart.setLayoutManager(layoutManager);
        recycle_cart.setAdapter(cartAdapter);

    }

}