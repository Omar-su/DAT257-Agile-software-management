package com.example.eurythmics.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.repositories.MovieRepo;

import java.util.List;

public class RatedMoviesViewModel extends ViewModel {
    private MovieRepo movieRepo;

    public RatedMoviesViewModel() {
        movieRepo = MovieRepo.getInstance();
    }


    public LiveData<List<MovieModel>> getRatedMovies(){ return movieRepo.getRatedMovies(); }

    public void searchRatedMovies(List<Integer> ids){
        movieRepo.searchRatedMovies(ids);
    }


}
