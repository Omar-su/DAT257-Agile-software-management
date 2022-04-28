package com.example.eurythmics.adapters;

import static android.os.Build.VERSION_CODES.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eurythmics.R;

public class RatingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    TextView title, description;
    ImageView poster;
    RatingBar ratingBar;

    // Click listener
    OnMovieCardListener onMovieCardListener;


    public RatingViewHolder(@NonNull View itemView, OnMovieCardListener onMovieCardListener) {
        super(itemView);

        title = itemView.findViewById(com.example.eurythmics.R.id.movie_title);
        description = itemView.findViewById(com.example.eurythmics.R.id.description);
        poster = itemView.findViewById(com.example.eurythmics.R.id.poster_img);
        ratingBar = itemView.findViewById(com.example.eurythmics.R.id.rating_bar_movie);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onMovieCardListener.onMovieClick(getAdapterPosition());
    }
}
