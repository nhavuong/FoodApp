package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.adapter.AllMenuAdapter;

import com.example.foodapp.adapter.CategoryAdapter;
import com.example.foodapp.adapter.RecommendedAdapter;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.Category;

import com.example.foodapp.model.Recommended;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;

    private static final String TAG = "MAINACTIVITY";
    RecyclerView categoryRecyclerView, recommendedRecyclerView, allMenuRecyclerView;

    EditText search_bar;
    CategoryAdapter categoryAdapter;
    RecommendedAdapter recommendedAdapter;
    AllMenuAdapter allMenuAdapter;

    ImageView checkCart;
    TextView itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemCount = findViewById(R.id.count);
        itemCount.setText(String.valueOf(Cart.amount));
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        connectCategory();
        connectRecommend();
        search_bar = findViewById(R.id.editText);
        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // https://stackoverflow.com/questions/3205339/android-how-to-make-keyboard-enter-button-say-search-and-handle-its-click
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i(TAG, "search now : " + search_bar.getText().toString());
                    if (search_bar.getText().toString().length() != 0) {
                        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                        intent.putExtra("searchKeyword", search_bar.getText().toString());
                        startActivity(intent);
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });

        checkCart = (ImageView) findViewById(R.id.imageView4);
        checkCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CartListActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        itemCount.setText(String.valueOf(Cart.amount));
    }

    private void connectCategory() {
        Call<List<Category>> call = apiInterface.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                populateCategoryData(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(call.request());
                Log.e(TAG, t.getMessage());
            }
        });

        // try Food type here.
//        Call<List<Food>> call = apiInterface.getCategory();
//        call.enqueue(new Callback<List<Food>>() {
//            @Override
//            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
//                populateCategoryData(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Food>> call, Throwable t) {
//                System.out.println(call.request());
//                Log.e(TAG, t.getMessage());
//            }
//        });

    }

    private void connectRecommend() {
        Call<List<Food>> call = apiInterface.getRecommend();
        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                populateRecommendedData(response.body());
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                System.out.println(call.request());
                Log.e(TAG, t.getMessage());
            }
        });

    }

    private void populateCategoryData(List<Category> categoryList){

        categoryRecyclerView = findViewById(R.id.category_recycler);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    // try Food type.
//    private void populateCategoryData(List<Food> categoryList){
//        categoryRecyclerView = findViewById(R.id.category_recycler);
//        categoryAdapter = new CategoryAdapter(this, categoryList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        categoryRecyclerView.setLayoutManager(layoutManager);
//        categoryRecyclerView.setAdapter(categoryAdapter);
//    }


    private void populateRecommendedData(List<Food> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }

    public void goToUser(View view) {
        Intent i = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(i);
    }
}
