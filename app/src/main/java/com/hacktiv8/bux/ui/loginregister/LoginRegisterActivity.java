package com.hacktiv8.bux.ui.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hacktiv8.bux.databinding.ActivityLoginRegisterBinding;

public class LoginRegisterActivity extends AppCompatActivity {

    private ActivityLoginRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.googleSingIn.setIconTint(null);

        binding.googleSingIn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegisterActivity.this, SetupPhoneActivity.class);
            startActivity(intent);
        });
    }
}