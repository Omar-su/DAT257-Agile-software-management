package com.example.eurythmics.api.response;

import com.example.eurythmics.api.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is for single movie request
 */
public class MovieResponse {

    @SerializedName("results") @Expose() private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }


    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
