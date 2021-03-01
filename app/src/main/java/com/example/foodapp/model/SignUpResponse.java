package com.example.foodapp.model;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
