package com.kuldeep.carassure.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Activity.HomeActivity;
import com.kuldeep.carassure.Adapter.MultipleImageAdapter;
import com.kuldeep.carassure.Adapter.SellAdapter;
import com.kuldeep.carassure.Model.SellModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SellFragment extends Fragment {
    MaterialButton mbtn_all, mbtn_live, mbtn_processing, mbtn_Expired, mbtn_Sold;
    ImageView iv_addmore, iv_back;
    ProgressBar progressBar;

    RecyclerView recycleViewAll, recycleViewLive;
    ArrayList<SellModel> sellModelArrayList;
    SellAdapter sellAdapter;

    MultipleImageAdapter multipleImageAdapter;
    String User_Id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
        recycleViewAll = view.findViewById(R.id.recycleViewAll);
        recycleViewLive = view.findViewById(R.id.recycleViewLive);
        progressBar = view.findViewById(R.id.progressBar);
        mbtn_all = view.findViewById(R.id.mbtn_all);
        iv_back = view.findViewById(R.id.iv_back);
        mbtn_live = view.findViewById(R.id.mbtn_live);
        mbtn_processing = view.findViewById(R.id.mbtn_processing);
        mbtn_Expired = view.findViewById(R.id.mbtn_Expired);
        iv_addmore = view.findViewById(R.id.iv_addmore);
        mbtn_Sold = view.findViewById(R.id.mbtn_Sold);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HomeActivity.class));     // fragment to activity on back
            }
        });

        mbtn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycleViewAll.setVisibility(View.VISIBLE);
                recycleViewLive.setVisibility(View.GONE);

                mbtn_all.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_live.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Expired.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_processing.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Sold.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_all.setTextColor(getResources().getColor(R.color.white));
                mbtn_live.setTextColor(getResources().getColor(R.color.red));
                mbtn_Expired.setTextColor(getResources().getColor(R.color.red));
                mbtn_processing.setTextColor(getResources().getColor(R.color.red));
                mbtn_Sold.setTextColor(getResources().getColor(R.color.red));

            }
        });
        mbtn_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycleViewAll.setVisibility(View.GONE);
                recycleViewLive.setVisibility(View.VISIBLE);

                mbtn_live.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_all.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_processing.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Expired.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Sold.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_live.setTextColor(getResources().getColor(R.color.white));
                mbtn_all.setTextColor(getResources().getColor(R.color.red));
                mbtn_Expired.setTextColor(getResources().getColor(R.color.red));
                mbtn_processing.setTextColor(getResources().getColor(R.color.red));
                mbtn_Sold.setTextColor(getResources().getColor(R.color.red));

            }
        });
        mbtn_processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_processing.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_all.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_live.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Expired.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Sold.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_processing.setTextColor(getResources().getColor(R.color.white));
                mbtn_all.setTextColor(getResources().getColor(R.color.red));
                mbtn_Expired.setTextColor(getResources().getColor(R.color.red));
                mbtn_live.setTextColor(getResources().getColor(R.color.red));
                mbtn_Sold.setTextColor(getResources().getColor(R.color.red));

            }
        });
        mbtn_Expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_Expired.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_all.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_live.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_processing.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Sold.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_Expired.setTextColor(getResources().getColor(R.color.white));
                mbtn_all.setTextColor(getResources().getColor(R.color.red));
                mbtn_processing.setTextColor(getResources().getColor(R.color.red));
                mbtn_live.setTextColor(getResources().getColor(R.color.red));
                mbtn_Sold.setTextColor(getResources().getColor(R.color.red));


            }
        });
        mbtn_Sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_Sold.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_all.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_live.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Expired.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_processing.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_Sold.setTextColor(getResources().getColor(R.color.white));
                mbtn_all.setTextColor(getResources().getColor(R.color.red));
                mbtn_processing.setTextColor(getResources().getColor(R.color.red));
                mbtn_live.setTextColor(getResources().getColor(R.color.red));
                mbtn_Expired.setTextColor(getResources().getColor(R.color.red));
            }
        });
        iv_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DeatilsListFragment NAME = new DeatilsListFragment();
                fragmentTransaction.replace(R.id.item_container, NAME);
                fragmentTransaction.commit();

            }
        });
        User_Id = SharedHelper.getKey(getActivity(), APPCONSTANT.user_Id);
        Log.e("tytfytyu", User_Id);
        view_car();
        return view;
    }

    public void view_car() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.view_car)
                .addBodyParameter("user_id",User_Id)
                .setTag("view_car")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        sellModelArrayList = new ArrayList<>();
                        Log.e("trgtffgf", "onResponse: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                SellModel sellModel = new SellModel();

                                sellModel.setId(jsonObject.getString("id"));
                                sellModel.setUser_id(jsonObject.getString("user_id"));
                                Log.e("gbngvbn", jsonObject.getString("user_id"));
                                sellModel.setName(jsonObject.getString("name"));
                                sellModel.setPrice(jsonObject.getString("price"));
                                sellModel.setLocation(jsonObject.getString("location"));
                                sellModel.setKm_driven(jsonObject.getString("km_driven"));
                                sellModel.setModel(jsonObject.getString("model"));
                                sellModel.setAutomatic(jsonObject.getString("automatic"));
                                sellModel.setCar_number(jsonObject.getString("car_number"));
                                sellModel.setAccidental(jsonObject.getString("accidental"));

                                JSONArray jsonArray = new JSONArray(jsonObject.getString("car_image"));
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                    sellModel.setImage(jsonObject1.getString("image"));
                                    sellModel.setPath(jsonObject1.getString("path"));
                                }
                                sellModelArrayList.add(sellModel);
                            }
                            recycleViewAll.setHasFixedSize(true);
                            recycleViewAll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recycleViewAll.setAdapter(new SellAdapter(sellModelArrayList, getActivity()));

                        } catch (Exception e) {
                            Log.e("iicfdikcfd", "onResponse: " + e.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dsdncfknsdkcf ", "onError: " + anError);
                    }
                });

    }
}