package com.example.foodapp.retrofit;


import com.example.foodapp.model.Category;
import com.example.foodapp.model.Food;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("category")
    Call<List<Category>> getCategory();

    @GET("recommend")
    Call<List<Food>> getRecommend();

    @GET("search/{keyword}")
    Call<List<Food>> getSearchResult(@Path("keyword") String keyword);
}
