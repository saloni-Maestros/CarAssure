package com.kuldeep.carassure.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Adapter.BuyNowAdapter;
import com.kuldeep.carassure.Adapter.PastCarAdapter;
import com.kuldeep.carassure.Model.PastCarModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAuctionsFragment extends Fragment {
    String User_Id = "";
MaterialButton mbtn_ongoging,mbtn_past;
ProgressBar progressBar;

RecyclerView recyclerPast;
ArrayList<PastCarModel> pastCarModelArrayList;
PastCarAdapter pastCarAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_auctions, container, false);
        mbtn_past = view.findViewById(R.id.mbtn_past);
        mbtn_ongoging = view.findViewById(R.id.mbtn_ongoging);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerPast = view.findViewById(R.id.recyclerPast);
        mbtn_ongoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_ongoging.setBackgroundColor(getResources().getColor(R.color.splashcolor));
                mbtn_past.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_ongoging.setTextColor(getResources().getColor(R.color.white));
                mbtn_past.setTextColor(getResources().getColor(R.color.splashcolor));
                recyclerPast.setVisibility(View.GONE);

            }
        });
        mbtn_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbtn_past.setBackgroundColor(getResources().getColor(R.color.splashcolor));
                mbtn_ongoging.setBackgroundColor(getResources().getColor(R.color.white));

                mbtn_past.setTextColor(getResources().getColor(R.color.white));
                mbtn_ongoging.setTextColor(getResources().getColor(R.color.splashcolor));
                recyclerPast.setVisibility(View.VISIBLE);
                show_past_bid_details();


            }
        });

        User_Id = SharedHelper.getKey(getContext(), APPCONSTANT.user_Id);
        Log.e("fcjdfhcjdfh", User_Id);
        return view;
    }

    public void show_past_bid_details(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.show_past_bid_details)
                .addBodyParameter("user_id", User_Id)
                .setTag("show_past_bid_details")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        pastCarModelArrayList = new ArrayList<>();
                        Log.e("dhdfcjfc", "onResponse: " +response.toString());
                        try {
                            for (int i=0; i<response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                PastCarModel pastCarModel = new PastCarModel();
                                pastCarModel.setId(jsonObject.getString("id"));
                                pastCarModel.setUser_id(jsonObject.getString("user_id"));
                                pastCarModel.setBid_price(jsonObject.getString("bid_price"));

                                JSONObject jsonObject1 = jsonObject.getJSONObject("detail");
                                pastCarModel.setId(jsonObject.getString("id"));
                                pastCarModel.setUser_id(jsonObject1.getString("user_id"));
                                pastCarModel.setName(jsonObject1.getString("name"));
                                pastCarModel.setPrice(jsonObject1.getString("price"));
                                pastCarModel.setLocation(jsonObject1.getString("location"));
                                pastCarModel.setKm_driven(jsonObject1.getString("km_driven"));
                                pastCarModel.setModel(jsonObject1.getString("model"));
                                pastCarModel.setAutomatic(jsonObject1.getString("automatic"));
                                pastCarModel.setAccidental(jsonObject1.getString("accidental"));

                                JSONArray jsonArray = new JSONArray(jsonObject.getString("car_images_detail"));
                                for (int j=0; j<jsonArray.length(); j++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(j);
                                    pastCarModel.setImage(jsonObject2.getString("image"));
                                    pastCarModel.setPath(jsonObject2.getString("path"));

                                }
                                 pastCarModelArrayList.add(pastCarModel);


                            }
                             recyclerPast.setHasFixedSize(true);
                            recyclerPast.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recyclerPast.setAdapter(new PastCarAdapter(pastCarModelArrayList, getActivity()));
                        } catch (Exception e){
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