package com.example.eurythmics.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eurythmics.api.models.MovieModel;
import com.example.eurythmics.repositories.MovieRepo;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepo movieRepo;

    public MovieListViewModel() {
        movieRepo = MovieRepo.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){ return movieRepo.getMovies(); }

    public LiveData<List<MovieModel>> getMoviesByCategory(){ return movieRepo.getMoviesCategory(); }

    /**
     *
     * @param query
     * @param pageNumber
     */
    public void searchMovieApi(String query, int pageNumber){
        movieRepo.searchMovieApi(query, pageNumber);
    }

    public MovieModel searchMovieApiById(int id){
        return movieRepo.searchMovieApiById(id);
    }
    public void searchNextPage(){
        movieRepo.searchNextPage();
    }


    public void searchMovieApiByCategory(String filterQ, int pageNumber) {
        movieRepo.searchMovieApiByCategory(filterQ, pageNumber);
    }

    public void searchNextPageCategory() {
        movieRepo.searchNextPageCategory();
    }
}
