package com.example.lifecare.appointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.R;
import com.example.lifecare.VO.DiagnosisVO;

import java.util.ArrayList;

public class diagnosisAdapter extends RecyclerView.Adapter<diagnosisAdapter.diagnosisViewHolder> {

    private ArrayList<DiagnosisVO> arrayList;

    public class diagnosisViewHolder extends RecyclerView.ViewHolder {
        TextView tv_diagnosisDate;
        TextView tv_diagnosisMajor;
        TextView tv_diagnosisDoctor;
        TextView tv_diagnosisDisease;
        TextView tv_diagnosisDrug;
        ImageButton bt_expand;
        public View lyt_expand;
        public View lyt_parent;

        public diagnosisViewHolder(View itemView) {
            super(itemView);
            this.tv_diagnosisDate = itemView.findViewById(R.id.tv_diagnosisDate);
            this.tv_diagnosisMajor = itemView.findViewById(R.id.tv_diagnosisMajor);
            this.tv_diagnosisDoctor = itemView.findViewById(R.id.tv_diagnosisDoctor);
            this.tv_diagnosisDisease = itemView.findViewById(R.id.tv_diagnosisDisease);
            this.tv_diagnosisDrug = itemView.findViewById(R.id.tv_diagnosisDrug);
            bt_expand = (ImageButton) itemView.findViewById(R.id.bt_expand);
            lyt_expand = (View) itemView.findViewById(R.id.lyt_expand);
            lyt_parent = (View) itemView.findViewById(R.id.lyt_parent);
        }
    }

    public diagnosisAdapter(ArrayList<DiagnosisVO> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public diagnosisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_diagnosis_item, parent, false);
        diagnosisViewHolder holder = new diagnosisViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull diagnosisAdapter.diagnosisViewHolder holder, final int position) {
            holder.tv_diagnosisDate.setText(arrayList.get(position).getDiagnosis_time());
            holder.tv_diagnosisMajor.setText(arrayList.get(position).getDoctor_major());
            holder.tv_diagnosisDoctor.setText(arrayList.get(position).getDoctor_name());
            holder.tv_diagnosisDisease.setText(arrayList.get(position).getDisease_name());
            holder.tv_diagnosisDrug.setText(arrayList.get(position).getDrug());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public DiagnosisVO getItem(int position) {
        return arrayList.get(position);
    }

    public void setItme(int position, DiagnosisVO item) {
        arrayList.set(position,item);
    }
}
