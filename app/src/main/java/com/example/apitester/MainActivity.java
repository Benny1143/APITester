package com.example.apitester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apitester.middleware.Auth;
import com.example.apitester.model.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextTextPassword;
    Button loginButton;
    TextView tokenView;
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = Auth.getInstance(getSharedPreferences("com.example.android.travelplanner", MODE_PRIVATE));
        if (auth.isAuth()) {
            redirect();
            return;
        }
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.login);
        tokenView = findViewById(R.id.token);

        loginButton.setOnClickListener(v -> {
            editTextUsername.setEnabled(false);
            editTextTextPassword.setEnabled(false);
            String username = editTextUsername.getText().toString();
            String password = editTextTextPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                if (username.isEmpty())
                    Toast.makeText(MainActivity.this, "Username Empty", Toast.LENGTH_LONG).show();
                else Toast.makeText(MainActivity.this, "Password Empty", Toast.LENGTH_LONG).show();
                editTextUsername.setEnabled(true);
                editTextTextPassword.setEnabled(true);
            } else {
                auth.login(username, password, new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            tokenView.setText(response.body().getToken());
                            redirect();
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong Username/Password", Toast.LENGTH_LONG).show();
                        }
                        editTextUsername.setEnabled(true);
                        editTextTextPassword.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable throwable) {
                    }
                });
            }
        });
    }

    private void redirect() {
        Log.i("Nav", "Going to Second");
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}