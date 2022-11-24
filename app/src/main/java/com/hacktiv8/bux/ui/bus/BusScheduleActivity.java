package com.hacktiv8.bux.ui.bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
        binding.seats.setText(displayPassengers);
        binding.departure.setText(departure.getCity());
        binding.arrival.setText(arrival.getCity());
        binding.date.setText(date.format(calendar.getTime()));

        String from = binding.departure.getText().toString();
        String to = binding.arrival.getText().toString();

        rvTrip.setHasFixedSize(true);
        rvTrip.setLayoutManager(new LinearLayoutManager(this));
        getData(from, to);


    }

    private void getTripData(String departure, String arrival, String date, String availableSeat) {

        //TODO availableSeat ?
        db.collection("trip")
                .whereEqualTo("departCity", departure)
                .whereEqualTo("arrivalCity", arrival)
                .whereEqualTo("date", date)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tripList.clear();
                        // input data ke adapter
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                        //show empty state
                    }
                });
    }

    private void getData(String departure, String arrival) {

        //TODO availableSeat ?
        db.collection("trip")
                .whereEqualTo("departCity", departure)
                .whereEqualTo("arrivalCity", arrival)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tripList.clear();
                        // input data ke adapter
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Trip trip = documentSnapshot.toObject(Trip.class);
                            tripList.add(trip);
                        }
                        Log.d("AdminProduk", String.valueOf(tripList.size()));
                        tripAdapter = new TripAdapter(BusScheduleActivity.this, tripList);
                        tripAdapter.notifyDataSetChanged();
                        rvTrip.setAdapter(tripAdapter);

                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                        //show empty state
                    }
                });
    }


}