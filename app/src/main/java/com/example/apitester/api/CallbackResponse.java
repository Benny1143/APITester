package com.example.apitester.api;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class CallbackResponse<T> implements Callback<T> {
    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
        //TODO: Handle all the Failures
    }
}
