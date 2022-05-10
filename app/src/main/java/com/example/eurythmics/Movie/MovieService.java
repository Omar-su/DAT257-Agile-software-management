package com.example.eurythmics.Movie;

import android.database.Cursor;

import com.example.eurythmics.Review.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import database.DataBaseManager;

public class MovieService {

    private DataBaseManager dataBaseManager;
    private static MovieService instance;

    private List<Movie> movieList = new ArrayList<>();
    private List<Review> reviewList = new ArrayList<>();

    public MovieService(DataBaseManager dataBaseManager){
        if(instance == null){
            this.dataBaseManager = dataBaseManager;
            instance = this;
        }

    }

    public static MovieService getMovieService(){
        if(instance == null){
            throw new RuntimeException("Movie service not initialized");
        }else{
            return instance;
        }
    }

    private void loadMoviesFromDB(){
        Cursor cursor = dataBaseManager.getAllMovies();
        movieList.clear();
        if(cursor.moveToFirst()){
            do{
                movieList.add(new Movie(
                        cursor.getString(0),
                        cursor.getString(1)
                ));
            }while (cursor.moveToNext());
        }
    }

    public Movie getMovie(String title){
        for(Movie movie : movieList){
            if(movie.getTitle() == title){
                return movie;
            }
        }
        Movie newMovie = new Movie(title, "");
        addMovie(newMovie);
        return newMovie;
    }



    public Review getReview(Movie movie){
        for(Review review : reviewList){
            if(review.getMovie().getTitle().equals(movie.getTitle())){
                return review;
            }
        }
        throw new NoSuchElementException("No such review found");
    }

    public boolean isReviewed(Movie movie){
        for(Review review : reviewList){
            if(review.getMovie().getTitle().equals(movie.getTitle())){
                return true;
            }
        }
        return false;
    }

    public double getOverallRating(Movie movie){
        return getReview(movie).getOverallRating();
    }

    public void addMovie(Movie movie){
        movieList.add(movie);
        dataBaseManager.addMovie(movie.getTitle(), movie.getDescription());
    }

    public void addMovie(String title, String description){
        addMovie(new Movie(title, description));
    }

    public void addReview(Review review){

        if(!isReviewed(review.getMovie())){
            reviewList.add(review);
            dataBaseManager.addReview(
                    review.getMovie().getTitle(),
                    review.getStoryRating(),
                    review.getCharactersRating(),
                    review.getScoreRating(),
                    review.getSceneryRating(),
                    review.getOverallRating(),
                    review.getThoughts()
            );
        }
        else {
            dataBaseManager.editReview(
                    review.getMovie().getTitle(),
                    review.getStoryRating(),
                    review.getCharactersRating(),
                    review.getScoreRating(),
                    review.getSceneryRating(),
                    review.getOverallRating(),
                    review.getThoughts()
            );
        }
    }

    public void addReview(String title, double storyRating, double charactersRating,
                          double scoreRating, double sceneryRating,
                          double overallRating, String thoughts){
        addReview(new Review(getMovie(title), storyRating, charactersRating,
                scoreRating, sceneryRating, overallRating, thoughts));
    }

    public void removeReview(Review review){
        if (isReviewed(review.getMovie())) {
            reviewList.remove(review);
            dataBaseManager.deleteReview(review);
        }
    }

    public List<Review> getAllReviews(){
        return reviewList;
    }

    public Map<String, Double> getOverallRatings(){
        HashMap<String, Double> overallRatings = new HashMap<>();
        for(Review review : reviewList){
            overallRatings.put(review.getMovie().getTitle(), review.getOverallRating());
        }
        return overallRatings;
    }
}
