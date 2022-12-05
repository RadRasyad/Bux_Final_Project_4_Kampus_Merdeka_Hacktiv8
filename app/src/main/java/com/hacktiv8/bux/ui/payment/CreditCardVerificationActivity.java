package com.hacktiv8.bux.ui.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.ActivityCreditCardVerificationBinding;
import com.hacktiv8.bux.model.Ticket;
import com.hacktiv8.bux.model.User;
import com.hacktiv8.bux.ui.MainActivity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class CreditCardVerificationActivity extends AppCompatActivity {

    private ActivityCreditCardVerificationBinding binding;
    private String total, tripId, platBus, bookedSeat, toTgl, email, idTiket, phoneNumber;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private User user;
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

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();
        getUserData(email);

        tripId = getIntent().getStringExtra(EXTRA_TRIP_ID);
        platBus = getIntent().getStringExtra(EXTRA_BUS_NO);
        bookedSeat = getIntent().getStringExtra(EXTRA_BOOKED_SEAT);
        total = getIntent().getStringExtra(EXTRA_TOTAL);
        toTgl = getIntent().getStringExtra(EXTRA_TO_TGL);
        double dTotal = Double.valueOf(total);
        binding.totalPaymentTv.setText(formatRupiah.format((double)dTotal));


        binding.btnVerifyPayment.setOnClickListener(v -> {
            idTiket = UUID.randomUUID().toString();
            String tranfer = "Master Card";
            String status = "Paid";
            Boolean rate = false;

            Ticket addOrder = new Ticket();
            addOrder.setIdTicket(idTiket);
            addOrder.setIdTrip(tripId);
            addOrder.setPlatno(platBus);
            addOrder.setSeatNo(bookedSeat);
            addOrder.setTotal(total);
            addOrder.setToTgl(toTgl);
            addOrder.setTransaksi(tranfer);
            addOrder.setStatus(status);
            addOrder.setRated(rate);

        });


    }

    private void getUserData(String userId) {
        db.collection("user").whereEqualTo("email", userId)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            user = documentSnapshot.toObject(User.class);
                            getPhoneNumber(user);
                        }
                    }
                });

    }

    private void getPhoneNumber(User user){
        phoneNumber = user.getPhoneNumber();
    }

    private void order(String email, String idTiket, Ticket ticket){
        db.collection("user")
                .document(email)
                .collection("order")
                .document(idTiket)
                .set(ticket)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent = new Intent(CreditCardVerificationActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        CreditCardVerificationActivity.this.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    // Function for linear search
    public static int search(int arr[], int x)
    {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            if (arr[i] == x)
                return i;
        }
        return 0;
    }
}