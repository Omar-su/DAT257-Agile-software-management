package com.example.eurythmics.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.repositories.MovieRepo;

import java.util.List;

/**
 * A view model which updates the home fragment with updated data when the view model is updated
 * @author Omar Sulaiman
 */
public class HomeListViewModel extends ViewModel {

    private MovieRepo movieRepo;

    public HomeListViewModel() {
        movieRepo = MovieRepo.getInstance();
    }

    /**
     * A method to get upcoming mmovies which will be shown in the image slider
     * @return Returns a list with the first page of upcoming movies
     */
    public LiveData<List<MovieModel>> getUpcomingMovies(){ return movieRepo.getMoviesCategory(); }

    public void searchUpcomingMovie(String filterQ, int pageNumber) {
        movieRepo.searchMovieApiByCategory(filterQ, pageNumber);
    }


}
