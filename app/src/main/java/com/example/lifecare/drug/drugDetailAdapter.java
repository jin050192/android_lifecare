package com.example.lifecare.drug;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lifecare.R;
import com.example.lifecare.VO.DrugVO;

import java.util.ArrayList;

public class drugDetailAdapter extends RecyclerView.Adapter<drugDetailAdapter.DrugViewHolder> implements OnDrugDetailclickListener {

    private ArrayList<DrugVO> arrayList;

    OnDrugDetailclickListener listener;

    public void setOnDrugDetailClick(OnDrugDetailclickListener listener){
        this.listener = listener;
    }

    @Override
    public void onDrugDetailclickListener(DrugViewHolder holder, View view, int position) {
        if(listener !=null){
            listener.onDrugDetailclickListener (holder,view,position);
        }
    }



    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public class DrugViewHolder extends RecyclerView.ViewHolder {
        ImageView drug_image;
        TextView drug_name;
        TextView drug_emtp;
        TextView drug_division;
        TextView drug_lisenceDate;
        TextView drug_category;
        TextView drug_formulation;
        TextView drug_color;
        TextView drug_num;
        TextView drug_storage;
        TextView drug_additives;
        TextView drug_effect;
        TextView drug_precautions;

        public DrugViewHolder(View itemView) {
            super(itemView);
            this.drug_image = itemView.findViewById(R.id.drug_image);
            this.drug_emtp = itemView.findViewById(R.id.drug_emtp);
            this.drug_division = itemView.findViewById(R.id.drug_division);
            this.drug_lisenceDate = itemView.findViewById(R.id.drug_lisenceDate);
            this.drug_category = itemView.findViewById(R.id.drug_category);
            this.drug_formulation = itemView.findViewById(R.id.drug_formulation);
            this.drug_color = itemView.findViewById(R.id.drug_color);
            this.drug_num = itemView.findViewById(R.id.drug_num);
            this.drug_storage = itemView.findViewById(R.id.drug_storage);
            this.drug_additives = itemView.findViewById(R.id.drug_additives);

        }
    }

    public drugDetailAdapter(ArrayList<DrugVO> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_drug, parent, false);
        DrugViewHolder holder = new DrugViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        Glide.with(holder.drug_image.getContext()).load(arrayList.get(position).getDrug_productimage()).into(holder.drug_image);
        holder.drug_name.setText(arrayList.get(position).getDrug_name());
        holder.drug_emtp.setText(arrayList.get(position).getDrug_enptname());
        holder.drug_division.setText(arrayList.get(position).getDrug_division());
        holder.drug_lisenceDate.setText(arrayList.get(position).getDrug_license_date());
        holder.drug_category.setText(arrayList.get(position).getDrug_category());
        holder.drug_formulation.setText(arrayList.get(position).getDrug_formulation());
        holder.drug_color.setText(arrayList.get(position).getDrug_color());
        holder.drug_num.setText(arrayList.get(position).getDrug_number());
        holder.drug_storage.setText(arrayList.get(position).getDrug_storage());
        holder.drug_additives.setText(arrayList.get(position).getDrug_additives());
        holder.drug_effect.setText(arrayList.get(position).getDrug_effect());
        holder.drug_precautions.setText(arrayList.get(position).getDrug_precautions());

    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }



}
