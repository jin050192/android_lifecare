package com.example.lifecare.food;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lifecare.R;
import com.example.lifecare.VO.FoodVO;

import java.util.ArrayList;
import java.util.List;

public class AdapterListFood extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FoodVO> items = new ArrayList<>();

    private Context ctx;

    @LayoutRes
    private int layout_id;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, FoodVO obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListFood(Context context, List<FoodVO> items, @LayoutRes int layout_id) {
        this.items = items;
        ctx = context;
        this.layout_id = layout_id;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView foodListImg;
        public TextView foodKcal;
        public TextView tdz;
        public TextView foodListName;
        public View lyt_parent2;

        public OriginalViewHolder(View v) {
            super(v);
            foodListImg = v.findViewById(R.id.foodListImg);
            foodKcal = v.findViewById(R.id.foodKcal);
            tdz = v.findViewById(R.id.tdz);
            foodListName = v.findViewById(R.id.foodListName);
            lyt_parent2 = v.findViewById(R.id.lyt_parent2);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            FoodVO n = items.get(position);
            view.foodKcal.setText(n.getKcal()+" Kcal");

            String tdz = "탄 : "+n.getCarbo() +"g  단 : "+n.getProtein() + "g  지 : "+n.getFat()+"g";
            SpannableStringBuilder ssb = new SpannableStringBuilder(tdz);
/*
            ssb.setSpan(new BackgroundColorSpan(Color.parseColor("#f6830f")), tdz.indexOf("탄"), tdz.indexOf("탄")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new BackgroundColorSpan(Color.parseColor("#0e918c")), tdz.indexOf("단"), tdz.indexOf("단")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new BackgroundColorSpan(Color.parseColor("#4c00b8")), tdz.indexOf("지"), tdz.indexOf("지")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), tdz.indexOf("탄"), tdz.indexOf("탄")+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), tdz.indexOf("단"), tdz.indexOf("단")+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), tdz.indexOf("지"), tdz.indexOf("지")+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#f6830f")), tdz.indexOf("탄")+1, tdz.indexOf("단")-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#0e918c")), tdz.indexOf("단")+1, tdz.indexOf("지")-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#4c00b8")), tdz.indexOf("지")+1, tdz.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            view.tdz.setText(ssb);
            view.foodListImg.setImageResource(n.getFoodImg());
            view.foodListName.setText(n.getFoodName());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}