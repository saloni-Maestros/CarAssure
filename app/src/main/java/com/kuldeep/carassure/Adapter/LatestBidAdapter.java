package com.kuldeep.carassure.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Activity.CarDeatilsActivity;
import com.kuldeep.carassure.Model.LatestBidModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LatestBidAdapter extends RecyclerView.Adapter<LatestBidAdapter.MyViewHolder> {
    ArrayList<LatestBidModel> latestBidModelArrayList;
    Context context;
    String User_Id = "";
    String Car_Id= "";
    String strfav = "";

    public LatestBidAdapter(ArrayList<LatestBidModel> latestBidModelArrayList, Context context) {
        this.latestBidModelArrayList = latestBidModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.latestbidlist, parent, false);
        User_Id = SharedHelper.getKey(context, APPCONSTANT.user_Id);
      //  strfav = SharedHelper.getKey(context, APPCONSTANT.STATUS);
        Log.e("vdfsvfdvdf", User_Id);
       // Log.e("cnkxnx", strfav);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestBidAdapter.MyViewHolder holder, int position) {
        LatestBidModel latestBidModel = latestBidModelArrayList.get(position);
        if (!latestBidModel.equals("")) {
            try {
                Glide.with(context).load(latestBidModel.getPath() + latestBidModel.getImage())
                        // .placeholder(R.drawable.scropio).override(250, 250)
                        //  .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imgCars);
            } catch (Exception e) {
                Log.e("dxcxcvccv", "onBindViewHolder: " + e.getMessage());
            }

            Log.e("cchkkuuui", latestBidModel.getPath() + latestBidModel.getImage());


            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedHelper.putkey(context, APPCONSTANT.ID, latestBidModel.getId());
                    Log.e("vfvfvbbgb", latestBidModel.getFavourite());
                    Log.e("rtgtdgdfgfg", latestBidModel.getId());
                    Intent intent = new Intent(context, CarDeatilsActivity.class);  //adapter to activity
                    intent.putExtra("your_extra", "your_class_value");
                    context.startActivity(intent);
                }
            });
            holder.tv_name.setText(latestBidModel.getName());
            holder.tv_price.setText(latestBidModel.getPrice());
            holder.tv_location.setText(latestBidModel.getLocation());
            holder.tv_km.setText(latestBidModel.getKm_driven());
            holder.tv_modeldate.setText(latestBidModel.getModel());
            holder.tv_automatice.setText(latestBidModel.getAutomatic());
            holder.tv_accidentail.setText(latestBidModel.getAccidental());
            holder.tv_carNo.setText(latestBidModel.getCar_number());
        }


        if (latestBidModel.getFavourite().equals("1")){
            holder.likered.setVisibility(View.VISIBLE);
            holder.like.setVisibility(View.GONE);
        }else {
            holder.likered.setVisibility(View.GONE);
            holder.like.setVisibility(View.VISIBLE);
        }
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavouriteCar(latestBidModel.getId(), holder);
                Log.e("vjcnv", "onClick: " + latestBidModel.getId());
            }

        });
        holder.likered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnFavouriteCar(latestBidModel.getId(), holder);
            }

        });


    }

    @Override
    public int getItemCount() {
        return latestBidModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCars, like, likered;
        TextView tv_name, tv_price, tv_location, tv_km, tv_modeldate, tv_automatice, tv_accidentail, tv_carNo;
        MaterialButton btn_placebid;
        RelativeLayout rlMain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMain = itemView.findViewById(R.id.rlMain);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_km = itemView.findViewById(R.id.tv_km);
            tv_modeldate = itemView.findViewById(R.id.tv_modeldate);
            tv_automatice = itemView.findViewById(R.id.tv_automatice);
            tv_accidentail = itemView.findViewById(R.id.tv_accidentail);
            tv_carNo = itemView.findViewById(R.id.tv_carNo);
            btn_placebid = itemView.findViewById(R.id.btn_placebid);
            imgCars = itemView.findViewById(R.id.imgCars);
            like = itemView.findViewById(R.id.like);
            likered = itemView.findViewById(R.id.likered);

        }
    }

    public void addFavouriteCar(String car_id, MyViewHolder holder) {
        Log.e("kjdfcd", User_Id);
        Log.e("jhcfxdnc", car_id);
        Log.e("vcvc", strfav);
        AndroidNetworking.post(Api.addFavouriteCar)
                .addBodyParameter("user_id", User_Id)
                .addBodyParameter("car_id", car_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("fkjdkfcmkdf", "onResponse: " + response.toString());
                        try {
                            if (response.getString("message").equals("Like Successfully")) {
                                Toast.makeText(context, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = new JSONObject(response.getString("data"));

                                holder.likered.setVisibility(View.VISIBLE);
                                holder.like.setVisibility(View.GONE);
                            } else {
                                Toast toast = Toast.makeText(context, "" + response.getString("message"), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        } catch (Exception e) {
                            Log.e("hdcnjcndxcfn", "onResponse: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("hdcnjcndxcfn", "onError: " + anError);

                    }
                });
    }

    public void UnFavouriteCar(String car_id, MyViewHolder holder) {
        Log.e("kjdfcd", User_Id);
        Log.e("jhcfxdnc", car_id);
        AndroidNetworking.post(Api.addFavouriteCar)
                .addBodyParameter("user_id", User_Id)
                .addBodyParameter("car_id", car_id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("gvfdgvfgb", "onResponse: " + response.toString());
                        try {
                            if (response.getString("message").equals("Dislike Successfully")) {
                                Toast.makeText(context, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = new JSONObject(response.getString("data"));
                                holder.likered.setVisibility(View.GONE);
                                holder.like.setVisibility(View.VISIBLE);
                            }else {
                                Toast toast = Toast.makeText(context, "" + response.getString("message"), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }


                        } catch (Exception e) {
                            Log.e("hdcnjcndxcfn", "onResponse: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("kcjvmcv", "onError: " + anError);

                    }
                });
    }
}
