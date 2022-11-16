package com.hacktiv8.bux.ui.home;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.FragmentHomeBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentHomeBinding binding;
    private SimpleDateFormat format;
    private Calendar calendar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        format = new SimpleDateFormat(" dd MMM");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.passangers.setOnClickListener(this);
        binding.date.setOnClickListener(this);

        if(savedInstanceState != null){
            onStateData(savedInstanceState);
        }

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("date", calendar);
    }

    private void onStateData(Bundle savedInstanceState) {
        calendar = (Calendar) savedInstanceState.getSerializable("date");
        if(calendar!=null) binding.date.setText(format.format(calendar.getTime()));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.passangers:

                break;
            case R.id.date:

                break;
        }
    }
}