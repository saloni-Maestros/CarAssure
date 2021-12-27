package com.kuldeep.carassure.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Activity.LoginActivity;
import com.kuldeep.carassure.Adapter.BuyNowAdapter;
import com.kuldeep.carassure.Adapter.InventoryAdapter;
import com.kuldeep.carassure.Adapter.LatestBidAdapter;
import com.kuldeep.carassure.Adapter.SellAdapter;
import com.kuldeep.carassure.Model.BuyNowModel;
import com.kuldeep.carassure.Model.InventoryModel;
import com.kuldeep.carassure.Model.LatestBidModel;
import com.kuldeep.carassure.Model.SellModel;
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
TextView text_deals;

    RecyclerView recyclerInventory;
    ArrayList<InventoryModel> inventoryModelArrayList;
    InventoryAdapter inventoryAdapter;

    RecyclerView recyclerBuyNow;
    ArrayList<BuyNowModel> buyNowModelArrayList;
    BuyNowAdapter buyNowAdapter;

    String User_Id = "";
    String Price = "";
    String strfav = "";
    private final int BUTTON_STATE = 0;
    private final int BUTTON_STATE_ONCE = 0;
    private final int BUTTON_STATE_TWICE = 1;

    MaterialButton mbtn_latest, mbtn_Inventery, mbtn_Buynow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        recyclerlatestbid = view.findViewById(R.id.recyclerlatestbid);
        recyclerBuyNow = view.findViewById(R.id.recyclerBuyNow);
        text_deals = view.findViewById(R.id.text_deals);
        recyclerInventory = view.findViewById(R.id.recyclerInventory);
        progressBar = view.findViewById(R.id.progressBar);
        mbtn_Inventery = view.findViewById(R.id.mbtn_Inventery);
        mbtn_Buynow = view.findViewById(R.id.mbtn_Buynow);
        mbtn_latest = view.findViewById(R.id.mbtn_latest);
        mbtn_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.splashcolor));
                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_latest.setTextColor(getResources().getColor(R.color.white));
                mbtn_Inventery.setTextColor(getResources().getColor(R.color.splashcolor));
                mbtn_Buynow.setTextColor(getResources().getColor(R.color.splashcolor));

                recyclerlatestbid.setVisibility(View.VISIBLE);
                recyclerInventory.setVisibility(View.GONE);
                recyclerBuyNow.setVisibility(View.GONE);
                text_deals.setVisibility(View.VISIBLE);

            }
        });
        mbtn_Inventery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.splashcolor));
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_Inventery.setTextColor(getResources().getColor(R.color.white));
                mbtn_latest.setTextColor(getResources().getColor(R.color.splashcolor));
                mbtn_Buynow.setTextColor(getResources().getColor(R.color.splashcolor));

                recyclerlatestbid.setVisibility(View.GONE);
                recyclerInventory.setVisibility(View.VISIBLE);
                recyclerBuyNow.setVisibility(View.GONE);
                text_deals.setVisibility(View.GONE);
                My_Inventory();


            }
        });
        mbtn_Buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_Buynow.setBackgroundColor(getResources().getColor(R.color.splashcolor));
                mbtn_latest.setBackgroundColor(getResources().getColor(R.color.white));
                mbtn_Inventery.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_latest.setTextColor(getResources().getColor(R.color.splashcolor));
                mbtn_Inventery.setTextColor(getResources().getColor(R.color.splashcolor));
                mbtn_Buynow.setTextColor(getResources().getColor(R.color.white));

                recyclerlatestbid.setVisibility(View.GONE);
                recyclerInventory.setVisibility(View.GONE);
                recyclerBuyNow.setVisibility(View.VISIBLE);
                text_deals.setVisibility(View.GONE);

                show_buy_now();

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
     //  LatestBid();
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
                                    latestBidModel.setStart_time(jsonObject.getString("start_time"));
                                    latestBidModel.setEnd_time(jsonObject.getString("end_time"));
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

    public void My_Inventory() {
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
                        inventoryModelArrayList = new ArrayList<>();
                        Log.e("trgtffgf", "onResponse: " + response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                InventoryModel inventoryModel = new InventoryModel();
                                inventoryModel.setId(jsonObject.getString("id"));
                                inventoryModel.setUser_id(jsonObject.getString("user_id"));
                                Log.e("gbngvbn", jsonObject.getString("user_id"));
                                inventoryModel.setName(jsonObject.getString("name"));
                                inventoryModel.setPrice(jsonObject.getString("price"));
                                inventoryModel.setLocation(jsonObject.getString("location"));
                                inventoryModel.setKm_driven(jsonObject.getString("km_driven"));
                                inventoryModel.setModel(jsonObject.getString("model"));
                                inventoryModel.setAutomatic(jsonObject.getString("automatic"));
                                inventoryModel.setCar_number(jsonObject.getString("car_number"));
                                inventoryModel.setAccidental(jsonObject.getString("accidental"));
                                SharedHelper.putkey(getContext(), APPCONSTANT.PRICE,jsonObject.getString("price"));

                                Log.e("sjdksjdd", "onResponse: " +jsonObject.getString("price"));
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("car_image"));
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                    inventoryModel.setImage(jsonObject1.getString("image"));
                                    inventoryModel.setPath(jsonObject1.getString("path"));
                                }
                                inventoryModelArrayList.add(inventoryModel);
                            }
                            recyclerInventory.setHasFixedSize(true);
                            recyclerInventory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recyclerInventory.setAdapter(new InventoryAdapter(inventoryModelArrayList, getActivity()));

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

    public void show_buy_now(){
     /*   String User_Id = SharedHelper.getKey(getActivity(), APPCONSTANT.user_Id);
        Log.e("rfdsgfdgfdg", "stCategoryId: " + User_Id);*/
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.show_buy_now)
               .addBodyParameter("user_id", User_Id)
                .setTag("show_buy_now")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        buyNowModelArrayList  = new ArrayList<>();
                        Log.e("dhkkxf", "onResponse: " +response.toString());
                   try {
                        for (int i=0; i<response.length(); i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            BuyNowModel buyNowModel = new BuyNowModel();
                            buyNowModel.setId(jsonObject.getString("id"));
                            buyNowModel.setUser_id(jsonObject.getString("user_id"));
                            buyNowModel.setCar_id(jsonObject.getString("car_id"));

                            JSONObject jsonObject1 = jsonObject.getJSONObject("car");
                            buyNowModel.setId(jsonObject1.getString("id"));
                            buyNowModel.setUser_id(jsonObject1.getString("user_id"));
                            buyNowModel.setName(jsonObject1.getString("name"));
                            buyNowModel.setPrice(jsonObject1.getString("price"));
                            buyNowModel.setLocation(jsonObject1.getString("location"));
                            buyNowModel.setKm_driven(jsonObject1.getString("km_driven"));
                            buyNowModel.setModel(jsonObject1.getString("model"));
                            buyNowModel.setAutomatic(jsonObject1.getString("automatic"));
                            buyNowModel.setCar_number(jsonObject1.getString("car_number"));
                            buyNowModel.setAccidental(jsonObject1.getString("accidental"));

                            JSONArray jsonArray = new JSONArray(jsonObject.getString("car_img"));
                            for (int j = 0; j < jsonArray.length() ; j++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(j);
                                buyNowModel.setImage(jsonObject2.getString("image"));
                                buyNowModel.setPath(jsonObject2.getString("path"));
                            }
                            buyNowModelArrayList.add(buyNowModel);


                        }
                       recyclerBuyNow.setHasFixedSize(true);
                       recyclerBuyNow.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                       recyclerBuyNow.setAdapter(new BuyNowAdapter(buyNowModelArrayList, getActivity()));

                   }


                   catch (Exception e){
                       progressBar.setVisibility(View.GONE);
                       Log.e("dkdcfdskjcfj", "onResponse: " +e.getMessage());

                   }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("vjfjfckjfc", "onError: " +anError);

                    }
                });

    }
}