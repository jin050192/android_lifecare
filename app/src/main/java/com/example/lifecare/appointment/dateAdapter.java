package com.example.lifecare.appointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.R;
import com.example.lifecare.VO.AppointmentVO;

import java.util.ArrayList;

public class dateAdapter extends RecyclerView.Adapter<dateAdapter.dateViewHolder> implements OnDateItemClickListener {
    private ArrayList<AppointmentVO> arrayList;

    OnDateItemClickListener listener;

    public void setOnDateItemClicklistener(OnDateItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(dateAdapter.dateViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    public class dateViewHolder extends RecyclerView.ViewHolder {
        TextView tv_day;
        TextView tv_date;

        public dateViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_day = itemView.findViewById(R.id.tv_day);
            this.tv_date = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(dateViewHolder.this, v, position);
                    }
                }
            });
        }
    }

    public dateAdapter(ArrayList<AppointmentVO> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public dateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_date_item, parent, false);
        dateViewHolder holder = new dateViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull dateAdapter.dateViewHolder holder, int position) {
        holder.tv_date.setText(arrayList.get(position).getAppoint_date());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public AppointmentVO getItem(int position){
        return arrayList.get(position);
    }
    public void setItem(int position, AppointmentVO item){
        arrayList.set(position,item);
    }

}
