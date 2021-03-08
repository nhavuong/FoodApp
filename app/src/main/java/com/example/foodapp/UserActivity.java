package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.model.LoginResponse;
import com.example.foodapp.model.SessionManagement;
import com.example.foodapp.model.SignUpResponse;
import com.example.foodapp.model.User;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    CircularProgressIndicator progress_circular;
    EditText emailEditText, passwordEditText;
    Button signInBtn, signUpBtn;
    TextView forgotPwd, messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        progress_circular = findViewById(R.id.progress_circular);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        forgotPwd = findViewById(R.id.forgotPwd);
        messageTextView = findViewById(R.id.messageTextView);

    }

    @Override
    protected void onStart(){
        super.onStart();

        SessionManagement sessionManagement = new SessionManagement(UserActivity.this);
        int userId = sessionManagement.getSession();
        if(userId != -1){
            moveToMain();
        }
        else{
            // currently do nothing
            Toast.makeText(UserActivity.this, "Already logged in. Welcome.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToSignUp(View view) {
        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(i);
    }

    public void login(View view) {
        String email = emailEditText.getText().toString();
        String pwd = passwordEditText.getText().toString();
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

                    User user = new User(id, email, name);
                    SessionManagement sessionManagement = new SessionManagement(UserActivity.this);
                    sessionManagement.saveSession(user);

                    moveToMain();
                }
                else if(status.equals("fail")){
                    if(message.equals("wrong password")){
                        Toast.makeText(UserActivity.this, "Wrong password.", Toast.LENGTH_LONG).show();
                    }
                    else if(message.equals("not existing email")){
                        Toast.makeText(UserActivity.this, "Not Existing Email, Register first", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(UserActivity.this, "Bad Connection. Wait a moment and try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void moveToMain(){
        Intent i = new Intent(UserActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}