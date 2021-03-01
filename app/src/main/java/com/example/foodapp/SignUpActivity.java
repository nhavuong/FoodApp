package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.model.SignUpResponse;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    CircularProgressIndicator progress_circular;
    EditText emailEditText, passwordEditText, usernameEditText, phoneEditText;
    Button signUpBtn;
    TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progress_circular = findViewById(R.id.progress_circular);
        usernameEditText = findViewById(R.id.usernameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        messageTextView = findViewById(R.id.messageTextView);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
                progress_circular.setVisibility(View.VISIBLE);
            }
        });
    }

    private void performSignUp() {
        String userName = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        Call<SignUpResponse> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class)
                .performSignUp(userName, email, password, phone);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    } else if (response.body().getStatus().equals("exists")) {
                        displaymessage("Existing Email Address");
                    } else {
                        displaymessage("Something went wrong");
                    }

                } else {
                    displaymessage("Server is down!");
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

            }
        });
    }

    private void displaymessage(String message) {
        passwordEditText.setText("");
        progress_circular.setVisibility(View.INVISIBLE);
        messageTextView.setText(message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}