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
/**
 * Class that represents the adapter for the list in the {@link com.example.eurythmics.view.fragments.fragments.ProfileFragment}
 * and {@link  com.example.eurythmics.view.fragments.fragments.RatedMoviesCollectionFragment}
 * @author Omar Suliman
 */
public class HomeRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * A list which will hold all movies that will be displayed in the recycleview
     */
    private List<MovieModel> savedMovies;

    /**
     * An instance of onMovieCardListener to make is possible to click on movies
     */
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
        // Setting values for the components in the recycle view
        ((HomeViewHolder)holder).cardTitle.setText(savedMovies.get(i).getTitle());
        ((HomeViewHolder)holder).cardDescription.setText(savedMovies.get(i).getOverview());
        MovieService ms = MovieService.getMovieService();
        // Setting the rating for the 5 stars ratingBar
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
        return 0;
    }

    private void setPoster(RecyclerView.ViewHolder holder, int i) {
        // Image using glide library
        String moviePosterPath = savedMovies.get(i).getPosterPath();
        // Check if the poster path is null
        if (moviePosterPath!=null){
            Glide.with(holder.itemView.getContext()).load(Credentials.IMG_BASE_URL + moviePosterPath).into(((HomeViewHolder) holder).cardPoster);
        }
    }

    /**
     * Updates the saved movie list when the list is changed
     * @param savedMovies The new updated list
     */
    public void setSavedMovies(List<MovieModel> savedMovies){
        this.savedMovies = savedMovies;
        // Setting categories for all movies
        for (MovieModel m: savedMovies){
            m.setCategory(m.getCategoryFromDetailMov());
        }
        // notifying the recycle view to show the new list
        notifyDataSetChanged();
    }

    /**
     * Returns the movieModel that was selected in the recycleView
     * @param position the position of the movie in the recycle view
     * @return Returns selected movie
     */
    public MovieModel getSelectedMovie(int position){
        if (savedMovies != null){
            if (savedMovies.size() > 0){
                return savedMovies.get(position);
            }
        }
        return null;
    }

}
