package com.example.eurythmics.model.api.response;

import com.example.eurythmics.model.api.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is the object that will hold the single movie json object
 * @author Omar Sulaiman
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
