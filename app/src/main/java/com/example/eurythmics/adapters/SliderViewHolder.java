package com.example.eurythmics.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.api.Credentials;
import com.makeramen.roundedimageview.RoundedImageView;

public class SliderViewHolder extends RecyclerView.ViewHolder{

    RoundedImageView imageView;

    public SliderViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageSlider);
    }


}
