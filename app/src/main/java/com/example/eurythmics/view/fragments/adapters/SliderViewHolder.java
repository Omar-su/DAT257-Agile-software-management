package com.example.eurythmics.view.fragments.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eurythmics.R;
import com.makeramen.roundedimageview.RoundedImageView;
/**
 * A view holder which represents the cards which will be shown on the page view
 * @author  Omar Sulaiman
 */
public class SliderViewHolder extends RecyclerView.ViewHolder{

    RoundedImageView imageView;

    public SliderViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageSlider);
    }

}
