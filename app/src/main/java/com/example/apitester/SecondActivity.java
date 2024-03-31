package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.apitester.api.Controller;
import com.example.apitester.middleware.Auth;
import com.example.apitester.model.TravelPlans;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = Auth.getInstance(getSharedPreferences("com.example.android.travelplanner", MODE_PRIVATE));
        if (!auth.isAuth()) redirect();
        else {
            setContentView(R.layout.activity_second);
            TextView messageView = findViewById(R.id.messageView);
            messageView.setText("Welcome " + auth.getUsername() + "!\uD83E\uDD73");
            findViewById(R.id.logoutButton).setOnClickListener(v -> {
                auth.logout();
                redirect();
            });
            findViewById(R.id.travelPlansButton).setOnClickListener(v-> {
                Controller.getTravelPlans(auth).enqueue(new Callback<ArrayList<TravelPlans>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TravelPlans>> call, Response<ArrayList<TravelPlans>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            response.body().forEach(t -> Log.e("Second", t.toString()));
                        } else {
                            Log.d("Second", response.toString());
                            Log.e("Second", "Empty");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TravelPlans>> call, Throwable throwable) {
                        Log.e("Second", throwable.toString());
                    }
                });
            });
        }
    }

    private void redirect() {
        Log.i("Nav", "Going to Main");
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
}