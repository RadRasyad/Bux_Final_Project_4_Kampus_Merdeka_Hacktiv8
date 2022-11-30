package com.hacktiv8.bux.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.ActivityBankTransferBinding;
import com.hacktiv8.bux.databinding.ActivityCreditCardVerificationBinding;
import com.hacktiv8.bux.databinding.ActivityRetailPaymentVerificationBinding;

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

//        binding.tvPaymentNumber.setOnClickListener(v ->{
//            try {
//                TextToQRImageEncode(this,  "Alfamaret", 230719-0001);
//            } catch (WriterException e) {
//                e.printStackTrace();
//            }
//        });


    }

    public static Bitmap TextToQRImageEncode(Context context, String Value, int QRCodeWidth) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(Value, BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        context.getResources().getColor(R.color.black) : context.getResources().getColor(R.color.white);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, QRCodeWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}