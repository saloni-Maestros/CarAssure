package com.kuldeep.carassure.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Fragment.HomescreenFragment;
import com.kuldeep.carassure.R;

public class SuccessfullyBidActivity extends AppCompatActivity {
    MaterialButton mbtn_backhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_bid);
        mbtn_backhome = findViewById(R.id.mbtn_backhome);
        mbtn_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}