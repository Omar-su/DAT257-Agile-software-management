package com.example.eurythmics.viewmodels;

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

    public void searchMovieApi(String query){
        movieRepo.searchMovieApi(query);
    }


}
