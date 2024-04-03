package com.example.apitester.api;

public interface Response<T> {
    void onResponse(T response);
}
