package com.kuldeep.carassure.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.kuldeep.carassure.Activity.HomeActivity;
import com.kuldeep.carassure.Adapter.HomeFragmentAdapter;
import com.kuldeep.carassure.R;

public class HomescreenFragment extends Fragment {
    TabLayout tablayout1;
    ViewPager viewPager;
    ImageView img_menu;
    MaterialButton btn_placebid,mbtn_latest,mbtn_Inventery,mbtn_Buynow;

 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homescreen, container, false);
        img_menu=view.findViewById(R.id.img_menu);
        tablayout1=view.findViewById(R.id.tablayout1);
        viewPager = view. findViewById(R.id.viewPager);
        mbtn_Buynow = view.findViewById(R.id.mbtn_Buynow);
      /*  mbtn_Inventery = view.findViewById(R.id.mbtn_Inventery);
        mbtn_Buynow = view.findViewById(R.id.mbtn_Buynow);
        mbtn_latest = view.findViewById(R.id.mbtn_latest);
        mbtn_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.rad));
                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });
        mbtn_Inventery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.rad));
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });
        mbtn_Buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.rad));
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });*/



        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout1.setupWithViewPager(viewPager);

        tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.drawerlayout.openDrawer(GravityCompat.START);
            }
        });
       onBack(view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.popup_home_fragment);
                    dialog.setCancelable(true);
                    Button btn_yes = dialog.findViewById(R.id.btn_yes);
                    Button btn_no = dialog.findViewById(R.id.btn_no);
                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finishAffinity();
                        }
                    });

                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
                return true;
            }
        });
    }

}