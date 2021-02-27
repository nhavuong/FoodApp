package com.example.foodapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/";
    private static final String BASE_URL2 = "http://foodapp-env.eba-idm3cpsj.us-east-2.elasticbeanstalk.com/";


    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL2)
                        .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }
    // http://foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/category
}
