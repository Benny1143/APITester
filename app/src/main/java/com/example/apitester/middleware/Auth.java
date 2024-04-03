package com.example.apitester.middleware;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.apitester.api.Controller;
import com.example.apitester.api.Service;
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
    private final String AUTH_CODE = "Auth";

    private Auth(android.content.SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
        refresh();
    }

    public static Auth getInstance(android.content.SharedPreferences sharedPreferences) {
        if (instance == null) instance = new Auth(sharedPreferences);
        return instance;
    }

    public boolean isAuth() {
        //TODO: Check if token is valid
        return token != null;
    }

    private void refresh() {
        token = mPreferences.getString(TOKEN_KEY, null);
        username = mPreferences.getString(USERNAME_KEY, null);
        Log.e("Auth:refresh", username + " " + token);
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
                Log.e(AUTH_CODE, throwable.toString());
                Log.e(AUTH_CODE, "Fail");
                callback.onFailure(call, throwable);
            }
        });
    }

    public void register(String username, String password, String email, Callback<Token> callback) {
        Controller.getService().register(new User(username, password, email)).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveUserAndToken(username, response.body().getToken());
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                Log.e(AUTH_CODE, throwable.toString());
                Log.e(AUTH_CODE, "Fail");
                callback.onFailure(call, throwable);
            }
        });
    }

    public void logout(Callback<Service.Message> callback) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(TOKEN_KEY, null);
        preferencesEditor.putString(USERNAME_KEY, null);
        preferencesEditor.apply();
        Log.i(AUTH_CODE, "Logging out");
        Log.i(AUTH_CODE, token);
        String tokenHolder = token;
        token = null;
        username = null;
        Controller.getService().logOut(getToken(tokenHolder)).enqueue(new Callback<Service.Message>() {
            @Override
            public void onResponse(Call<Service.Message> call, Response<Service.Message> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.i(AUTH_CODE, "Log out Successful");
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Service.Message> call, Throwable throwable) {
                Log.e("Auth", throwable.toString());
                Log.e("Auth", "Fail");
                callback.onFailure(call, throwable);
            }
        });
    }

//    public static void responseDebugger(Response<Object> response) {
//        Log.i("Debugger", response.raw().request().toString());
//        Log.i("Debugger", response.raw().request().headers().toString());
//    }

    public String getUsername() {
        return Auth.username;
    }

    public String getToken() {
        return "Bearer " + getTokenRaw();
    }

    public String getToken(String token) {
        return "Bearer " + token;
    }

    public String getTokenRaw() {
        return token;
    }
}
