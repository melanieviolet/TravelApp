package com.example.travelapp;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class TripAdapter extends FirestoreRecyclerAdapter<Trip, TripAdapter.TripViewHolder> {
    Context context;

    public TripAdapter(@NonNull FirestoreRecyclerOptions<Trip> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull TripViewHolder tripViewHolder, int i, @NonNull Trip trip) {
        tripViewHolder.titleTextView.setText(trip.title);
        tripViewHolder.locationTextView.setText(trip.location);
        tripViewHolder.timestampTextView.setText(Utility.timestampToString(trip.timestamp));


        tripViewHolder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,AddTrip.class);
            intent.putExtra("title",trip.title);
            intent.putExtra("start date",trip.startDate);
            intent.putExtra("end date",trip.endDate);
            intent.putExtra("location",trip.location);
            intent.putExtra("additional notes",trip.notes);
            String docId = this.getSnapshots().getSnapshot(i).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });


    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new TripViewHolder(view);
    }

    class TripViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView, locationTextView, timestampTextView;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_textview);
            locationTextView = itemView.findViewById(R.id.location_textview);
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);


        }
    }
}
