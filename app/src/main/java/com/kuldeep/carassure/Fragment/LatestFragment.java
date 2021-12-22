package com.kuldeep.carassure.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Adapter.LatestBidAdapter;
import com.kuldeep.carassure.Model.LatestBidModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class LatestFragment extends Fragment {
    RecyclerView recyclerlatestbid;
    ProgressBar progressBar;
    ArrayList<LatestBidModel> latestBidModelArrayList;
    LatestBidAdapter latestBidAdapter;
    String User_Id = "";
    String strfav = "";
    private final int BUTTON_STATE = 0;
    private final int BUTTON_STATE_ONCE = 0;
    private final int BUTTON_STATE_TWICE = 1;

    MaterialButton mbtn_latest, mbtn_Inventery, mbtn_Buynow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        recyclerlatestbid = view.findViewById(R.id.recyclerlatestbid);
        progressBar = view.findViewById(R.id.progressBar);
        mbtn_Inventery = view.findViewById(R.id.mbtn_Inventery);
        mbtn_Buynow = view.findViewById(R.id.mbtn_Buynow);
        mbtn_latest = view.findViewById(R.id.mbtn_latest);
        mbtn_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(BUTTON_STATE==BUTTON_STATE_ONCE){

                    mbtn_latest.setTextColor(getResources().getColor(R.color.white));
                    BUTTON_STATE = BUTTON_STATE_TWICE;
                }else if(BUTTON_STATE==BUTTON_STATE_TWICE){

                    mbtn_latest.setTextColor(getResources().getColor(R.color.rad));
                    BUTTON_STATE = BUTTON_STATE_ONCE;
                }*/
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_latest.setTextColor(getResources().getColor(R.color.white));
                mbtn_Inventery.setTextColor(getResources().getColor(R.color.red));
                mbtn_Buynow.setTextColor(getResources().getColor(R.color.red));
                recyclerlatestbid.setVisibility(View.VISIBLE);
            }
        });
        mbtn_Inventery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_Inventery.setTextColor(getResources().getColor(R.color.white));
                mbtn_latest.setTextColor(getResources().getColor(R.color.red));
                mbtn_Buynow.setTextColor(getResources().getColor(R.color.red));
                recyclerlatestbid.setVisibility(View.GONE);

            }
        });
        mbtn_Buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.red));
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_latest.setTextColor(getResources().getColor(R.color.red));
                mbtn_Inventery.setTextColor(getResources().getColor(R.color.red));
                mbtn_Buynow.setTextColor(getResources().getColor(R.color.white));

                recyclerlatestbid.setVisibility(View.GONE);

            }
        });


        User_Id = SharedHelper.getKey(getActivity(), APPCONSTANT.user_Id);
        Log.e("hdjsdsdc", User_Id);
        strfav = SharedHelper.getKey(getActivity(), APPCONSTANT.STATUS);
        Log.e("hdjsdsdc", strfav);
        LatestBid();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LatestBid();
    }

    public void LatestBid() {
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.showCar)
                .addBodyParameter("user_id", User_Id)
                .setTag("showCar")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        latestBidModelArrayList = new ArrayList<>();
                        Log.e("jkdjcdcmfcf", "onResponse: " + response.toString());
                        try {
                            if (response.getString("result").equals("true")) {
                                JSONArray jsonArray = new JSONArray(response.getString("data"));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    LatestBidModel latestBidModel = new LatestBidModel();
                                    latestBidModel.setId(jsonObject.getString("id"));
                                    latestBidModel.setName(jsonObject.getString("name"));
                                    latestBidModel.setPrice(jsonObject.getString("price"));
                                    latestBidModel.setLocation(jsonObject.getString("location"));
                                    latestBidModel.setKm_driven(jsonObject.getString("km_driven"));
                                    latestBidModel.setModel(jsonObject.getString("model"));
                                    latestBidModel.setAutomatic(jsonObject.getString("automatic"));
                                    latestBidModel.setCar_number(jsonObject.getString("car_number"));
                                    latestBidModel.setAccidental(jsonObject.getString("accidental"));
                                    latestBidModel.setImage(jsonObject.getString("image"));
                                    latestBidModel.setFavourite(jsonObject.getString("favourite"));
                                    latestBidModel.setPath(jsonObject.getString("path"));
                                    latestBidModelArrayList.add(latestBidModel);

                                }

                            }

                            recyclerlatestbid.setHasFixedSize(true);
                            recyclerlatestbid.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
                            recyclerlatestbid.setAdapter(new LatestBidAdapter(latestBidModelArrayList, getActivity()));

                        } catch (Exception e) {
                            Log.e("fgvfdf", e.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("fjkdf", anError.getMessage());
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }

}