package com.hacktiv8.bux.ui.bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.ActivityBusScheduleBinding;
import com.hacktiv8.bux.databinding.ActivityDestinationChooserBinding;
import com.hacktiv8.bux.model.City;
import com.hacktiv8.bux.model.Trip;
import com.hacktiv8.bux.ui.adapter.TripAdapter;

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
    private Calendar calendar;
    private String passengers;
    private SimpleDateFormat format, date;
    private TripAdapter tripAdapter;
    private RecyclerView rvTrip;

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
        calendar =  (Calendar)getIntent().getSerializableExtra("date");
        format = new SimpleDateFormat("EEE, d MMM yyyy");
        date = new SimpleDateFormat("d MMM yyyy");

        String displayPassengers = "Seat " +passengers;
        if (displayPassengers!=null) {
            binding.seats.setText(displayPassengers);
        }
        if (departure!=null) {
            binding.departure.setText(departure.getCity());
        }
        if (arrival!=null) {
            binding.arrival.setText(arrival.getCity());
        }
        if (calendar!=null) {
            binding.date.setText(date.format(calendar.getTime()));
        }

        String from = binding.departure.getText().toString();
        String to = binding.arrival.getText().toString();

        rvTrip.setHasFixedSize(true);
        rvTrip.setLayoutManager(new LinearLayoutManager(this));
        getData(from, to, Integer.parseInt(passengers));

    }

    private void getData(String departure, String arrival, int availableSeat) {

        progressBar(true);
        db.collection("trip")
                .whereEqualTo("departCity", departure)
                .whereEqualTo("arrivalCity", arrival)
                .whereGreaterThanOrEqualTo("seatAvailable", availableSeat)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tripList.clear();

                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Trip trip = documentSnapshot.toObject(Trip.class);
                            tripList.add(trip);
                        }
                        progressBar(false);
                        if (tripList.size()==0) {
                            binding.emptyState.getRoot().setVisibility(View.VISIBLE);
                        } else {
                            binding.emptyState.getRoot().setVisibility(View.INVISIBLE);
                        }
                        tripAdapter = new TripAdapter(BusScheduleActivity.this, tripList);
                        tripAdapter.notifyDataSetChanged();
                        rvTrip.setAdapter(tripAdapter);

                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                        binding.emptyState.getRoot().setVisibility(View.VISIBLE);
                        progressBar(false);
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

}