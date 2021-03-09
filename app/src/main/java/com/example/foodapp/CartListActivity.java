package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    TextView total_price, tax_price;
    Button btn_order;

    Double total = 0.0;
    Double tax = 0.0;
    CartAdapter cartAdapter;
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        recycle_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        total_price = findViewById(R.id.total_price);
        tax_price = findViewById(R.id.tax);
        btn_order = findViewById(R.id.btn_order);



        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext() ,PaymentActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        for(Food food : Cart.cart){
            total += (Double)food.getFood_price() * food.getQuantity();
        }

        tax = Math.round((0.075 * total)*100)/100D;
        total = Math.round((tax + total)*100)/100D;
        tax_price.setText("$ " + tax);
        total_price.setText("$ " + total);
        populateCartList(Cart.cart);
    }

    private void populateCartList(List<Food> foodList){
        recycle_cart = findViewById(R.id.recycler_cart);
        cartAdapter = new CartAdapter(foodList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycle_cart.setLayoutManager(layoutManager);
        recycle_cart.setAdapter(cartAdapter);

    }



}