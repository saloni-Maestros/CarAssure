package com.kuldeep.carassure.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.kuldeep.carassure.Activity.CarDeatilsActivity;
import com.kuldeep.carassure.Activity.SellCarDeatilsActivity;
import com.kuldeep.carassure.Model.SellModel;
import com.kuldeep.carassure.Model.SliderModel;
import com.kuldeep.carassure.Model.SliderSellModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.Api;
import com.kuldeep.carassure.other.SharedHelper;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.MyViewHolder> {
ArrayList<SellModel> sellModelArrayList;
Context context;
    String User_Id = "";

RecyclerView recyclerMultipleImg,recycleViewAll;
MultipleImageAdapter multipleImageAdapter;
ProgressBar progressBar;

    public SliderSellAdapter sliderSellAdapter;
    private SliderView sliderView;
    ArrayList<SliderSellModel> sliderSellModels;


    public SellAdapter(ArrayList<SellModel> sellModelArrayList, Context context) {
        this.sellModelArrayList = sellModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sliderSellModels = new ArrayList<>();


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sellcarlist,parent,false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellAdapter.MyViewHolder holder, int position) {
        SellModel sellModel =  sellModelArrayList.get(position);


        if (! sellModel.equals("")){
        //    SharedHelper.putkey(context, APPCONSTANT.user_Id, sellModel.getUser_id());
          //  Log.e("rdgfghgf", sellModel.getUser_id());
            User_Id = SharedHelper.getKey(context, APPCONSTANT.user_Id);
            Log.e("tytfytyu", User_Id);
            holder.r2Main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedHelper.putkey(context, APPCONSTANT.ID, sellModel.getId());
                    Log.e("rtgtdgdfgfg", sellModel.getId());
                    Intent intent = new Intent(context, SellCarDeatilsActivity.class);  //adapter to activity
                    intent.putExtra("your_extra", "your_class_value");
                    context.startActivity(intent);
                }
            });
            holder.tv_CarName1.setText(sellModel.getName());
            holder.tv_Carprice.setText(sellModel.getPrice());
            holder.tv_Location.setText(sellModel.getLocation());
            holder.tv_kilometer.setText(sellModel.getKm_driven());
            holder.tv_modelNum.setText(sellModel.getModel());
            holder.tv_automaticee.setText(sellModel.getAutomatic());
            holder.tv_Accential.setText(sellModel.getAccidental());
            holder.tv_carNum.setText(sellModel.getCar_number());

             Glide.with(context).load(sellModel.getPath() + sellModel.getImage())
                    .placeholder(R.drawable.car1).override(250, 250)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_img);

        }










       // view_car();
    }

    @Override
    public int getItemCount() {
        return sellModelArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img,img_logo;
        TextView tv_CarName1,tv_Carprice,tv_Location,tv_kilometer,tv_modelNum,tv_automaticee,tv_Accential,tv_carNum;
        MaterialButton btn_edit;
        RelativeLayout relativelayout,recycleViewAll,r2Main;
        Adapter MultipleImageAdapter;
        ProgressBar progressBar;
        SliderView sliderView,img_slider1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_CarName1 = itemView.findViewById(R.id.tv_CarName1);
            tv_Carprice = itemView.findViewById(R.id.tv_Carprice);
            tv_Location = itemView.findViewById(R.id.tv_Location);
            tv_kilometer = itemView.findViewById(R.id.tv_kilometer);
            tv_modelNum = itemView.findViewById(R.id.tv_modelNum);
            tv_automaticee = itemView.findViewById(R.id.tv_automaticee);
            tv_Accential = itemView.findViewById(R.id.tv_Accential);
            tv_carNum = itemView.findViewById(R.id.tv_carNum);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            iv_img = itemView.findViewById(R.id.iv_img);
            img_logo = itemView.findViewById(R.id.img_logo);
            r2Main = itemView.findViewById(R.id.r2Main);
        //   sliderView = itemView.findViewById(R.id.img_slider1);
         //   img_slider1 = itemView.findViewById(R.id.img_slider1);
           // sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);


        }
    }

  /*  public void view_car() {
     //   progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Api.view_car)
                .addBodyParameter("user_id",User_Id)
                .setTag("view_car")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                   //     progressBar.setVisibility(View.GONE);
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
                                    SliderSellModel sliderSellModel  = new SliderSellModel();
                                    sliderSellModel.setImage(jsonObject1.getString("image"));
                                    sliderSellModel.setPath(jsonObject1.getString("path"));

                                }
                                sellModelArrayList.add(sellModel);

                            }
                            recycleViewAll.setHasFixedSize(true);
                            recycleViewAll.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            recycleViewAll.setAdapter(new SellAdapter(sellModelArrayList, context));

                        } catch (Exception e) {
                            Log.e("iicfdikcfd", "onResponse: " + e.getMessage());
                          //  progressBar.setVisibility(View.GONE);
//
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                 //       progressBar.setVisibility(View.GONE);
                        Log.e("dsdncfknsdkcf ", "onError: " + anError);
                    }
                });

    }
*/
}
