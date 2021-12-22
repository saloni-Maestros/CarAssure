package com.kuldeep.carassure.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.R;

public class MyAuctionsFragment extends Fragment {
MaterialButton mbtn_ongoging,mbtn_past;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_auctions, container, false);
        mbtn_past = view.findViewById(R.id.mbtn_past);
        mbtn_ongoging = view.findViewById(R.id.mbtn_ongoging);

        mbtn_ongoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_ongoging.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_past.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_ongoging.setTextColor(getResources().getColor(R.color.white));
                mbtn_past.setTextColor(getResources().getColor(R.color.red));

            }
        });
        mbtn_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_past.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_ongoging.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_past.setTextColor(getResources().getColor(R.color.white));
                mbtn_ongoging.setTextColor(getResources().getColor(R.color.red));


            }
        });


        return view;
    }
}