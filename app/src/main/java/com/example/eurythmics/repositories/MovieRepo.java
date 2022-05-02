package com.example.eurythmics.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eurythmics.api.models.MovieModel;
import com.example.eurythmics.api.request.MovieApiClient;

import java.util.List;

public class MovieRepo {

    private static MovieRepo instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepo getInstance(){
        if (instance  == null){
            instance = new MovieRepo();
        }
        return instance;
    }

    private MovieRepo(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }


    public void searchMovieApi(String query, int pageNumber){
        movieApiClient.searchMoviesApi(query);

        mQuery = query;
        mPageNumber = mPageNumber;

    }





}
