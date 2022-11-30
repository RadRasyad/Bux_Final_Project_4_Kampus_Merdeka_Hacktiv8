package com.hacktiv8.bux.ui.ticket;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hacktiv8.bux.databinding.FragmentTicketBinding;
import com.hacktiv8.bux.model.User;

public class TicketFragment extends Fragment {

    private FirebaseFirestore db;
    private FragmentTicketBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        String userId = "088228659668";
//        Log.d("Extra idUser Detail", userId);
        getUserData(userId);




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

//    private void tiket(User user) {
//    }
}