package com.kuldeep.carassure.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.R;

public class MyPurchesFragment extends Fragment {
MaterialButton mbtn_ongoging1, mbtn_Completed;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_purches, container, false);
        mbtn_Completed = view.findViewById(R.id.mbtn_Completed);
        mbtn_ongoging1 = view.findViewById(R.id.mbtn_ongoging1);

        mbtn_ongoging1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_ongoging1.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_Completed.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_ongoging1.setTextColor(getResources().getColor(R.color.white));
                mbtn_Completed.setTextColor(getResources().getColor(R.color.red));
            }
        });

        mbtn_Completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_Completed.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_ongoging1.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_Completed.setTextColor(getResources().getColor(R.color.white));
                mbtn_ongoging1.setTextColor(getResources().getColor(R.color.red));
            }
        });

        return view;
    }
}