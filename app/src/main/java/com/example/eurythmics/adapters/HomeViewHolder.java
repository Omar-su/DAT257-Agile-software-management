package com.example.eurythmics.adapters;

import android.content.pm.LabeledIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eurythmics.R;

public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnMovieCardListener onMovieCardListener;

     TextView cardTitle, cardDescription;
     RatingBar movieRating;
     ImageView cardPoster;

    public HomeViewHolder(@NonNull View itemView, OnMovieCardListener onMovieCardListener) {
        super(itemView);
        this.onMovieCardListener = onMovieCardListener;

        cardTitle = itemView.findViewById(R.id.saved_movie_card_title);
        cardDescription = itemView.findViewById(R.id.description_card);
        movieRating = itemView.findViewById(R.id.rating_bar_card);
        cardPoster = itemView.findViewById(R.id.poster_saved_card_img);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onMovieCardListener.onMovieClick(getAdapterPosition());
    }
}
