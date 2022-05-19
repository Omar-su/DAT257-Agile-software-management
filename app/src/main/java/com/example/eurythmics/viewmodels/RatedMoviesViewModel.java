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
import java.util.Locale;
/**
 * A view model which is reponsible to provide all rated movies to the ratedmoviescollection,
 * profile and favorite collection
 * @author Omar Sulaiman
 */
public class RatedMoviesViewModel extends ViewModel {

    private MovieRepo movieRepo;
    private List<MovieModel> modelList;
    private List<MovieModel> filterList;

    public RatedMoviesViewModel() {
        movieRepo = MovieRepo.getInstance();
        modelList = new ArrayList<>();
    }


    public LiveData<List<MovieModel>> getRatedMovies(){
        return movieRepo.getRatedMovies();
    }

    /**
     * Searches for all rated movies in the api by calling the movierepo
     * @param ids The ids of all rated movies
     */
    public void searchRatedMovies(List<Integer> ids){
        movieRepo.searchRatedMovies(ids);
    }


    /**
     * A method which  handles how to sort movies
     * @param sortOption A sort option
     */
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

    /**
     * Sorts the filterlist by the movies duration time
     */
    private void sortByTime() {
        Collections.sort(filterList, (m1, m2) -> Integer.compare(m2.getDuration(), m1.getDuration()));
    }

    /**
     * Sorts the filterlist by the movies rating
     */
    private void sortByRating() {
        Collections.sort(filterList, (m1, m2) -> {
            MovieService ms = MovieService.getMovieService();
            return Double.compare(ms.getOverallRating(m2.getMovie_id()), ms.getOverallRating(m1.getMovie_id()));
        });
    }

    /**
     * Sorts the filterlist by the movies the oldest date
     */
    private void sortByOldestDate() {
        Collections.sort(filterList, new Comparator<MovieModel>() {
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

    /**
     * Sorts the filterlist by the movies newest date
     */
    private void sortByNewestDate() {
        filterList.sort(new Comparator<MovieModel>() {
            @Override
            public int compare(MovieModel m1, MovieModel m2) {
                if (m1.getDate(m1.getReleaseDate()) == null || m2.getDate(m1.getReleaseDate()) == null) {
                    return 0;
                }
                Date date1 = m1.getDate(m1.getReleaseDate());
                Date date2 = m2.getDate(m2.getReleaseDate());
                return date2.compareTo(date1);
            }
        });
    }

    /**
     * Handles all filter operations for the movies
     * @param filterOption Filtes option
     */
    public void filter(FilterOption filterOption) {
        switch (filterOption) {
            case DEFAULT: filterList = modelList; break;
            case ACTION:
                filterCat("action");
                break;

            case ADVENTURE:
                filterCat("adventure");
                break;

            case COMEDY:
                filterCat("comedy");
                break;
        }
    }

    /**
     * Filters the filterlist by the movies category
     */
    private void filterCat(String cat) {
        filterList = new ArrayList<>();
        for (MovieModel m: modelList){
            if (m.getCategory().toLowerCase(Locale.ROOT).equals(cat)){
                filterList.add(m);
            }
        }
    }

    public void setListMovies(List<MovieModel> movieModels) {
        this.modelList = movieModels;
        this.filterList = movieModels;
    }


    public List<MovieModel> getFilterList() {
        return filterList;
    }

    /**
     * Reorders the list based on how matching the search string is to the movies names
     */
    public void searchTextChange(String s) {
        Collections.sort(filterList, new Comparator<MovieModel>() {
            @Override
            public int compare(MovieModel m1, MovieModel m2) {
                String prefix = s.toLowerCase();
                String a = m1.getTitle().toLowerCase();
                String b = m2.getTitle().toLowerCase();
                if (a.contains(prefix) && b.contains(prefix)) return a.compareTo(b);
                if (a.contains(prefix) && !b.contains(prefix)) return -1;
                if (!a.contains(prefix) && b.contains(prefix)) return 1;
                return 0;
            }
        });
    }
}
