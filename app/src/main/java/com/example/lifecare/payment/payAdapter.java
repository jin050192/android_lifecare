package com.example.lifecare.payment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.R;
import com.example.lifecare.VO.DiagnosisVO;

import java.util.ArrayList;

public class payAdapter extends RecyclerView.Adapter<payAdapter.payViewHolder> implements OnpayItemClickListener {

    private ArrayList<DiagnosisVO> arrayList;

    OnpayItemClickListener listener;

    public void setOnpayItemClickListener(OnpayItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(payAdapter.payViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder,view,position);
        }
    }

    public class payViewHolder extends RecyclerView.ViewHolder {
        TextView tv_diagnosis_date;
        TextView tv_diagnosis_major;
        TextView tv_diagnosisAmount;

        public payViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_diagnosis_date = itemView.findViewById(R.id.tv_diagnosis_date);
            this.tv_diagnosis_major = itemView.findViewById(R.id.tv_diagnosis_major);
            this.tv_diagnosisAmount = itemView.findViewById(R.id.tv_diagnosisAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(payAdapter.payViewHolder.this, v, position);
                    }
                }
            });
        }

    }

    public payAdapter(ArrayList<DiagnosisVO> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public payViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pay_item, parent, false);
        payViewHolder holder = new payViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull payViewHolder holder, int position) {
        holder.tv_diagnosis_date.setText(arrayList.get(position).getDiagnosis_time());
        holder.tv_diagnosis_major.setText(arrayList.get(position).getDoctor_major());
        holder.tv_diagnosisAmount.setText(arrayList.get(position).getCustomer_amount());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public DiagnosisVO getItem(int position) {
        return arrayList.get(position);
    }

    public void setItem(int position, DiagnosisVO item) {
        arrayList.set(position,item);
    }
}
