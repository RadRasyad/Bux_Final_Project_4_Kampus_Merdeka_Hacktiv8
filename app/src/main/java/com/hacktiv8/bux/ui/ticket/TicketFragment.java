package com.hacktiv8.bux.ui.ticket;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hacktiv8.bux.databinding.FragmentTicketBinding;
import com.hacktiv8.bux.model.Order;
import com.hacktiv8.bux.model.User;
import com.hacktiv8.bux.ui.MainActivity;
import com.hacktiv8.bux.ui.adapter.TicketAdapter;

import java.util.ArrayList;
import java.util.List;

public class TicketFragment extends Fragment {

    private FirebaseFirestore db;
    private FragmentTicketBinding binding;
    private List<Order> orderList = new ArrayList<>();
    private TicketAdapter ticketAdapter;
    private RecyclerView rvTicket;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvTicket = binding.rvTicket;

        db = FirebaseFirestore.getInstance();
        String userId = "088228659668";
//        Log.d("Extra idUser Detail", userId);
        getUserData(userId);

        rvTicket.setHasFixedSize(true);
        rvTicket.setLayoutManager(new LinearLayoutManager(this));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("RestrictedApi")
    private void getUserData(String userId) {
        db.collection("user")
                .document(userId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }else{
                        Log.d(TAG, "get failed with ", task.getException());
                    }

                });
    }

    private void getData(){

    }

   //private void tiket(User user) {
   //}

}