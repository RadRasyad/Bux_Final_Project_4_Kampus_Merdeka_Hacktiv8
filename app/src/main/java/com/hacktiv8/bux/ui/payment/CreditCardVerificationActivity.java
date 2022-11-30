package com.hacktiv8.bux.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.ActivityCreditCardVerificationBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class CreditCardVerificationActivity extends AppCompatActivity {

    private ActivityCreditCardVerificationBinding binding;
    private String total, tripId, platBus, bookedSeat, toTgl;
    public static final String EXTRA_TRIP_ID = "extra_tripid";
    public static final String EXTRA_BUS_NO = "extra_busno";
    public static final String EXTRA_BOOKED_SEAT = "extra_booked_seat";
    public static final String EXTRA_TOTAL = "extra_total";
    public static final String EXTRA_TO_TGL = "extra_to_tgl";
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditCardVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tripId = getIntent().getStringExtra(EXTRA_TRIP_ID);
        platBus = getIntent().getStringExtra(EXTRA_BUS_NO);
        bookedSeat = getIntent().getStringExtra(EXTRA_BOOKED_SEAT);
        total = getIntent().getStringExtra(EXTRA_TOTAL);
        toTgl = getIntent().getStringExtra(EXTRA_TO_TGL);
        double dTotal = Double.valueOf(total);
        binding.totalPaymentTv.setText(formatRupiah.format((double)dTotal));


    }
}