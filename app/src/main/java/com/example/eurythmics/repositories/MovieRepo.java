package com.example.eurythmics.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eurythmics.api.models.MovieModel;

import java.util.List;

public class MovieRepo {

    private static MovieRepo instance;

    private MutableLiveData<List<MovieModel>> mMovies = new MutableLiveData<>();

    public static MovieRepo getInstance(){
        if (instance  == null){
            instance = new MovieRepo();
        }
        return instance;
    }

    private MovieRepo(){
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }
}
