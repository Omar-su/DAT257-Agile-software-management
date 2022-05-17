package com.example.eurythmics.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Review.Review;
import com.example.eurythmics.model.api.models.FilterOption;
import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.model.api.models.SortOptions;
import com.example.eurythmics.model.api.repositories.MovieRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class RatedMoviesViewModel extends ViewModel {
    private MovieRepo movieRepo;
    List<MovieModel> modelList;

    public RatedMoviesViewModel() {
        movieRepo = MovieRepo.getInstance();
        modelList = new ArrayList<>();
    }


    public LiveData<List<MovieModel>> getRatedMovies(){
        return movieRepo.getRatedMovies();
    }

    public void searchRatedMovies(List<Integer> ids){
        movieRepo.searchRatedMovies(ids);
    }


    public void sortBy(SortOptions sortOption) {
        switch (sortOption) {
            case NEWEST_DATE:
                sortByNewestDate();
                break;

            case OLDEST_DATE:
                sortByOldestDate();
                break;

            case HIGHEST_RATING:
                sortByRating();
                break;

            case DURATION_TIME:
                sortByTime();
                break;
        }

    }

    private void sortByTime() {
        Collections.sort(modelList, new Comparator<MovieModel>() {
            @Override
            public int compare(MovieModel m1, MovieModel m2) {

                return Integer.compare(m1.getDuration(), m2.getDuration());
            }
        });
    }

    private void sortByRating() {
        Collections.sort(modelList, new Comparator<MovieModel>() {
            @Override
            public int compare(MovieModel m1, MovieModel m2) {
                MovieService ms = MovieService.getMovieService();
                return Double.compare(ms.getOverallRating(m2.getMovie_id()), ms.getOverallRating(m1.getMovie_id()));
            }
        });
    }

    private void sortByOldestDate() {
        Collections.sort(modelList, new Comparator<MovieModel>() {
            @Override
            public int compare(MovieModel m1, MovieModel m2) {
                if (m1.getDate(m1.getReleaseDate()) == null || m2.getDate(m1.getReleaseDate()) == null){
                    return 0;
                }
                Date date1 = m1.getDate(m1.getReleaseDate());
                Date date2 = m2.getDate(m2.getReleaseDate());
                return date1.compareTo(date2);
            }
        });
    }

    private void sortByNewestDate() {
        Collections.sort(modelList, new Comparator<MovieModel>() {
            @Override
            public int compare(MovieModel m1, MovieModel m2) {
                if (m1.getDate(m1.getReleaseDate()) == null || m2.getDate(m1.getReleaseDate()) == null){
                    return 0;
                }
                Date date1 = m1.getDate(m1.getReleaseDate());
                Date date2 = m2.getDate(m2.getReleaseDate());
                return date2.compareTo(date1);
            }
        });
    }

    public void setFilter(FilterOption action) {
    }

    public void setListMovies(List<MovieModel> movieModels) {
        this.modelList = movieModels;
    }

    public List<MovieModel> getSortedMovies() {
        return modelList;
    }
}
