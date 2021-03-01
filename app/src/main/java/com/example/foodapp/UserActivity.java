package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

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

    public void goToSignUp(View view) {
        Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(i);
    }
}