package com.example.lifecare.appointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.R;
import com.example.lifecare.VO.ReservationVO;

import java.util.ArrayList;

public class reservationAdapter extends RecyclerView.Adapter<reservationAdapter.reservationViewHolder> implements OnReservationItemClickListener {

    private ArrayList<ReservationVO> arrayList;

    OnReservationItemClickListener listener;

    public void setOnReservationItemClickListener(OnReservationItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(reservationAdapter.reservationViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    public class reservationViewHolder extends RecyclerView.ViewHolder {
        TextView tv_selectedMajor;
        TextView tv_selectedDoctor;
        TextView tv_selectedDate;

        public reservationViewHolder(View itemView) {
            super(itemView);
            this.tv_selectedMajor = itemView.findViewById(R.id.tv_selectedMajor);
            this.tv_selectedDoctor = itemView.findViewById(R.id.tv_selectedDoctor);
            this.tv_selectedDate = itemView.findViewById(R.id.tv_selectedDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(reservationAdapter.reservationViewHolder.this, v, position);
                    }
                }
            });
        }
    }

    public reservationAdapter(ArrayList<ReservationVO> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public reservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reservation_item, parent, false);
        reservationViewHolder holder = new reservationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull reservationViewHolder holder, int position) {
        holder.tv_selectedMajor.setText(arrayList.get(position).getDoctor_major());
        holder.tv_selectedDoctor.setText(arrayList.get(position).getDoctor_name());
        holder.tv_selectedDate.setText(arrayList.get(position).getReservation_date());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public ReservationVO getItem(int position){
        return arrayList.get(position);
    }

    public void setItem(int position, ReservationVO item){
        arrayList.set(position,item);
    }
}
