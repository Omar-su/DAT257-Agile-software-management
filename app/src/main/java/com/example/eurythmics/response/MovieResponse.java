package com.example.eurythmics.response;

import com.example.eurythmics.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is for single movie request
 */
public class MovieResponse {

    @SerializedName("result") @Expose() private Movie movie;

    public Movie getMovie(){
        return movie;
    }


    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
