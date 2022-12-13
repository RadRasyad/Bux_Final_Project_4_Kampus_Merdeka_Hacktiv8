package com.hacktiv8.bux.ui.bus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hacktiv8.bux.databinding.ActivityBusScheduleBinding;
import com.hacktiv8.bux.model.City;
import com.hacktiv8.bux.model.Trip;
import com.hacktiv8.bux.ui.adapter.TripAdapter;
import com.hacktiv8.bux.ui.chooser.DestinationChooserActivity;
import com.hacktiv8.bux.utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusScheduleActivity extends AppCompatActivity {

    private ActivityBusScheduleBinding binding;
    private FirebaseFirestore db;
    private List<Trip> tripList = new ArrayList<>();
    private City departure;
    private City arrival;
    private String passengers, dep, arr;
    private TripAdapter tripAdapter;
    private RecyclerView rvTrip;
    private long timeInMillis;
    private boolean bolData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvTrip = binding.rvTrip;

        db = FirebaseFirestore.getInstance();

        departure = getIntent().getParcelableExtra("departure");
        arrival = getIntent().getParcelableExtra("arrival");
        passengers = getIntent().getStringExtra("passengers");
        timeInMillis = getIntent().getLongExtra("timeInMillis", 0);

        String displayPassengers = "Seat " +passengers;

        binding.seats.setText(displayPassengers);
        binding.departure.setText(departure.getCity());
        binding.arrival.setText(arrival.getCity());
        binding.date.setText(DateHelper.timestampToLocalDate3(timeInMillis));

        String from = binding.departure.getText().toString();
        dep = from;
        String to = binding.arrival.getText().toString();
        arr = to;

        rvTrip.setHasFixedSize(true);
        rvTrip.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = new Intent(getApplicationContext(), DestinationChooserActivity.class);
        binding.card1.setOnClickListener(v -> {
            startActivityForResult(intent, 1);
            bolData = true;
        });

        binding.card2.setOnClickListener(v -> {
            startActivityForResult(intent, 2);
            bolData = true;
        });

        if(savedInstanceState != null){
            onStateData(savedInstanceState);
        }

        getData(from, to);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("departure", departure);
        outState.putParcelable("arrival", arrival);

    }

    private void onStateData(Bundle savedInstanceState) {
        departure = savedInstanceState.getParcelable("departure");
        arrival = savedInstanceState.getParcelable("arrival");

        if(departure!=null) binding.departure.setText(departure.getCity());
        if(arrival!=null) binding.arrival.setText(arrival.getCity());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(data != null && resultCode == RESULT_OK){
                    departure = data.getParcelableExtra("city");
                    binding.departure.setText(departure.getCity());
                    dep = departure.getCity();
                    getData(dep, arr);

                }
                break;
            case 2:
                if(data != null && resultCode == RESULT_OK){
                    arrival = data.getParcelableExtra("city");
                    binding.arrival.setText(arrival.getCity());
                    arr = arrival.getCity();
                    getData(dep, arr);
                }
                break;
        }

    }

    private void getData(String departure, String arrival) {
        //Karena keterbatasan query jadi filter total seat yg tersedia tidak bisa diquery
        // hanya bisa 1 .wheregreaterThanOrEqualTo
        progressBar(true);
        db.collection("trip")
                .whereEqualTo("departCity", departure)
                .whereEqualTo("arrivalCity", arrival)
                .whereGreaterThanOrEqualTo("date", timeInMillis)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tripList.clear();

                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Trip trip = documentSnapshot.toObject(Trip.class);
                            tripList.add(trip);
                        }
                        emptyState(tripList.size() < 1);
                        progressBar(false);
                        tripAdapter = new TripAdapter(BusScheduleActivity.this, tripList);
                        tripAdapter.notifyDataSetChanged();
                        rvTrip.setAdapter(tripAdapter);

                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                        emptyState(true);
                    }
                });
    }

    private void progressBar(boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void emptyState(boolean state) {
        if (state) {
            binding.emptyState.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.emptyState.getRoot().setVisibility(View.GONE);
        }
    }



}