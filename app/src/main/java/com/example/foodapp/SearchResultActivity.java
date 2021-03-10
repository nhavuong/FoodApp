package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.foodapp.adapter.CategoryAdapter;
import com.example.foodapp.adapter.SearchResultAdapter;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Category;
import com.example.foodapp.model.Food;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    static TextView countTv;
    private final String TAG = "SearchResultActivity";
    RecyclerView searchResultRecyclerView;
    SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        String searchKeyword = intent.getStringExtra("searchKeyword");
        countTv = findViewById(R.id.count);

        Call<List<Food>> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class).getSearchResult(searchKeyword);
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                populateCategoryData(new ArrayList<Food>(response.body()));
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.i(TAG, "API on failure");
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        countTv.setText(String.valueOf(Cart.amount));
    }

    private void populateCategoryData(ArrayList<Food> searchResultList){

        searchResultRecyclerView = findViewById(R.id.search_result_recyclerview);
        searchResultAdapter = new SearchResultAdapter(searchResultList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultRecyclerView.setAdapter(searchResultAdapter);
    }

    public void goToCart(View view) {
        Intent i = new Intent(view.getContext(), CartListActivity.class);
        startActivity(i);
    }

    public static void update_value() {
        countTv.setText(String.valueOf(Cart.amount));
    }
}