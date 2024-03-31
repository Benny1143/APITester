package com.example.apitester.api;

import com.example.apitester.model.Token;
import com.example.apitester.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Service {
    //Auth Services
    @POST("/api/v1/auth/authenticate")
    Call<Token> authenticate(@Body User user);

    //TravelPlans Services
}