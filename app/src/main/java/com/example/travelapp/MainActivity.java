package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    Button AddHolidayNav, ViewHolidaysNav, AboutNav, LogOutNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        AddHolidayNav = findViewById(R.id.addButton);
        ViewHolidaysNav = findViewById(R.id.viewButton);
        AboutNav = findViewById(R.id.aboutNav);
        LogOutNav = findViewById(R.id.logout_button);

        AddHolidayNav.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, AddTrip.class)));
        ViewHolidaysNav.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, ViewTrips.class)));
        AboutNav.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));
        LogOutNav.setOnClickListener((v) -> logOutUser());

    }

    private void logOutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,HomescreenActivity.class));
        finish();

    }
}
