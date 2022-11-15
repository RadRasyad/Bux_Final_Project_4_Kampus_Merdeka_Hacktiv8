package com.hacktiv8.bux.ui.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hacktiv8.bux.R;
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


        binding.btnSeePicture.setOnClickListener(v -> {
            onImageShow("pathImg");
        });

    }

    private void onImageShow(String uri) {
        View inflatedView = getLayoutInflater().inflate(R.layout.img_view_layout, null);
        ImageView imageView = inflatedView.findViewById(R.id.ivViewer);
        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.bux_logo)
                .into(imageView);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setView(inflatedView).show();
    }
}