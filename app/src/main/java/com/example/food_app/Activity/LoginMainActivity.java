package com.example.food_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.food_app.R;

public class LoginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        TextView buttonLogin = findViewById(R.id.buttonLogin);
        TextView textViewRegisterlink = findViewById(R.id.textViewRegisterlink);

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginMainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        textViewRegisterlink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginMainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}