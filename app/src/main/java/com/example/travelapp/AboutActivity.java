package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutActivity extends AppCompatActivity {

    Button backButton = findViewById(R.id.ok_button);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);


        backButton.setOnClickListener((v) -> homescreenDecider());
    }

    private void homescreenDecider() {
        FirebaseUser loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
        if (loggedInUser == null) {
            startActivity(new Intent(AboutActivity.this, HomescreenActivity.class));
        } else {
            startActivity(new Intent(AboutActivity.this, MainActivity.class));
        }

    }
}

