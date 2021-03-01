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

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("userId")
    private String userId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

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
