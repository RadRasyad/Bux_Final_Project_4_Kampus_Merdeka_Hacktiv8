package com.hacktiv8.bux.ui.home;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.FragmentHomeBinding;
import com.hacktiv8.bux.model.City;
import com.hacktiv8.bux.ui.bus.BusScheduleActivity;
import com.hacktiv8.bux.ui.chooser.DatePickerActivity;
import com.hacktiv8.bux.ui.chooser.DestinationChooserActivity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SimpleDateFormat format;
    private Calendar calendar;
    private SlidingUpPanelLayout slidingUp;
    private City departureCity;
    private  City arrivalCity;

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        format = new SimpleDateFormat(" dd MMM");
        slidingUp = requireActivity().findViewById(R.id.sliding_layout);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent intent = new Intent(getContext(), DestinationChooserActivity.class);
        binding.passangers.setOnClickListener(v ->{
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
//            View bottomSheetView = LayoutInflater.from(getContext())
//                    .inflate(R.layout.seat_sleding_up,
//                    .(LinearLayout)
        });
        binding.date.setOnClickListener(v ->{
            startActivityForResult(new Intent(getContext(), DatePickerActivity.class), 1);
        });
        binding.departure.setOnClickListener(v ->{
            startActivityForResult(intent, 2);
        });
        binding.arrival.setOnClickListener(v ->{
            startActivityForResult(intent, 3);
        });

        binding.btnSearch.setOnClickListener(v ->{
            onSearchActivity();
        });

        if(savedInstanceState != null){
            onStateData(savedInstanceState);
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("departure", departureCity);
        outState.putParcelable("arrival", arrivalCity);
        outState.putSerializable("date", calendar);
        outState.putString("passengers", binding.passangers.getText().toString());
    }

    private void onStateData(Bundle savedInstanceState) {
        calendar = (Calendar) savedInstanceState.getSerializable("date");
        if(calendar!=null) binding.date.setText(format.format(calendar.getTime()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onSearchActivity() {
        boolean txtDeparture = binding.departure.getText().equals(getString(R.string.str_try_departure));
        boolean txtArrival = binding.arrival.getText().equals(getString(R.string.str_try_arrival));
        boolean txtDate = binding.date.getText().equals(getString(R.string.str_select_date));

        if(!txtDeparture && !txtArrival && !txtDate) {
            startActivity(new Intent(getContext(), BusScheduleActivity.class)
                    .putExtra("departure", departureCity)
                    .putExtra("arrival", arrivalCity)
                    .putExtra("date", calendar)
                    .putExtra("passengers", binding.passangers.getText())
            );
        }else {
            getBoolean(txtDate, "Please select a date");
            getBoolean(txtDeparture, "Please select the departure city");
            getBoolean(txtArrival, "Please select the arrival city");
            getBoolean(txtDeparture && txtArrival && txtDate,
                    "please fill in the data completely");
        }
    }

    private void getBoolean(boolean txtDeparture, String message) {
        if (txtDeparture) {
            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(data != null && resultCode == RESULT_OK){
                    calendar = (Calendar) data.getSerializableExtra("date");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM");
                    binding.date.setText(simpleDateFormat.format(calendar.getTime()));
                }
                break;
            case 2:
                if(data != null && resultCode == RESULT_OK){
                    departureCity = data.getParcelableExtra("city");
                    binding.departure.setText(departureCity.getCity());
                }
                break;
            case 3:
                if(data != null && resultCode == RESULT_OK){
                    arrivalCity = data.getParcelableExtra("city");
                    binding.arrival.setText(arrivalCity.getCity());
                }
                break;


        }

    }
}