package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apitester.api.API;
import com.example.apitester.api.Controller;
import com.example.apitester.middleware.Auth;
import com.example.apitester.model.TravelPlan;

public class SecondActivity extends AppCompatActivity {
    private Auth auth;

    @SuppressLint("SetTextI18n")
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
                auth.logout(msg -> Toast.makeText(SecondActivity.this, "Logout Successful", Toast.LENGTH_LONG).show());
                redirect();
            });

            findViewById(R.id.travelPlansButton).setOnClickListener(v -> {
                outputTextView.setText("Renewing");
                API.TravelPlans.create(auth, new TravelPlan.Create("Going Home1111", Controller.fakeDate, Controller.fakeDate))
                        .setOnResponse(travelPlan -> {
                            outputTextView.setText(travelPlan.toString());
                            Toast.makeText(this, "Travel Plan Created", Toast.LENGTH_LONG).show();
                        })
                        .setOnFailure(response -> Log.e("Debugger", response.toString()))
                        .fetch();
            });
        }
    }

    private void redirect() {
        Log.i("Nav", "Going to Main");
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
}