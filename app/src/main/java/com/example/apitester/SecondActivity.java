package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apitester.api.CallbackResponse;
import com.example.apitester.api.Controller;
import com.example.apitester.api.Service;
import com.example.apitester.middleware.Auth;
import com.example.apitester.model.TravelPlan;

import retrofit2.Call;
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
                auth.logout(new CallbackResponse<Service.Message>() {
                    @Override
                    public void onResponse(Call<Service.Message> call, Response<Service.Message> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(SecondActivity.this, "Logout Successful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                redirect();
            });
            findViewById(R.id.travelPlansButton).setOnClickListener(v -> {
//                Controller.getTravelPlans(auth).enqueue(new CallbackResponse<ArrayList<TravelPlan>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<TravelPlan>> call, Response<ArrayList<TravelPlan>> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            response.body().forEach(t -> Log.e(SECOND_KEY, t.toString()));
//                        } else {
//                            Log.d(SECOND_KEY, response.toString());
//                            Log.e(SECOND_KEY, "Empty");
//                        }
//                    }
//                });
                TravelPlan.createTravelPlan(auth, "Going Home1111", Controller.fakeDate, Controller.fakeDate, new CallbackResponse<TravelPlan>() {
                    @Override
                    public void onResponse(Call<TravelPlan> call, Response<TravelPlan> response) {
                        assert response.body() != null;
                        outputTextView.setText(response.body().toString());
                        Toast.makeText(SecondActivity.this, "Travel Plan Created",Toast.LENGTH_LONG);
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