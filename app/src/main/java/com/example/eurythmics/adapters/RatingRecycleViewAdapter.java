package com.example.eurythmics.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.models.MovieModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<MovieModel> mMovies;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_serie_card_row_item,parent,false);
        return new RatingViewHolder(view, onMovieCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        ((RatingViewHolder)holder).title.setText(mMovies.get(i).getTitle());
        ((RatingViewHolder)holder).description.setText(mMovies.get(i).getOverview());


        ((RatingViewHolder)holder).releaseDateTextView.setText(String.valueOf(mMovies.get(i).getReleaseDate()));

        setCategory((RatingViewHolder) holder, i);


        setPoster(holder, i);


    }

    private void setPoster(RecyclerView.ViewHolder holder, int i) {
        // Image using glide library
        String moviePosterPath = mMovies.get(i).getPosterPath();
        if (moviePosterPath!=null){
            Glide.with(holder.itemView.getContext()).load(Credentials.IMG_BASE_URL + moviePosterPath).into(((RatingViewHolder) holder).poster);
        }
    }

    private void setCategory(RatingViewHolder holder, int i) {
        int genreId = 0;
        String category = "";

        while (category == null || category.isEmpty()){
            if (genreId >= mMovies.get(i).getGenre_ids().size())break;
            Integer categoryId = (mMovies.get(i).getGenre_ids().get(genreId));

            category = categoryIds.get(categoryId);
            genreId ++;

        }

        holder.categoryTextView.setText(category);
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
