package com.example.apitester.api;

import retrofit2.Response;

public interface Failure<T> {
    void onFailure(Response<T> response);
}
