package com.example.eurythmics.Movie;

import android.database.Cursor;

import com.example.eurythmics.Review.Review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DataBaseManager;

public class MovieService {

    private DataBaseManager dataBaseManager;
    private static MovieService instance;

    private List<Review> reviewList = new ArrayList<>();
    private List<Integer> favoritesList = new ArrayList<>();


    public MovieService(DataBaseManager dataBaseManager){
        if(instance == null){
            this.dataBaseManager = dataBaseManager;
            instance = this;
            loadReviewsFromDB();
            loadFavoritesFromDB();
        }
    }

    public static MovieService getMovieService(){
        if(instance == null){
            throw new RuntimeException("Movie service not initialized");
        }else{
            return instance;
        }
    }

    public void loadReviewsFromDB(){
        Cursor cursor = dataBaseManager.getAllReviews();
        if(cursor.moveToFirst()){
            do{
                reviewList.add(new Review(
                        cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getDouble(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4),
                        cursor.getDouble(5),
                        cursor.getString(6)
                ));
            }while (cursor.moveToNext());
        }
    }

    public void loadFavoritesFromDB(){
        Cursor cursor = dataBaseManager.getAllFavorites();
        if(cursor.moveToFirst()){
            do{
                favoritesList.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
    }


    public Review getReview(int movieID){
        for(Review review : reviewList){
            if(review.getMovieID() == movieID){
                return review;
            }
        }
        return null;
    }

    public boolean isReviewed(int movieID){
        for(Review review : reviewList){
            if(review.getMovieID() == movieID){
                return true;
            }
        }
        return false;
    }

    public double getOverallRating(int movieID){
        return getReview(movieID).getOverallRating();
    }

    public void addReview(Review review){

        if(!isReviewed(review.getMovieID())){
            reviewList.add(review);
            dataBaseManager.addReview(
                    review.getMovieID(),
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
                    review.getMovieID(),
                    review.getStoryRating(),
                    review.getCharactersRating(),
                    review.getScoreRating(),
                    review.getSceneryRating(),
                    review.getOverallRating(),
                    review.getThoughts()
            );
        }
    }

    public void addReview(int movieID, double storyRating, double charactersRating,
                          double scoreRating, double sceneryRating,
                          double overallRating, String thoughts){
        addReview(new Review(movieID, storyRating, charactersRating,
                scoreRating, sceneryRating, overallRating, thoughts));
    }

    public void removeReview(Review review){
        if (isReviewed(review.getMovieID())) {
            reviewList.remove(review);
            dataBaseManager.deleteReview(review);
        }
    }

    public List<Review> getAllReviews(){
        return reviewList;
    }

    public Map<Integer, Double> getOverallRatings(){
        HashMap<Integer, Double> overallRatings = new HashMap<>();
        for(Review review : reviewList){
            overallRatings.put(review.getMovieID(), review.getOverallRating());
        }
        return overallRatings;
    }

    public void addFavorite(int movieID){
        favoritesList.add(movieID);
        dataBaseManager.setFavorite(movieID);
    }

    public double getAverageOverallRating(){
        double total = 0;
        for(Review review : reviewList){
            total += review.getOverallRating();
        }
        return total / (double) reviewList.size();
    }

    public List<Integer> getTop5Rated(){
        Collections.sort(reviewList, (r1, r2) -> Double.compare(r1.getOverallRating(), r2.getOverallRating()));
        List<Integer> top5 = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            if(i >= reviewList.size()){
                break;
            }else{
                top5.add(reviewList.get(i).getMovieID());
            }
        }
        return top5;
    }


}
