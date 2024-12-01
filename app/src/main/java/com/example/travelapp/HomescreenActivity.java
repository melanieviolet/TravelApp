package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomescreenActivity extends AppCompatActivity {

    Button buttonLogin;
    Button buttonReg;
    Button buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homescreen);

        buttonReg = findViewById(R.id.About);
        buttonLogin = findViewById(R.id.loginNav);
        buttonAbout = findViewById(R.id.aboutNav);

        buttonReg.setOnClickListener((v) -> startActivity(new Intent(HomescreenActivity.this, RegistrationActivity.class)));
        buttonLogin.setOnClickListener((v) -> startActivity(new Intent(HomescreenActivity.this, LoginActivity.class)));
        buttonAbout.setOnClickListener((v) -> startActivity(new Intent(HomescreenActivity.this, AboutActivity.class)));

    }
}