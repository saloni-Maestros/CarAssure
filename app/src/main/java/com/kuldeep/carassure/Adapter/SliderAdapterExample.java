package com.kuldeep.carassure.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kuldeep.carassure.Model.SliderModel;
import com.kuldeep.carassure.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapterExample  extends SliderViewAdapter <SliderAdapterExample.SliderAdapterVH>{
    ArrayList<SliderModel> sliderModels;
    Context context;

    public SliderAdapterExample(ArrayList<SliderModel> sliderModels, Context context) {
        this.sliderModels = sliderModels;
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterExample.SliderAdapterVH viewHolder, int position) {
        final  SliderModel sliderModel=sliderModels.get(position);
        Log.e("sliderModels : ", sliderModels.size() + " ");

        if (!sliderModel.equals("")){
            Glide.with(context).load(sliderModel.getPath()+sliderModel.getImage()).into(viewHolder.imglogo);
        }


    }

    @Override
    public int getCount() {
        return sliderModels.size();
    }
    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imglogo;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imglogo = itemView.findViewById(R.id.imglogo);
            this.itemView = itemView;
        }
    }
}
