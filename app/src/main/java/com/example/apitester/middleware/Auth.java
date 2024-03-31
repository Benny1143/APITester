package com.example.apitester.middleware;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.apitester.api.Controller;
import com.example.apitester.model.Token;
import com.example.apitester.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Auth {
    private static String username;
    private static Auth instance;
    private static String token;
    private static SharedPreferences mPreferences;
    private final String TOKEN_KEY = "Token_key";
    private final String USERNAME_KEY = "Username_key";

    private Auth(android.content.SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
        refresh();
    }

    public static Auth getInstance(android.content.SharedPreferences sharedPreferences) {
        if (instance == null) instance = new Auth(sharedPreferences);
        return instance;
    }

    public boolean isAuth() {
        return token != null;
    }

    private void refresh() {
        token = mPreferences.getString(TOKEN_KEY, null);
        username = mPreferences.getString(USERNAME_KEY, null);
        Log.e("Auth:refresh", username + "" + token);
    }

    public void saveUserAndToken(String username, String token) {
        Auth.token = token;
        Auth.username = username;
        Log.e("Auth", username + " " + token);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(TOKEN_KEY, token);
        preferencesEditor.putString(USERNAME_KEY, username);
        preferencesEditor.apply();
    }

    public void login(String username, String password, Callback<Token> callback) {
        Controller.getService().authenticate(new User(username, password)).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveUserAndToken(username, response.body().getToken());
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                Log.e("Auth", throwable.toString());
                Log.e("Auth", "Fail");
                callback.onFailure(call, throwable);
            }
        });
    }

    public void logout() {
        token = null;
        username = null;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(TOKEN_KEY, null);
        preferencesEditor.putString(USERNAME_KEY, null);
        preferencesEditor.apply();
        //Todo: API Call to Logout
        //Todo: Callback function
    }

    public String getUsername(){
        return Auth.username;
    }

    public String getToken() {
        return token;
    }
}
