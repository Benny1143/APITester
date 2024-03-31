package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apitester.api.Controller;
import com.example.apitester.model.Token;
import com.example.apitester.model.User;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextTextPassword;
    Button login;
    TextView token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.login);
        token = findViewById(R.id.token);

        login.setOnClickListener(v -> {
            editTextUsername.setEnabled(false);
            editTextTextPassword.setEnabled(false);
            String username = editTextUsername.getText().toString();
            String password = editTextTextPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                if (username.isEmpty()) Toast.makeText(MainActivity.this, "Username Empty", Toast.LENGTH_LONG).show();
                else Toast.makeText(MainActivity.this, "Password Empty", Toast.LENGTH_LONG).show();
                editTextUsername.setEnabled(true);
                editTextTextPassword.setEnabled(true);
            } else {
                Controller.getService().authenticate(new User(username, password)).enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Token t = response.body();
                            token.setText(t.getToken());
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            Log.i("Auth", t.getToken());
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong Username/Password", Toast.LENGTH_LONG).show();
                            Log.e("Auth", response.toString());
                        }
                        editTextUsername.setEnabled(true);
                        editTextTextPassword.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable throwable) {
                        Log.e("Auth", throwable.toString());
                        Log.e("Auth", "Fail");
                    }
                });

            }
        });
    }
}