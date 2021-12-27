package com.kuldeep.carassure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Adapter.SliderAdapterExample;
import com.kuldeep.carassure.Model.SliderModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarDeatilsActivity extends AppCompatActivity {
    MaterialButton mbtn_placebid;
    ProgressBar progressBar;
    ImageView iv_back;
    String car_id = "";
    String ID = "";
    String  User_Id = "",Car_Id = "";

    public SliderAdapterExample sliderAdapter;
    private SliderView sliderView;
    ArrayList<SliderModel> sliderModels;
    JSONArray jsonArray;
    TextView tv_carName,tv_carprice,tv_location1,tv_km1,tv_model1,tv_automatice1,tv_nonAccidental1,tv_carno1,tv_fueltype,tv_seter,
            tv_kmpl,tv_cc,tv_NonAccidental,tv_Hard,tv_Ok,tv_Ok1,tv_dec,tv_Ok2,tv_Ok3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_deatils);
        sliderModels = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);

        tv_carName = findViewById(R.id.tv_carName);
        tv_carprice = findViewById(R.id.tv_carprice);
        tv_location1 = findViewById(R.id.tv_location1);
        tv_km1 = findViewById(R.id.tv_km1);
        tv_model1 = findViewById(R.id.tv_model1);
        tv_automatice1 = findViewById(R.id.tv_automatice1);
        tv_nonAccidental1 = findViewById(R.id.tv_nonAccidental1);
        tv_carno1 = findViewById(R.id.tv_carno1);
        tv_fueltype = findViewById(R.id.tv_fueltype);
        tv_seter = findViewById(R.id.tv_seter);
        tv_kmpl = findViewById(R.id.tv_kmpl);
        tv_cc = findViewById(R.id.tv_cc);
        tv_NonAccidental = findViewById(R.id.tv_NonAccidental);
        tv_Hard = findViewById(R.id.tv_Hard);
        tv_Ok = findViewById(R.id.tv_Ok);
        tv_Ok1 = findViewById(R.id.tv_Ok1);
        tv_dec = findViewById(R.id.tv_dec);
        tv_Ok2 = findViewById(R.id.tv_Ok2);
        tv_Ok3 = findViewById(R.id.tv_Ok3);

        sliderView = findViewById(R.id.img_slider);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        car_id = SharedHelper.getKey(CarDeatilsActivity.this, APPCONSTANT.car_id);
        ID = SharedHelper.getKey(CarDeatilsActivity.this, APPCONSTANT.ID);
        Log.e("gbgf", ID);
        Log.e("dfd", car_id);

        User_Id = SharedHelper.getKey(CarDeatilsActivity.this,APPCONSTANT.user_Id);
        Car_Id = SharedHelper.getKey(CarDeatilsActivity.this, APPCONSTANT.CAR_ID);
        Log.e("hdjsdsdc",Car_Id );
        Log.e("hdjsdsdc",User_Id );



        mbtn_placebid = findViewById(R.id.mbtn_placebid);
        mbtn_placebid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaceBid();
               // startActivity(new Intent(CarDeatilsActivity.this,BidPriceActivity.class));
            }
        });
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        showCarDetails();
    }

    public void showCarDetails(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.showCarDetails)
                .addBodyParameter("user_id", User_Id)
                .addBodyParameter("car_id", ID)
                .setPriority(Priority.HIGH)
                .setTag("showCarDetails")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("kldslksdc", "onResponse: " +response.toString());
                       try {
                           if (response.getString("result").equals("true")){
                             JSONArray jsonArray = new JSONArray(response.getString("data"));
                               for (int i = 0; i < jsonArray.length(); i++) {
                                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                                       jsonObject.getString("id");
                                   tv_carName.setText(jsonObject.getString("name"));
                                   tv_carprice.setText(jsonObject.getString("price"));
                                   tv_location1.setText(jsonObject.getString("location"));
                                   tv_km1.setText(jsonObject.getString("km_driven"));
                                   tv_model1.setText(jsonObject.getString("model"));
                                   tv_automatice1.setText(jsonObject.getString("automatic"));
                                   tv_nonAccidental1.setText(jsonObject.getString("accidental"));
                                   tv_model1.setText(jsonObject.getString("car_number"));
                                   tv_fueltype.setText(jsonObject.getString("fuel_type"));
                                   tv_seter.setText(jsonObject.getString("sitting_capicity"));
                                   tv_kmpl.setText(jsonObject.getString("average_of_car_in_km"));
                                   tv_cc.setText(jsonObject.getString("engine_in_cc"));
                                   tv_NonAccidental.setText(jsonObject.getString("accidental"));
                                   tv_Hard.setText(jsonObject.getString("clutch_function"));
                                   tv_Ok.setText(jsonObject.getString("brake_function"));
                                   tv_Ok1.setText(jsonObject.getString("brake_condition_summary"));
                                   tv_dec.setText(jsonObject.getString("special_feature_summary"));
                                   tv_Ok2.setText(jsonObject.getString("engine_leak_summary"));
                                   tv_Ok3.setText(jsonObject.getString("air_conditioning"));
                                    SharedHelper.putkey(CarDeatilsActivity.this,APPCONSTANT.CAR_ID,jsonObject.getString("id"));
                                   Log.e("hcfjcfkdcf", jsonObject.getString("id"));

                                   jsonArray = new JSONArray(jsonObject.getString("image"));
                                   for (int j = 0; j < jsonArray.length(); j++) {
                                       JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                       SliderModel sliderModel = new SliderModel();
                                       sliderModel.setImage(jsonObject1.optString("image"));
                                       sliderModel.setPath(jsonObject1.optString("path"));
                                       sliderModels.add(sliderModel);
                                   }
                                   Log.e("INSIDE : ", sliderModels.size() + "");
                                  sliderAdapter  = new SliderAdapterExample(sliderModels, CarDeatilsActivity.this);
                                   sliderView.setSliderAdapter(sliderAdapter);


                               }
                           }

                       } catch (Exception e){
                           progressBar.setVisibility(View.GONE);
                           Log.e("ikfcdjkdcf", "onResponse: " +e.getMessage());

                       }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("cnjcnc", "onError: " +anError);

                    }
                });

    }
    public void  PlaceBid(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.place_a_bid)
                .addBodyParameter("user_id", User_Id)
                .addBodyParameter("car_id" ,Car_Id)
                .setTag("place_a_bid")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("shfjdfj", "onResponse: " +response.toString());
                        try {
                            if (response.getString("result").equals("bid placed success")){
                                Toast.makeText(CarDeatilsActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                                tv_carprice.setText(response.getString("price"));
                                JSONObject jsonObject = new JSONObject(response.getString("bid_detail"));
                            }else {
                                Toast.makeText(CarDeatilsActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Log.e("xmxnmxcn", "onResponse: " +e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("kxcnckcn", "onError: " +anError);

                    }
                });


    }
}