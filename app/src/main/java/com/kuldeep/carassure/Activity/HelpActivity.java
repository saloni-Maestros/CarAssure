package com.kuldeep.carassure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kuldeep.carassure.R;

public class HelpActivity extends AppCompatActivity {

    ImageView imgBack;
    LinearLayout llFeedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        imgBack = findViewById(R.id.imgBack);
        llFeedBack = findViewById(R.id.llFeedBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        llFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(HelpActivity.this,FeedBackActivity.class));
            }
        });
    }
}