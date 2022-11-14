package com.hacktiv8.bux.ui.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hacktiv8.bux.databinding.ActivityBusDetailBinding;

public class BusDetailActivity extends AppCompatActivity {

    private ActivityBusDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbutton.setOnClickListener(v -> {
            finish();
        });


    }
}