package com.example.eurythmics.view.fragments.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.model.api.models.MovieModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> savedMovies;
    private OnMovieCardListener onMovieCardListener;


    public HomeRecycleViewAdapter(OnMovieCardListener onMovieCardListener){
        this.onMovieCardListener = onMovieCardListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_movies_card, parent, false);

        return new HomeViewHolder(view, onMovieCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((HomeViewHolder)holder).cardTitle.setText(savedMovies.get(i).getTitle());
        ((HomeViewHolder)holder).cardDescription.setText(savedMovies.get(i).getOverview());
        MovieService ms = MovieService.getMovieService();
        float rating = (float) ms.getOverallRating(savedMovies.get(i).getMovie_id());
        Log.d("rfs", "_____________"+ rating/2);
        ((HomeViewHolder)holder).movieRating.setRating(rating/2);
        setPoster(holder, i);

    }

    @Override
    public int getItemCount() {
        if (savedMovies!=null){
            return savedMovies.size();
        }
        return 0;    }

    private void setPoster(RecyclerView.ViewHolder holder, int i) {
        // Image using glide library
        String moviePosterPath = savedMovies.get(i).getPosterPath();
        if (moviePosterPath!=null){
            Glide.with(holder.itemView.getContext()).load(Credentials.IMG_BASE_URL + moviePosterPath).into(((HomeViewHolder) holder).cardPoster);
        }
    }

    public void setSavedMovies(List<MovieModel> savedMovies){
        this.savedMovies = savedMovies;
        for (MovieModel m: savedMovies){
            m.setCategory(m.getCategoryFromDetailMov());
        }
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position){
        if (savedMovies != null){
            if (savedMovies.size() > 0){
                return savedMovies.get(position);
            }
        }
        return null;
    }

}
