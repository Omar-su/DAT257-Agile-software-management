package com.example.eurythmics.view.fragments.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.model.api.models.MovieModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An adapter which connects each list item to the recycle view
 * @author Omar Sulaiman
 */
public class RatingRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * A list of movieModels that is used to display the movies
     */
    private List<MovieModel> mMovies;

    /**
     * An instance for onmoviecardlistener
     */
    private OnMovieCardListener onMovieCardListener;

    /**
     * A hashmap with all categories connected to their matching ids from the api.
     * This map helps with finding the category for the movie
     */
    private static final Map<Integer,String> categoryIds = getHashMap();

    @NonNull
    private static HashMap<Integer, String> getHashMap() {
        return new HashMap<Integer, String>() {{
            put(28, "Action");
            put(12, "Adventure");
            put(16, "Animation");
            put(35, "Comedy");
            put(80, "Crime");
            put(99, "Documentary");
            put(18, "Drama");
            put(10751, "Family");
            put(14, "Fantasy");
            put(36, "History");
            put(27, "Horror");
            put(10402, "Music");
            put(9684, "Mystery");
            put(10749, "Romance");
            put(878, "Science Fiction");
            put(10770, "TV Movie");
            put(53, "Thriller");
            put(10752, "War");
            put(37, "Western");

        }};
    }


    public RatingRecycleViewAdapter(OnMovieCardListener onMovieCardListener) {
        this.onMovieCardListener = onMovieCardListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Connecting to the movie cards
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_serie_card_row_item,parent,false);
        // Returning a view holder which connects the components
        return new RatingViewHolder(view, onMovieCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        // Setting the values of the components
        ((RatingViewHolder)holder).title.setText(mMovies.get(i).getTitle());
        ((RatingViewHolder)holder).description.setText(mMovies.get(i).getOverview());


        ((RatingViewHolder)holder).releaseDateTextView.setText(String.valueOf(mMovies.get(i).getReleaseDate()));

        // Setting the category
        String category = "Undefined";
        if (getCategory(i) != null && !getCategory(i).equals("")){
            category = getCategory(i);
            Log.d("ta", "---------------" + category);
        }else {
            category = mMovies.get(i).getCategoryFromDetailMov();
            Log.d("ta2", "---------------" + category);
        }
        mMovies.get(i).setCategory(category);
        ((RatingViewHolder)holder).categoryTextView.setText(category);

        setPoster(holder, i);
    }

    private void setPoster(RecyclerView.ViewHolder holder, int i) {
        // Image using glide library
        String moviePosterPath = mMovies.get(i).getPosterPath();
        if (moviePosterPath!=null){
            Glide.with(holder.itemView.getContext()).load(Credentials.IMG_BASE_URL + moviePosterPath).into(((RatingViewHolder) holder).poster);
        }
    }

    private String getCategory(int i) {
        int genreId = 0;
        String category = "";

        while ((category == null || category.isEmpty())&& mMovies.get(i).getGenre_ids() != null){
            if (genreId >= mMovies.get(i).getGenre_ids().size())break;
            Integer categoryId = (mMovies.get(i).getGenre_ids().get(genreId));

            category = categoryIds.get(categoryId);
            genreId ++;

        }

        mMovies.get(i).setCategory(category);


        return category;
    }

    @Override
    public int getItemCount() {
        if (mMovies!=null){
            return mMovies.size();
        }
        return 0;
    }

    /**
     * Updates the list of movie models that will displayed on the recycle view
     * @param mMovies The new updated list
     */
    public void setmMovies(List<MovieModel> mMovies){
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    /**
     * Returns the selected movie model on the recycle view
     * @param position The position of the card
     * @return Returns the selected movie
     */
    public MovieModel getSelectedMovie(int position){
        if (mMovies != null){
            if (mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }


}
