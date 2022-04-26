package com.example.eurythmics.api.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eurythmics.api.models.MovieModel;

import java.util.List;

public class MovieApiClient {

    private static MovieApiClient instance;


    private MutableLiveData<List<MovieModel>> mMovies;

    public static MovieApiClient getInstance() {
        if (instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }



}
