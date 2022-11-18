package com.hacktiv8.bux.ui.home;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.hacktiv8.bux.R;
import com.hacktiv8.bux.databinding.FragmentHomeBinding;
import com.hacktiv8.bux.ui.chooser.DatePickerActivity;
import com.hacktiv8.bux.ui.chooser.DestinationChooserActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentHomeBinding binding;
    private SimpleDateFormat format;
    private Calendar calendar;

    @SuppressLint("SimpleDateFormat")
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

        return root;
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

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), DestinationChooserActivity.class);
        switch (view.getId()){
            case R.id.passangers:

                break;
            case R.id.date:
                startActivityForResult(new Intent(getContext(), DatePickerActivity.class), 1);
                break;
            case R.id.departure:
                intent.putExtra("hint", binding.departure.getText().toString());
                startActivityForResult(intent, 2);
                break;
            case R.id.arrival:
                intent.putExtra("hint", binding.arrival.getText().toString());
                startActivityForResult(intent, 3);
                break;

        }
    }

    private void onStartActivity() {
        boolean isDate = binding.date.getText().equals(R.string.str_select_date);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(data != null && resultCode == RESULT_OK){
                    calendar = (Calendar) data.getSerializableExtra("date");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM");
                    binding.date.setText(simpleDateFormat.format(calendar.getTime()));
                }
                break;
        }

    }
}