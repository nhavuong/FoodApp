package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.model.SessionManagement;
import com.example.foodapp.model.SignUpResponse;
import com.example.foodapp.model.User;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    CircularProgressIndicator progress_circular;
    EditText emailEditText, passwordEditText, usernameEditText, phoneEditText, confirmPasswordEditText;
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
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        messageTextView = findViewById(R.id.messageTextView);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignUp();
//                progress_circular.setVisibility(View.VISIBLE);
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void performSignUp() {
        final String name = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmedPassword = confirmPasswordEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if(!isEmailValid(email)){
            emailEditText.setText("");
            emailEditText.setHint("Invalid Email Address!");
            emailEditText.setHintTextColor(Color.RED);
        }
        else if(!password.equals(confirmedPassword)){
            passwordEditText.setText("");
            passwordEditText.setHint("Passwords are not match!");
            passwordEditText.setHintTextColor(Color.RED);

            confirmPasswordEditText.setText("");
            confirmPasswordEditText.setHint("Passwords are not match!");
            confirmPasswordEditText.setHintTextColor(Color.RED);
        }
        else if(phone.length() != 10){
            phoneEditText.setText("");
            phoneEditText.setHint("Invalid phone number!");
            phoneEditText.setHintTextColor(Color.RED);
        }
        else {
            Call<SignUpResponse> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class)
                    .performSignUp(name, email, password, phone);
            call.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("success")) {
                            int id = Integer.valueOf(response.body().getUserId());
                            User user = new User(id, email, name);
                            SessionManagement sessionManagement = new SessionManagement(SignUpActivity.this);
                            sessionManagement.saveSession(user);
//                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_LONG).show();
                            onBackPressed();
                            finish();
                        } else if (response.body().getStatus().equals("exists")) {
                            emailEditText.setText("");
                            emailEditText.setHint("Existing Email Address");
                            emailEditText.setHintTextColor(Color.RED);
                            emailEditText.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
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