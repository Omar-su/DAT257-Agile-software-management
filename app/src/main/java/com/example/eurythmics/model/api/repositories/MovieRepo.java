package com.example.eurythmics.model.api.repositories;

import androidx.lifecycle.LiveData;

import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.request.MovieApiClient;

import java.util.List;

/**
 * A repository class which connects the api client class with the viewmodels
 * @author omar Sulaiman
 */
public class MovieRepo {

    private static MovieRepo instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    private String filterQ;
    private int categoryPageNumber;

    /**
     * A singleton pattern which only returns one object
     * @return
     */
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

    public LiveData<List<MovieModel>> getMoviesCategory(){
        return movieApiClient.getMoviesCategory();
    }

    public LiveData<List<MovieModel>> getRatedMovies(){
        return movieApiClient.getRatedMovies();
    }

    public void searchRatedMovies(List<Integer> ids){
        movieApiClient.searchRatedMovies(ids);
    }

    public MovieModel searchMovieApiById(int id){

        return movieApiClient.searchMoviesApiById(id);

    }

    public void searchMovieApi(String query, int pageNumber){

        mQuery = query;
        mPageNumber = pageNumber;

        movieApiClient.searchMoviesApi(query, pageNumber);


    }

    public void searchNextPage(){
        searchMovieApi(mQuery,mPageNumber + 1);
    }


    public void searchMovieApiByCategory(String filterQ, int pageNumber) {
        this.filterQ = filterQ;
        this.categoryPageNumber = pageNumber;
        movieApiClient.searchMoviesApiByCategory(filterQ,pageNumber);
    }

    /**
     * Searches for the next page of the api result
     */
    public void searchNextPageCategory() {
        searchMovieApiByCategory(filterQ, categoryPageNumber + 1);
    }
}
