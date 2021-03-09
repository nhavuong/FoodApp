package com.example.foodapp.retrofit;


import com.example.foodapp.model.Category;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.LoginResponse;
import com.example.foodapp.model.SignUpResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("category")
    Call<List<Category>> getCategory();
    // try Food type.
//    @GET("category")
//    Call<List<Food>> getCategory();

    @GET("categoryfoods/{cat_id}")
    Call<List<Food>> getCategoryFood(@Path("cat_id") int cat_id);

    @GET("recommend")
    Call<List<Food>> getRecommend();

    @GET("search/{keyword}")
    Call<List<Food>> getSearchResult(@Path("keyword") String keyword);

    @FormUrlEncoded
    @POST("reg")
    Call<SignUpResponse> performSignUp(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("phone") String phone);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> performLogin(@Field("email") String email,
                                     @Field("password") String password);

    @GET("cart")
    Call<List<Food>> getCart(@Query("userid") int id);

    @FormUrlEncoded
    @POST("addToCart")
    Call<Integer> addToCart(@Field("userid") int id,
                            @Field("foodid") int foodid);
}
