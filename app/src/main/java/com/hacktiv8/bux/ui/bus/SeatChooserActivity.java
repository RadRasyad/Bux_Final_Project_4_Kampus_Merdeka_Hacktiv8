package com.hacktiv8.bux.ui.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hacktiv8.bux.databinding.ActivitySeatChooserBinding;
import com.hacktiv8.bux.model.Seats;
import com.hacktiv8.bux.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class SeatChooserActivity extends AppCompatActivity {

    private ActivitySeatChooserBinding binding;
    private FirebaseFirestore db;
    public static final String EXTRA_TRIP_ID = "extra_trip_id";
    public static final String EXTRA_BUS_NO = "extra_bus_no";
    private List<Seats> seatsList = new ArrayList<>();
    private List<CheckedTextView> checkedTextViewList = new ArrayList<>();
    private String tripId;
    private String platBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeatChooserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSeatView();

        db = FirebaseFirestore.getInstance();

        tripId = getIntent().getStringExtra(EXTRA_TRIP_ID);
        platBus = getIntent().getStringExtra(EXTRA_BUS_NO);
        if (tripId==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (platBus==null) {
            Toast.makeText(this ,"Failed to get data", Toast.LENGTH_SHORT).show();
            finish();
        }

        getSeatsData(platBus);

    }


    private void setSeatView() {

        checkedTextViewList.add(0, binding.ivSeatA1);
        checkedTextViewList.add(1, binding.ivSeatA2);
        checkedTextViewList.add(2, binding.ivSeatA3);
        checkedTextViewList.add(3, binding.ivSeatA4);
        checkedTextViewList.add(4, binding.ivSeatA5);
        checkedTextViewList.add(5, binding.ivSeatA6);
        checkedTextViewList.add(6, binding.ivSeatA7);
        checkedTextViewList.add(7, binding.ivSeatA8);
        checkedTextViewList.add(8, binding.ivSeatA9);
        checkedTextViewList.add(9, binding.ivSeatB1);
        checkedTextViewList.add(10, binding.ivSeatB2);
        checkedTextViewList.add(11, binding.ivSeatB3);
        checkedTextViewList.add(12, binding.ivSeatB4);
        checkedTextViewList.add(13, binding.ivSeatB5);
        checkedTextViewList.add(14, binding.ivSeatB6);
        checkedTextViewList.add(15, binding.ivSeatB7);
        checkedTextViewList.add(16, binding.ivSeatB8);
        checkedTextViewList.add(17, binding.ivSeatB9);
        checkedTextViewList.add(18, binding.ivSeatB10);
        checkedTextViewList.add(19, binding.ivSeatC1);
        checkedTextViewList.add(20, binding.ivSeatC2);
        checkedTextViewList.add(21, binding.ivSeatC3);
        checkedTextViewList.add(22, binding.ivSeatC4);
        checkedTextViewList.add(23, binding.ivSeatC5);
        checkedTextViewList.add(24, binding.ivSeatC6);
        checkedTextViewList.add(25, binding.ivSeatC7);
        checkedTextViewList.add(26, binding.ivSeatC8);
        checkedTextViewList.add(27, binding.ivSeatC9);
        checkedTextViewList.add(28, binding.ivSeatC10);
        checkedTextViewList.add(29, binding.ivSeatD1);
        checkedTextViewList.add(30, binding.ivSeatD2);
        checkedTextViewList.add(31, binding.ivSeatD3);
        checkedTextViewList.add(32, binding.ivSeatD4);
        checkedTextViewList.add(33, binding.ivSeatD5);
        checkedTextViewList.add(34, binding.ivSeatD6);
        checkedTextViewList.add(35, binding.ivSeatD7);
        checkedTextViewList.add(36, binding.ivSeatD8);
        checkedTextViewList.add(37, binding.ivSeatD9);
        checkedTextViewList.add(38, binding.ivSeatD10);
    }
    private void getSeatsData(String platNo) {

        db.collection("bus")
                .document(platNo)
                .collection("seats")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        seatsList.clear();
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Seats seat = documentSnapshot.toObject(Seats.class);
                            seatsList.add(seat);
                        }
                        for (int i=0; i<seatsList.size(); i++) {
                            populateSeatsData(checkedTextViewList.get(i), seatsList.get(i).isBooked());
                        }
                    }
                });
    }

    private void populateSeatsData(CheckedTextView seatView, boolean state) {
            seatView.setChecked(state);
            if (state==true) {
                seatView.setEnabled(false);
            }
    }

    private void seatChooserListener() {

    }

}