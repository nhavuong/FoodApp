package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.LoginResponse;
import com.example.foodapp.model.SessionManagement;
import com.example.foodapp.model.SignUpResponse;
import com.example.foodapp.model.User;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button signInBtn, signUpBtn, cartbtn, logoutbtn;
    TextView forgotPwd, welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        cartbtn = findViewById(R.id.cartbtn);
        logoutbtn = findViewById(R.id.logoutbtn);
        forgotPwd = findViewById(R.id.forgotPwd);
        welcomeMessage = findViewById(R.id.welcomeMessage);

    }

    @Override
    protected void onStart(){
        super.onStart();

        SessionManagement sessionManagement = new SessionManagement(UserActivity.this);
        int userId = sessionManagement.getSession();
        if(userId != -1){
            emailEditText.setVisibility(View.INVISIBLE);
            passwordEditText.setVisibility(View.INVISIBLE);

            signInBtn.setVisibility(View.INVISIBLE);
            signUpBtn.setVisibility(View.INVISIBLE);
            forgotPwd.setVisibility(View.INVISIBLE);

            logoutbtn.setVisibility(View.VISIBLE);
            cartbtn.setVisibility(View.VISIBLE);
            welcomeMessage.setVisibility(View.VISIBLE);
            welcomeMessage.setText("Hello, " + sessionManagement.getName());
        }
        else{
            emailEditText.setVisibility(View.VISIBLE);
            passwordEditText.setVisibility(View.VISIBLE);

            signInBtn.setVisibility(View.VISIBLE);
            signUpBtn.setVisibility(View.VISIBLE);
            forgotPwd.setVisibility(View.VISIBLE);

            logoutbtn.setVisibility(View.INVISIBLE);
            cartbtn.setVisibility(View.INVISIBLE);
            welcomeMessage.setVisibility(View.INVISIBLE);
//            welcomeMessage.setText("Hello, " + sessionManagement.getName());
        }
    }

    public void goToSignUp(View view) {
        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(i);
    }

    public void login(View view) {
        String email = emailEditText.getText().toString();
        String pwd = passwordEditText.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(UserActivity.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(!email.isEmpty() && pwd.isEmpty()){
            Toast.makeText(UserActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(!email.isEmpty() && !pwd.isEmpty()){
            Call<LoginResponse> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class)
                    .performLogin(email, pwd);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    String status = loginResponse.getStatus();
                    String message = loginResponse.getMessage();

                    if(status.equals("success")) {
                        int id = loginResponse.getUserId();
                        String name = loginResponse.getName();
                        String email = loginResponse.getEmail();

                        Gson gson = new Gson();
                        Type foodListType = new TypeToken<ArrayList<Food>>(){}.getType();
                        Cart.cart = gson.fromJson(loginResponse.getCart().getAsString(), foodListType);

                        Cart.amount = 0;
                        for (Food food:
                             Cart.cart) {
                            Cart.amount += food.getQuantity();
                        }

                        User user = new User(id, email, name);
                        SessionManagement sessionManagement = new SessionManagement(UserActivity.this);
                        sessionManagement.saveSession(user);

                        moveToMain();
                    }
                    else if(status.equals("fail")){
                        if(message.equals("wrong password")){
                            Toast.makeText(UserActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                        }
                        else if(message.equals("not existing email")){
                            Toast.makeText(UserActivity.this, "Not Existing Email, Register first", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                    Toast.makeText(UserActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void moveToMain(){
        Intent i = new Intent(UserActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void logout(View view) {
        SessionManagement sessionManagement = new SessionManagement(UserActivity.this);
        sessionManagement.removeSession();
        Cart.cart.clear();
        Cart.amount = 0;
        moveToMain();
    }

    public void goToCart(View view) {
        Intent i = new Intent(view.getContext(), CartListActivity.class);
        startActivity(i);
    }
}