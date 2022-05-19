package com.example.eurythmics.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.repositories.MovieRepo;

import java.util.List;
/**
 * A view model which updates the rating fragment with updated movies searches or categories when the view model is updated
 * @author Omar Sulaiman
 */
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

    /**
     * Searches for a movie by an id
     * @param id The movie id
     * @return Returns a movieModel object is found
     */
    public MovieModel searchMovieApiById(int id){
        return movieRepo.searchMovieApiById(id);
    }

    /**
     * Searches next page of the search result
     */
    public void searchNextPage(){
        movieRepo.searchNextPage();
    }

    /**
     * Searches for movies with a specific category
     * @param filterQ The category
     * @param pageNumber The page number
     */
    public void searchMovieApiByCategory(String filterQ, int pageNumber) {
        movieRepo.searchMovieApiByCategory(filterQ, pageNumber);
    }

    /**
     * Searches next page of the search result
     */
    public void searchNextPageCategory() {
        movieRepo.searchNextPageCategory();
    }
}
