package com.example.lifecare.drug;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lifecare.R;
import com.example.lifecare.VO.AppointmentVO;
import com.example.lifecare.VO.DoctorVO;
import com.example.lifecare.VO.DrugVO;
import com.example.lifecare.appointment.OnItemClickListener;

import java.util.ArrayList;

public class drugAdapter extends RecyclerView.Adapter<drugAdapter.DrugViewHolder> implements OnDrugclickListener {

    private ArrayList<DrugVO> arrayList;
    private Context mContext;
    OnDrugclickListener listener;
//
//    public drugAdapter(Context mContext, ArrayList<DrugVO> arrayList)
//    {
//        this.mContext = mContext;
//        this.arrayList = arrayList;
//        //this.itemLayout = itemLayout;
//    }
    public void setOnDrugClicklistener(OnDrugclickListener listener){
        this.listener = listener;
    }


    @Override
    public void onDrugClick(drugAdapter.DrugViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onDrugClick(holder,view,position);
        }
    }

    public class DrugViewHolder extends RecyclerView.ViewHolder {
        ImageView d_img;
        TextView d_num;
        TextView d_name;
        TextView d_emtp;
        TextView d_f_shape;

        public DrugViewHolder(View itemView) {
            super(itemView);
            this.d_img = itemView.findViewById(R.id.d_img);
            this.d_num = itemView.findViewById(R.id.d_num);
            this.d_name = itemView.findViewById(R.id.d_name);
            this.d_emtp = itemView.findViewById(R.id.d_emtp);
            this.d_f_shape = itemView.findViewById(R.id.d_f_shape);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onDrugClick(DrugViewHolder.this, v, position);
                    }
                }
            });
        }
    }

    public drugAdapter(ArrayList<DrugVO> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_drug, parent, false);

        DrugViewHolder holder = new DrugViewHolder(view);
        mContext = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        Glide.with(holder.d_img.getContext()).load(arrayList.get(position).getDrug_productimage()).into(holder.d_img);

        holder.d_num.setText(arrayList.get(position).getDrug_number());
        holder.d_name.setText(arrayList.get(position).getDrug_name());
        holder.d_emtp.setText(arrayList.get(position).getDrug_enptname());
        holder.d_f_shape.setText(arrayList.get(position).getDrug_frontShape());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                int position = getAdapterPosition();
////                if(listener != null){
////                    listener.onDrugClick(DrugViewHolder.this, v, position);
////                }
//               System.out.print("어디냐 ? :  + " + arrayList.get(position).getDrug_number().toString());
//                Intent intent = new Intent(mContext, drugPhotoDetail.class);
//                String drug_num = arrayList.get(position).getDrug_number();
//                intent.putExtra("drug_num",drug_num );
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public DrugVO getItem(int position){
        return arrayList.get(position);
    }
    public void setItem(int position, DrugVO item){
        arrayList.set(position,item);
    }

}
