package com.example.eurythmics.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.models.MovieModel;

import java.util.List;

public class RatingRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<MovieModel> mMovies;
    private OnMovieCardListener onMovieCardListener;


    public RatingRecycleViewAdapter(OnMovieCardListener onMovieCardListener) {
        this.onMovieCardListener = onMovieCardListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_serie_card_row_item,parent,false);
        return new RatingViewHolder(view, onMovieCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        ((RatingViewHolder)holder).title.setText(mMovies.get(i).getTitle());
        ((RatingViewHolder)holder).description.setText(mMovies.get(i).getOverview());


        ((RatingViewHolder)holder).durationTextView.setText(String.valueOf(mMovies.get(i).getReleaseDate()));
        //((RatingViewHolder)holder).categoryTextView.setText(mMovies.get(i).getCategory);



        // Image using glide library

        Glide.with(holder.itemView.getContext()).load(Credentials.IMG_BASE_URL +mMovies.get(i).getPosterPath()).into(((RatingViewHolder)holder).poster);




    }

    @Override
    public int getItemCount() {
        if (mMovies!=null){
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies){
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

}
