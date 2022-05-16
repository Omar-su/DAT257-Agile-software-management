package com.example.eurythmics.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.repositories.MovieRepo;

import java.util.List;

public class HomeListViewModel extends ViewModel {

    private MovieRepo movieRepo;

    public HomeListViewModel() {
        movieRepo = MovieRepo.getInstance();
    }

    public LiveData<List<MovieModel>> getUpcomingMovies(){ return movieRepo.getMoviesCategory(); }

    public void searchUpcomingMovie(String filterQ, int pageNumber) {
        movieRepo.searchMovieApiByCategory(filterQ, pageNumber);
    }


}
