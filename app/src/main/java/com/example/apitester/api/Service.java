package com.example.apitester.api;

import com.example.apitester.model.Token;
import com.example.apitester.model.TravelPlans;
import com.example.apitester.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Service {
    //Auth Services
    @POST("/api/v1/auth/authenticate")
    Call<Token> authenticate(@Body User user);

    //TravelPlans Services
    @GET("/api/v1/travelplans")
    Call<ArrayList<TravelPlans>> getTravelPlans(@Header("Authorization") String token);
}