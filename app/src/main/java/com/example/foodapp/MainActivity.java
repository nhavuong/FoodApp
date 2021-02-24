package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodapp.adapter.AllMenuAdapter;

import com.example.foodapp.adapter.CategoryAdapter;
import com.example.foodapp.adapter.RecommendedAdapter;
import com.example.foodapp.model.Allmenu;
import com.example.foodapp.model.FoodData;
import com.example.foodapp.model.Category;

import com.example.foodapp.model.Recommended;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    RecyclerView categoryRecyclerView, recommendedRecyclerView, allMenuRecyclerView;

    CategoryAdapter categoryAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;

    static final String BASE_URL = "Foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/";
    static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        connectCategory();
//        Call<Category> call = apiInterface.getCategory();
//        call.enqueue(new Callback<Category>() {
//            @Override
//            public void onResponse(Call<List<FoodData>> call, Response<List<FoodData>> response) {
//
//                List<FoodData> foodDataList = response.body();
//
//
//                getCategoryData(foodDataList.get(0).getCategory());
//
//                getRecommendedData(foodDataList.get(0).getRecommended());
//
//                getAllMenu(foodDataList.get(0).getAllmenu());
//
//            }
//
//            @Override
//            public void onFailure(Call<List<FoodData>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void connectCategory() {
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<List<Category>> call = apiInterface.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                getCategoryData(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

    }

    private void getCategoryData(List<Category> categoryList){

        categoryRecyclerView = findViewById(R.id.category_recycler);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);

    }


    private void  getRecommendedData(List<Recommended> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }

    private void  getAllMenu(List<Allmenu> allmenuList){

        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        allMenuAdapter = new AllMenuAdapter(this, allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();

    }

}
