package com.example.eurythmics.view.fragments.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.model.api.models.MovieModel;

import java.util.List;

/**
 * An adapter for the image slider in the home page which populates the view page with images
 * @author Omar Sulaiman
 */
public class SliderAdapter extends RecyclerView.Adapter<SliderViewHolder>{
    List<MovieModel> modelList;


    public SliderAdapter() {}

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int i) {
        // Sets the images
        String moviePosterPath = modelList.get(i).getPosterPath();
        Glide.with(holder.itemView.getContext()).load(Credentials.IMG_BASE_URL + moviePosterPath).into((holder).imageView);
    }

    @Override
    public int getItemCount() {
        if (modelList!=null){
            return modelList.size();
        }
        return 0;
    }

    public void setMovies(List<MovieModel> mMovies){
        this.modelList = mMovies;
        notifyDataSetChanged();
    }
}
