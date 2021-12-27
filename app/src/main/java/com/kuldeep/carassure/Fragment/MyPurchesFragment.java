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
import com.kuldeep.carassure.Adapter.MyPurchasingAdapter;
import com.kuldeep.carassure.Adapter.PastCarAdapter;
import com.kuldeep.carassure.Model.MyPurchasingModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPurchesFragment extends Fragment {
MaterialButton mbtn_ongoging1, mbtn_Completed;
RecyclerView recyclerMyPucshasing;
ArrayList<MyPurchasingModel> myPurchasingModelArrayList;
MyPurchasingAdapter myPurchasingAdapter;
ProgressBar  progressBar;
String User_Id = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_purches, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerMyPucshasing = view.findViewById(R.id.recyclerMyPucshasing);

        User_Id = SharedHelper.getKey(getContext(), APPCONSTANT.user_Id);
        Log.e("fcjdfhcjdfh", User_Id);
        my_purchase();
        return view;
    }
     public void my_purchase(){
        progressBar.setVisibility(View.VISIBLE);
         AndroidNetworking.post(Api.my_purchase)
                 .addBodyParameter("user_id", User_Id)
                 .setTag("my_purchase")
                 .setPriority(Priority.HIGH)
                 .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        myPurchasingModelArrayList = new ArrayList<>();
                        Log.e("sjdjfjf", "onResponse: " +response.toString());
                        try {
                            for (int i=0; i<response.length(); i++){
                                JSONObject jsonObject = response.getJSONObject(i);
                                MyPurchasingModel myPurchasingModel = new MyPurchasingModel();

                                JSONObject jsonObject1 = jsonObject.getJSONObject("car_details");
                                myPurchasingModel.setId(jsonObject1.getString("id"));
                                myPurchasingModel.setUser_id(jsonObject1.getString("user_id"));
                                myPurchasingModel.setName(jsonObject1.getString("name"));
                                myPurchasingModel.setPrice(jsonObject1.getString("price"));
                                myPurchasingModel.setLocation(jsonObject1.getString("location"));
                                myPurchasingModel.setKm_driven(jsonObject1.getString("km_driven"));
                                myPurchasingModel.setModel(jsonObject1.getString("model"));
                                myPurchasingModel.setAutomatic(jsonObject1.getString("automatic"));
                                myPurchasingModel.setAccidental(jsonObject1.getString("accidental"));
                                myPurchasingModel.setModel(jsonObject1.getString("car_number"));

                                JSONArray jsonArray = new JSONArray(jsonObject.getString("car_images"));
                                for (int j=0; j<jsonArray.length(); j++){
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(j);
                                    myPurchasingModel.setImage(jsonObject2.getString("image"));
                                    myPurchasingModel.setPath(jsonObject2.getString("path"));

                                }
                                myPurchasingModelArrayList.add(myPurchasingModel);

                            }
                            recyclerMyPucshasing.setHasFixedSize(true);
                            recyclerMyPucshasing.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recyclerMyPucshasing.setAdapter(new MyPurchasingAdapter(myPurchasingModelArrayList, getActivity()));
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