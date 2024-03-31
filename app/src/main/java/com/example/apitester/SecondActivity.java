package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.apitester.middleware.Auth;

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
        }
    }

    private void redirect() {
        Log.i("Nav", "Going to Main");
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
}