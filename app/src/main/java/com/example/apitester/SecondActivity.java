package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.apitester.api.Controller;
import com.example.apitester.middleware.Auth;
import com.example.apitester.model.TravelPlan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {
    private Auth auth;

    private String SECOND_KEY = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = Auth.getInstance(getSharedPreferences("com.example.android.travelplanner", MODE_PRIVATE));
        if (!auth.isAuth()) redirect();
        else {
            setContentView(R.layout.activity_second);
            TextView messageView = findViewById(R.id.messageView);
            TextView outputTextView = findViewById(R.id.outputTextView);

            messageView.setText("Welcome " + auth.getUsername() + "!\uD83E\uDD73");
            findViewById(R.id.logoutButton).setOnClickListener(v -> {
                auth.logout();
                redirect();
            });
            findViewById(R.id.travelPlansButton).setOnClickListener(v-> {
                Controller.getTravelPlans(auth).enqueue(new Callback<ArrayList<TravelPlan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TravelPlan>> call, Response<ArrayList<TravelPlan>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            response.body().forEach(t -> Log.e(SECOND_KEY, t.toString()));
                        } else {
                            Log.d(SECOND_KEY, response.toString());
                            Log.e(SECOND_KEY, "Empty");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TravelPlan>> call, Throwable throwable) {
                        Log.e("Second", throwable.toString());
                    }
                });

                Controller.getTravelPlans(auth,"0").enqueue(new Callback<TravelPlan>() {
                    @Override
                    public void onResponse(Call<TravelPlan> call, Response<TravelPlan> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            outputTextView.setText(response.body().toString());
                        } else {
                            Log.d(SECOND_KEY, response.toString());
                            int code = response.code();
                            if(code == 404) {
                                Log.d(SECOND_KEY, "File Not Found");
                            }
                            Log.e(SECOND_KEY, "Code: " + code);
                        }
                    }

                    @Override
                    public void onFailure(Call<TravelPlan> call, Throwable throwable) {
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