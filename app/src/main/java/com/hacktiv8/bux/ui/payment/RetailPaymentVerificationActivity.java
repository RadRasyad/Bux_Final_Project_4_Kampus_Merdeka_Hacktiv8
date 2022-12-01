package com.hacktiv8.bux.ui.payment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.ActivityRetailPaymentVerificationBinding;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.NumberFormat;
import java.util.Locale;

public class RetailPaymentVerificationActivity extends AppCompatActivity {

    private ActivityRetailPaymentVerificationBinding binding;
    private String total, tripId, platBus, bookedSeat, toTgl;
    public static final String EXTRA_TRIP_ID = "extra_tripid";
    public static final String EXTRA_BUS_NO = "extra_busno";
    public static final String EXTRA_BOOKED_SEAT = "extra_booked_seat";
    public static final String EXTRA_TOTAL = "extra_total";
    public static final String EXTRA_TO_TGL = "extra_to_tgl";
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    private MultiFormatWriter multi = new MultiFormatWriter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRetailPaymentVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tripId = getIntent().getStringExtra(EXTRA_TRIP_ID);
        platBus = getIntent().getStringExtra(EXTRA_BUS_NO);
        bookedSeat = getIntent().getStringExtra(EXTRA_BOOKED_SEAT);
        total = getIntent().getStringExtra(EXTRA_TOTAL);
        toTgl = getIntent().getStringExtra(EXTRA_TO_TGL);
        double dTotal = Double.valueOf(total);
        binding.totalPaymentTv.setText(formatRupiah.format((double)dTotal));

        binding.tvPaymentNumber.setOnClickListener(v ->{
            puQrCode();
        });


    }

    void puQrCode(){
        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.form_qrcode, null);


        ImageView imgQRCode = (ImageView) view.findViewById(R.id.imgQrCode);
        TextView back = (TextView) view.findViewById(R.id.back);

        Bitmap bitmap ;
//        String code = "230719-0001";
        String code = binding.tvPaymentNumber.getText().toString();
        try {
            BitMatrix bitMatrix = multi.encode(code, BarcodeFormat.AZTEC, 320, 320);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQRCode.setImageBitmap(bitmap);

        }catch (WriterException e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }


        popupBuilder.setView(view);

        AlertDialog popupForm = popupBuilder.create();
        popupForm.show();

        back.setOnClickListener(v ->{
            popupForm.dismiss();
        });


    }
}