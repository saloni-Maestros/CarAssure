package com.kuldeep.carassure.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kuldeep.carassure.Activity.BidPriceActivity;
import com.kuldeep.carassure.Activity.HomeActivity;
import com.kuldeep.carassure.Adapter.FavoritesCarAdapter;
import com.kuldeep.carassure.Adapter.LatestBidAdapter;
import com.kuldeep.carassure.Model.FavoritesCarModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavouriteCarFragment extends Fragment {
    ImageView iv_back;
    ProgressBar progressBar;
    String  User_Id = "";
    String Car_Id = "";

    RecyclerView recycler_favrouite;
    ArrayList<FavoritesCarModel> favoritesCarModelArrayList;
    FavoritesCarAdapter favoritesCarAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_car, container, false);
       /* Car_Id = SharedHelper.getKey(getContext(), APPCONSTANT.CAR_ID);
        Log.e("jkcxc", Car_Id);*/

        recycler_favrouite = view.findViewById(R.id.recycler_favrouite);
        progressBar = view.findViewById(R.id.progressBar);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HomeActivity.class));

            }
        });
        showFavouriteCar();
        return view;

    }
    public void showFavouriteCar(){
        progressBar.setVisibility(View.VISIBLE);
        String User_Id = SharedHelper.getKey(getActivity(), APPCONSTANT.user_Id);
        Log.e("rfdsgfdgfdg", "stCategoryId: " + User_Id);
        AndroidNetworking.post(Api.showFavouriteCar)
                .addBodyParameter("user_id", User_Id)
                .setTag("showFavouriteCar")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        favoritesCarModelArrayList = new ArrayList<>();
                        Log.e("jgbjhgvhf", "onResponse: " +response.toString());
                        try {
                            if (response.getString("message").equals("Successfull")){
                                JSONArray jsonArray = new JSONArray(response.getString("data"));
                               for (int i = 0; i<jsonArray.length(); i++){
                                   JSONObject jsonObject =  jsonArray.getJSONObject(i);
                                   FavoritesCarModel favoritesCarModel =  new FavoritesCarModel();
                                   favoritesCarModel.setId(jsonObject.getString("id"));
                                   favoritesCarModel.setUser_id(jsonObject.getString("user_id"));
                                   Log.e("jmdxkfmkvf", jsonObject.getString("id"));
                                   favoritesCarModel.setCar_id(jsonObject.getString("car_id"));
                                   Log.e("jmdxkfmkvf", jsonObject.getString("car_id"));

                                   favoritesCarModel.setName(jsonObject.getString("name"));
                                   favoritesCarModel.setPrice(jsonObject.getString("price"));
                                   favoritesCarModel.setLocation(jsonObject.getString("location"));
                                   favoritesCarModel.setKm_driven(jsonObject.getString("km_driven"));
                                   favoritesCarModel.setModel(jsonObject.getString("model"));
                                   favoritesCarModel.setAutomatic(jsonObject.getString("automatic"));
                                   favoritesCarModel.setAccidental(jsonObject.getString("accidental"));
                                   favoritesCarModel.setCar_number(jsonObject.getString("car_number"));

                                 JSONArray jsonArray1 = new JSONArray(jsonObject.getString("image"));
                                   for (int j = 0; j < jsonArray1.length() ; j++) {
                                       JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                       favoritesCarModel.setCar_id(jsonObject1.getString("car_id"));
                                       favoritesCarModel.setImage(jsonObject1.getString("image"));
                                       favoritesCarModel.setPath(jsonObject1.getString("path"));
                                   }
                                   favoritesCarModelArrayList.add(favoritesCarModel);

                               }
                                recycler_favrouite.setHasFixedSize(true);
                                recycler_favrouite.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
                                recycler_favrouite.setAdapter(new FavoritesCarAdapter(favoritesCarModelArrayList, getActivity()));

                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Log.e("cjkldjmkcd", "onResponse: " +e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("sjmdkws", "onError: " +anError);
                    }
                });

    }

}