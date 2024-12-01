package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class ViewTrips extends AppCompatActivity {

    FloatingActionButton addTripButton;
    RecyclerView recyclerView;
    TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_trips);


        addTripButton = findViewById(R.id.add_Button);
        recyclerView = findViewById(R.id.recycler_view);

        addTripButton.setOnClickListener((v) -> startActivity(new Intent(ViewTrips.this, AddTrip.class)));
        setUpRecyclerView();

    }

     void setUpRecyclerView() {
         Query query = Utility.getCollectionReferenceForTrips().orderBy("timestamp",Query.Direction.DESCENDING);
         FirestoreRecyclerOptions<Trip> options = new FirestoreRecyclerOptions.Builder<Trip>()
                 .setQuery(query,Trip.class).build();
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         tripAdapter = new TripAdapter(options, this);
         recyclerView.setAdapter(tripAdapter);
     }

    @Override
    protected void onStart() {
        super.onStart();
            tripAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tripAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tripAdapter.notifyDataSetChanged();
    }


    }
