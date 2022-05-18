package com.example.eurythmics.view.fragments.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RatingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    // Movie card components
    TextView title, description, categoryTextView, releaseDateTextView;
    ImageView poster;


    // Click listener
    OnMovieCardListener onMovieCardListener;

    /**
     * connects the items to the view
     * @param itemView the view
     * @param onMovieCardListener card listener
     */
    public RatingViewHolder(@NonNull View itemView, OnMovieCardListener onMovieCardListener) {
        super(itemView);

        this.onMovieCardListener = onMovieCardListener;

        title = itemView.findViewById(com.example.eurythmics.R.id.movie_card_title);
        description = itemView.findViewById(com.example.eurythmics.R.id.description_card);
        poster = itemView.findViewById(com.example.eurythmics.R.id.poster_img);
        categoryTextView = itemView.findViewById(com.example.eurythmics.R.id.category_textView);
        releaseDateTextView = itemView.findViewById(com.example.eurythmics.R.id.releaseDate_text);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onMovieCardListener.onMovieClick(getAdapterPosition());
    }
}
