package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddTrip extends AppCompatActivity {

    Button doneButton;
    ImageButton addPhotos;
    EditText tripTitle, startDate, endDate, location, add_notes;
    TextView titleTextView, deleteButton;
    String tripTitle1,  startDate1, endDate1, location1, notes, docID;
    boolean isEditMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_trip);

        tripTitle = findViewById(R.id.trip_title);
        startDate = findViewById(R.id.startDateField);
        endDate = findViewById(R.id.endDateField);
        location = findViewById(R.id.locationField);
        add_notes = findViewById(R.id.additional_notes);
        titleTextView = findViewById(R.id.title_page);

        doneButton = findViewById(R.id.doneButton);
        deleteButton = findViewById(R.id.delete_textviewbutton);
        addPhotos = findViewById(R.id.imageButton);

        // addPhotos.setOnClickListener((v) -> startActivity(new Intent(AddTrip.this, AddPhoto.class)));

        tripTitle1 = getIntent().getStringExtra("title");
        startDate1 = getIntent().getStringExtra("startDate");
        endDate1 = getIntent().getStringExtra("endDate");
        location1 = getIntent().getStringExtra("location");
        notes = getIntent().getStringExtra("notes");
        docID = getIntent().getStringExtra("docID");

        if(docID!=null && !docID.isEmpty()){
            isEditMode = true;
            titleTextView.setText("Edit your trip");
            deleteButton.setVisibility(View.VISIBLE);

        }

        tripTitle.setText(tripTitle1);
        startDate.setText(startDate1);
        endDate.setText(endDate1);
        location.setText(location1);
        add_notes.setText(notes);



        doneButton.setOnClickListener((v) -> saveTrip());


        deleteButton.setOnClickListener((v -> deleteTrip()));


    }

    void saveTrip() {
        String tripTitle1 = tripTitle.getText().toString();
        String startDate1 = startDate.getText().toString();
        String endDate1 = endDate.getText().toString();


        SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
        Date startDateObject = null;
        Date endDateObject = null;

        try {
            // Parse the date strings into Date objects
            startDateObject = dateFormat.parse(startDate1);
            endDateObject = dateFormat.parse(endDate1);
        } catch (ParseException e) {
            // Set error message if the date format is invalid
            startDate.setError("Invalid start date format");
            endDate.setError("Invalid end date format");
            return;

        }

        String formattedStartDate = dateFormat.format(startDateObject);
        String formattedEndDate = dateFormat.format(endDateObject);


        String location1 = location.getText().toString();
        String notes = add_notes.getText().toString();

        // validation - make sure title and location are not empty
        if (tripTitle1.isEmpty()) {
            tripTitle.setError("Title is required");
            return;
        } else if (location1.isEmpty()) {
            location.setError("Location is required");
            return;
        }
        if (isEditMode) {
            // Fetch the existing trip object using docID
            DocumentReference documentReference = Utility.getCollectionReferenceForTrips().document(docID);
            documentReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Trip trip = task.getResult().toObject(Trip.class);
                    if (trip != null) {
                        // Update the trip object with new values
                        trip.setTitle(tripTitle1);
                        trip.setStartDate(formattedStartDate);
                        trip.setEndDate(formattedEndDate);
                        trip.setLocation(location1);
                        trip.setNotes(notes);
                        saveTripToFirebase(trip);
                    }
                }
            });
        } else {
            // (create a new trip object and save)
            Trip trip = new Trip();
            trip.setTitle(tripTitle1);
            trip.setStartDate(formattedStartDate);
            trip.setEndDate(formattedEndDate);
            trip.setLocation(location1);
            trip.setNotes(notes);
            trip.setTimestamp(Timestamp.now());
            saveTripToFirebase(trip);
        }

        }

    void saveTripToFirebase(Trip trip) {
        DocumentReference documentReference;

        if (isEditMode){
            documentReference = Utility.getCollectionReferenceForTrips().document(docID);
        } else {
            documentReference = Utility.getCollectionReferenceForTrips().document();
        }

        documentReference.set(trip).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // success,  trip added
                    Toast.makeText(AddTrip.this, "Trip added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //failure, trip not added
                    Toast.makeText(AddTrip.this, "Error whilst adding trip",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void deleteTrip(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForTrips().document(docID);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //trip deleted
                    Toast.makeText(AddTrip.this,"Trip deleted!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    // fail
                    Toast.makeText(AddTrip.this, "Error whilst deleting trip", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

