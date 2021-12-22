package com.kuldeep.carassure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.kuldeep.carassure.Model.FavoritesCarModel;
import com.kuldeep.carassure.R;
import com.kuldeep.carassure.other.APPCONSTANT;
import com.kuldeep.carassure.other.SharedHelper;

import java.util.ArrayList;

public class FavoritesCarAdapter extends RecyclerView.Adapter<FavoritesCarAdapter.MyViewHolder> {
    String User_Id = "";
    ArrayList<FavoritesCarModel> favoritesCarModelArrayList;
    Context context;

    public FavoritesCarAdapter( ArrayList<FavoritesCarModel> favoritesCarModelArrayList, Context context) {

        this.favoritesCarModelArrayList = favoritesCarModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favcarlist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesCarAdapter.MyViewHolder holder, int position) {
        FavoritesCarModel favoritesCarModel = favoritesCarModelArrayList.get(position);
        if (!favoritesCarModel.equals("")){
         //   SharedHelper.putkey(context, APPCONSTANT.ID, favoritesCarModel.getId());
            holder.tv_Carname.setText(favoritesCarModel.getName());
            holder.tv_Carprice.setText(favoritesCarModel.getPrice());
            holder.tv_Carlocation.setText(favoritesCarModel.getLocation());
            holder.tv_Carkm.setText(favoritesCarModel.getKm_driven());
            holder.tv_modelno.setText(favoritesCarModel.getModel());
            holder.tv_Carautomatice.setText(favoritesCarModel.getAutomatic());
            holder.tv_Caraccidentail.setText(favoritesCarModel.getAccidental());
            holder.tv_Carnumber.setText(favoritesCarModel.getCar_number());
            Glide.with(context).load(favoritesCarModel.getPath() + favoritesCarModel.getImage())
                     .placeholder(R.drawable.tataharrier).override(250, 250)
                      .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_harrier);
        }


    }

    @Override
    public int getItemCount() {
        return favoritesCarModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_harrier;
        TextView tv_Carname,tv_Carprice,tv_Carlocation,tv_Carkm,tv_modelno,tv_Carautomatice,tv_Caraccidentail,tv_Carnumber;
        MaterialButton btn_Favplacebid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_Favplacebid = itemView.findViewById(R.id.btn_Favplacebid);
            tv_Carname = itemView.findViewById(R.id.tv_Carname);
            tv_Carprice = itemView.findViewById(R.id.tv_Carprice);
            tv_Carlocation = itemView.findViewById(R.id.tv_Carlocation);
            tv_Carkm = itemView.findViewById(R.id.tv_Carkm);
            tv_modelno = itemView.findViewById(R.id.tv_modelno);
            tv_Carautomatice = itemView.findViewById(R.id.tv_Carautomatice);
            tv_Caraccidentail = itemView.findViewById(R.id.tv_Caraccidentail);
            tv_Carnumber = itemView.findViewById(R.id.tv_Carnumber);
            img_harrier = itemView.findViewById(R.id.img_harrier);
        }
    }
}
