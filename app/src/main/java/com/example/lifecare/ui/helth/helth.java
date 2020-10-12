package com.example.lifecare.ui.helth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.lifecare.R;

public class helth extends Fragment {
    private ImageView ivImage;



public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_helth, container, false);



        return root;

    }

}

