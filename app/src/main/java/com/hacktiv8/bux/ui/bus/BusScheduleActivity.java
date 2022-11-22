package com.hacktiv8.bux.ui.bus;

import static android.icu.lang.UCharacter.toUpperCase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.ActivityBusScheduleBinding;
import com.hacktiv8.bux.databinding.ActivityDestinationChooserBinding;
import com.hacktiv8.bux.model.City;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BusScheduleActivity extends AppCompatActivity {

    private ActivityBusScheduleBinding binding;
    private City departure;
    private City arrival;
    private Calendar calendar;
    private String passengers;
    private SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        departure = getIntent().getParcelableExtra("departure");
        arrival = getIntent().getParcelableExtra("arrival");
        passengers = getIntent().getStringExtra("passengers");
        calendar =  (Calendar)getIntent().getSerializableExtra("date");
        format = new SimpleDateFormat("EEE, d MMM yyyy");

        String displayPassengers = "Seat " +passengers;
        binding.seats.setText(displayPassengers);
        binding.departure.setText(toUpperCase(departure.getCity()));
        binding.arrival.setText(toUpperCase(arrival.getCity()));
        binding.date.setText(format.format(calendar.getTime()));



    }
}