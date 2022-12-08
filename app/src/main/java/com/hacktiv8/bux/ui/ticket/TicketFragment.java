package com.hacktiv8.bux.ui.ticket;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hacktiv8.bux.databinding.FragmentTicketBinding;
import com.hacktiv8.bux.model.Order;
import com.hacktiv8.bux.model.Ticket;
import com.hacktiv8.bux.model.Trip;
import com.hacktiv8.bux.model.User;
import com.hacktiv8.bux.ui.MainActivity;
import com.hacktiv8.bux.ui.adapter.TicketAdapter;

import java.util.ArrayList;
import java.util.List;

public class TicketFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private User currentUser;
    private FragmentTicketBinding binding;
    private List<Order> orderList = new ArrayList<>();
    private TicketAdapter ticketAdapter;
    private RecyclerView rvTicket;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rvTicket = binding.rvTicket;

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String userId = auth.getCurrentUser().getUid();

        getUserData(userId);
        Log.d("user uid", userId);

        rvTicket.setHasFixedSize(true);
        rvTicket.setLayoutManager(new LinearLayoutManager(requireContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getUserData(String userId) {
        db.collection("user")
                .whereEqualTo("uid", userId)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        currentUser = documentSnapshot.toObject(User.class);
                    }
                    getOrderData(currentUser.getPhoneNumber());
                });
    }

    private void getOrderData(String phoneNumber){
        db.collection("user")
                .document(phoneNumber)
                .collection("order")
                .get().addOnCompleteListener(task -> {
                    orderList.clear();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            Order ticket = documentSnapshot.toObject(Order.class);
                            orderList.add(ticket);
                        }
                        if (orderList.size()>0) {
                            ticketAdapter = new TicketAdapter(requireContext(), orderList, db);
                            ticketAdapter.notifyDataSetChanged();
                            rvTicket.setAdapter(ticketAdapter);
                        } else {
                            //
                        }

                    } else {
                        Toast.makeText(requireContext(), "failed to get data", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}