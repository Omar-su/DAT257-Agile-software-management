package com.example.eurythmics.model.api.models;

import android.database.Cursor;

import com.example.eurythmics.model.api.Review.Review;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.eurythmics.model.api.database.DataBaseManager;

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
            favoritesList = getFavoritesFromDB();
        }else{
            throw new RuntimeException("Should not create singleton");
        }
    }

    public static MovieService getMovieService(){
        if(instance == null){
            throw new RuntimeException("Movie service not initialized");
        }else{
            return instance;
        }
    }

    private void loadReviewsFromDB(){
        System.out.println("Loaded the com.example.eurythmics.model.api.database");
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

    public List<Integer> getFavoritesFromDB(){
        Cursor cursor = dataBaseManager.getAllFavorites();
        List<Integer> favoriteIDs = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                favoriteIDs.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        return favoriteIDs;
    }

    public boolean isFavorite(int movieId){
        List<Integer> ids = getFavoritesFromDB();
        for (int id: ids){
            if (id == movieId){
                return true;
            }
        }
        return false;
    }


    public Review getReview(int movieID){
        for(Review review : reviewList){
            if(review.getMovieID() == movieID){
                return review;
            }
        }
        return null;
    }

    public List<Integer> getAllMovieIds(){
        List<Integer> ids = new ArrayList<>();
        Cursor cursor = dataBaseManager.getAllReviews();
        if(cursor.moveToFirst()){
            do{
                ids.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }
        return ids;
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

        if (getReview(movieID) != null){
            return getReview(movieID).getOverallRating();
        }else {
            return 0;
        }
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
        }else {
            dataBaseManager.editReview(
                    review.getMovieID(),
                    review.getStoryRating(),
                    review.getCharactersRating(),
                    review.getScoreRating(),
                    review.getSceneryRating(),
                    review.getOverallRating(),
                    review.getThoughts()
            );
            Review oldReview = getReview(review.getMovieID());
            reviewList.remove(oldReview);
            reviewList.add(review);
        }

        //TODO: remove, this is only for sanity checks
        System.out.println("Printing review list:");
        for(Review r : reviewList){
            System.out.println(r);
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

    public void removeReview(int movieID){
        Review review = getReview(movieID);
        if(review != null){
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

    public void deleteFavoriteMovie(int movieId){
        dataBaseManager.deleteFavoriteMovie(movieId);
        favoritesList = getFavoritesFromDB();
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
