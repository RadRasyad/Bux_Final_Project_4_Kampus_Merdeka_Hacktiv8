package com.hacktiv8.bux.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hacktiv8.bux.databinding.ItemTripTicketLayoutBinding;
import com.hacktiv8.bux.model.Trip;
import com.hacktiv8.bux.ui.bus.BusScheduleActivity;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    public ItemClickListener itemClickListener;
    private Context context;
    private List<Trip> list;

    public TripAdapter(Context context, List<Trip> list){
        this.context = context;
        this.list = list;
    }


    public void setFilteredList(Context context, ArrayList<Trip> filteredList){
        this.context = context;
        this.list = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTripTicketLayoutBinding binding = ItemTripTicketLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.MyViewHolder holder, int position) {
        Trip trip = list.get(position);
        if(trip!= null){
            holder.bind(trip);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemTripTicketLayoutBinding binding;


        public MyViewHolder(ItemTripTicketLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Trip trip){
            binding.tvBusName.setText(trip.getBusName());
            binding.tvBusNo.setText(trip.getPlatBus());
            binding.tvDepartDate.setText(trip.getDate());
            binding.tvDepartHour.setText(trip.getDepartHour());
            binding.tvDepartCity.setText(trip.getDepartCity());
            binding.tvDepartStation.setText("Terminal"+trip.getDepartTerminal());
            binding.tvArriveHour.setText(trip.getArrivalHour());
            binding.tvArriveCity.setText(trip.getArrivalCity());
            binding.tvArriveStation.setText("Terminal"+trip.getArrivalTerminal());
            binding.tvPrice.setText("Rp."+trip.getPrice());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClick(list.get(getAdapterPosition()));
                }
            });


        }

    }

    public interface ItemClickListener{
        void onClick(Trip Trip);
    }
}
