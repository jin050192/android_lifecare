package com.example.lifecare.drug;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lifecare.R;

public class drugSearchMain extends AppCompatActivity {
    Button drugSearch;
    Button photoSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);

        drugSearch =(Button)findViewById(R.id.btn_drugSearch);
        photoSearch =(Button)findViewById(R.id.btn_photoSearch);

        drugSearch.setOnClickListener(drugClickListener);
        photoSearch.setOnClickListener(drugClickListener);

    }

    View.OnClickListener drugClickListener = new
            View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.btn_drugSearch:
                            Intent intent = new
                                    Intent(drugSearchMain.this, drugSearch.class);
                            startActivity(intent);
                            break;
                    }
                    switch ((v.getId())) {
                        case R.id.btn_photoSearch:
//                            Intent intent = new
//                                    Intent(drugSearchMain.this, drugSearch.class);
//                            startActivity(intent);
                            break;
                    }
                }
            };

    }

