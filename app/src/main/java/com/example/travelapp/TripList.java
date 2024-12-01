package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripList extends AppCompatActivity {

    FloatingActionButton addTrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip_list);

        addTrip = findViewById(R.id.add_trip);

        addTrip.setOnClickListener((v) -> startActivity(new Intent(TripList.this, AddTrip.class)));
    }
}