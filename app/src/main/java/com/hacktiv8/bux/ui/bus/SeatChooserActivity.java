package com.hacktiv8.bux.ui.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hacktiv8.bux.databinding.ActivitySeatChooserBinding;

public class SeatChooserActivity extends AppCompatActivity {

    private ActivitySeatChooserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeatChooserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}