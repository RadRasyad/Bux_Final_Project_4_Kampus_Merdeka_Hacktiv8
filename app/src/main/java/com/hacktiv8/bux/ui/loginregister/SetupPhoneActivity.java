package com.hacktiv8.bux.ui.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hacktiv8.bux.databinding.ActivitySetupPhoneBinding;

public class SetupPhoneActivity extends AppCompatActivity {

    private ActivitySetupPhoneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}