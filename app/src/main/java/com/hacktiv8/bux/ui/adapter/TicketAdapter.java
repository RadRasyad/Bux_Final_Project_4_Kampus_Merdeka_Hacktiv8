package com.hacktiv8.bux.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hacktiv8.bux.databinding.OrderBinding;
import com.hacktiv8.bux.model.Order;
import com.hacktiv8.bux.model.Ticket;
import com.hacktiv8.bux.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private Context context;
    private List<Order> list;

    public TicketAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderBinding binding = OrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = list.get(position);
        if (order != null){
            holder.bind(order);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final OrderBinding binding;

        public MyViewHolder(OrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order){
           binding.jam.setText(order.getDepartHour());
           binding.idTicket.setText(order.getBookNo());
           binding.nameBus.setText(order.getBusName());
           binding.platBus.setText(order.getPlatBus());
           binding.tgl.setText(DateHelper.timestampToLocalDate(order.getDate()));
           binding.terminalBus.setText(order.getDepartTerminal());
           binding.destBus.setText(order.getDepartCity());
           binding.status.setText(order.getStatus());
        }
    }
}
