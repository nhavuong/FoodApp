package com.example.foodapp.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "id";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        int id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        editor.putInt(SESSION_KEY, id).commit();
//        editor.putString()
    }

    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

}
