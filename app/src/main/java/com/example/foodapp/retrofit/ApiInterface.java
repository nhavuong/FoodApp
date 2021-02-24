package com.example.foodapp.retrofit;


import com.example.foodapp.model.Category;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("category")
    Call<List<Category>> getCategory();

}
