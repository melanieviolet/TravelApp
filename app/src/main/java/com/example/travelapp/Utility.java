package com.example.travelapp;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    static CollectionReference getCollectionReferenceForTrips(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        return FirebaseFirestore.getInstance().collection("trips")
                .document(currentUser.getUid()).collection("my_trips");
    }



    static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("DD/MM/YYYY").format(timestamp.toDate());
    }

}

