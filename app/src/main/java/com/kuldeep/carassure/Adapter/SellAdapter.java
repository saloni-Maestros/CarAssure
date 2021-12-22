package com.kuldeep.carassure.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Model.SellModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.SharedHelper;

import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.MyViewHolder> {
ArrayList<SellModel> sellModelArrayList;
Context context;

    public SellAdapter(ArrayList<SellModel> sellModelArrayList, Context context) {
        this.sellModelArrayList = sellModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        Log.e("dfsdfdfdff", sellModel.getName());

    }

    @Override
    public int getItemCount() {
        return sellModelArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img;
        TextView tv_CarName1,tv_Carprice,tv_Location,tv_kilometer,tv_modelNum,tv_automaticee,tv_Accential,tv_carNum;
        MaterialButton btn_edit;
        RelativeLayout relativelayout;

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
            relativelayout = itemView.findViewById(R.id.relativelayout);
        }
    }
}
