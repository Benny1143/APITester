package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apitester.api.API;
import com.example.apitester.middleware.Auth;

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
                API.Event.delete(auth, "1", "9")
                        .setOnResponse(res -> {
                            Toast.makeText(this, "Event Deleted", Toast.LENGTH_LONG).show();
                        })
                        .setOnFailure(response -> Log.e("Debugger", response.toString()))
                        .fetch();
//                ZonedDateTime dateTime = ZonedDateTime.now();
//                EventModel.Create c = new EventModel.Create("Breakfast", dateTime.plusDays(23), dateTime.plusDays(24), "Food", EventModel.Status.CONCRETE, "Hostel");
//                Log.e("Debugger", c.toString());
//                API.Event.create(auth, "1", c)
//                        .setOnResponse(eventResponse -> {
//                            outputTextView.setText(eventResponse.getEvent().toString());
//                            Toast.makeText(this, "Event Created", Toast.LENGTH_LONG).show();
//                        })
//                        .setOnFailure(response -> Log.e("Debugger", response.toString()))
//                        .fetch();


//                API.TravelPlans.create(auth, new TravelPlan.Create("Going Home1111", Controller.fakeDate, Controller.fakeDate))
//                        .setOnResponse(travelPlan -> {
//                            outputTextView.setText(travelPlan.toString());
//                            Toast.makeText(this, "Travel Plan Created", Toast.LENGTH_LONG).show();
//                        })
//                        .setOnFailure(response -> Log.e("Debugger", response.toString()))
//                        .fetch();
            });
        }
    }

    private void redirect() {
        Log.i("Nav", "Going to Main");
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
}