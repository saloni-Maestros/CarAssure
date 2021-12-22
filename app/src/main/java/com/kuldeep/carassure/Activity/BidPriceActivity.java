package com.kuldeep.carassure.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONObject;

public class BidPriceActivity extends AppCompatActivity {
    MaterialButton mbtn_submit;
    String ID = "";
    String Car_Id = "", User_Id = "";
    EditText et_price;
    ProgressBar progressBar;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_price);

        Car_Id = SharedHelper.getKey(BidPriceActivity.this, APPCONSTANT.CAR_ID);
        User_Id = SharedHelper.getKey(BidPriceActivity.this,APPCONSTANT.user_Id);
        Log.e("hdjsdsdc",Car_Id );
        Log.e("hdjsdsdc",User_Id );
        iv_back = findViewById(R.id.iv_back);
        progressBar = findViewById(R.id.progressBar);
        mbtn_submit = findViewById(R.id.mbtn_submit);
        et_price = findViewById(R.id.et_price);
        mbtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_price.getText().toString().trim().isEmpty()){


                    Toast.makeText(BidPriceActivity.this, "please Enter your Bid Price", Toast.LENGTH_SHORT).show();
                } else {
                    PlaceBid();

                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void PlaceBid(){
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.placeBid)
           .addBodyParameter("user_id",User_Id)
            .addBodyParameter("car_id", Car_Id)
                .addBodyParameter("bid_amount", et_price.getText().toString().trim())
                .setTag("placeBid")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("cnxmcnxc", "onResponse: " +response.toString());
                        try {
                            if (response.getString("message").equals("Successfully Bid")){
                                Toast.makeText(BidPriceActivity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                               // startActivity(new Intent(BidPriceActivity.this, SuccessfullyBidActivity.class));
                                BottomSheetDialog bottomSheerDialog = new BottomSheetDialog(BidPriceActivity.this);
                                View parentView = getLayoutInflater().inflate(R.layout.popup_successfully_bid,null);
                                bottomSheerDialog.setContentView(parentView);
                                //  BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parentView.getParent());
                                //bottomSheetBehavior.setPeekHeight(int);
                                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics());
                                MaterialButton mbtn_backhome = bottomSheerDialog.findViewById(R.id.mbtn_backhome);


                                mbtn_backhome.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(BidPriceActivity.this, HomeActivity.class));

                                    }
                                });

                                bottomSheerDialog.show();

                            } else {
                                Toast.makeText(BidPriceActivity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            Log.e("gtffh", "onResponse: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("jmvkcmvm ", "onError: " +anError);

                    }
                });
    }
}