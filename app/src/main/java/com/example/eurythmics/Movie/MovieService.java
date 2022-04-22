package com.example.eurythmics.Movie;

import com.example.eurythmics.Review.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MovieService {



    public void saveMovieToDB(Movie movie){
        try{
            //TODO: get connection to database
            Connection conn = DriverManager.getConnection("URL GOES HERE");
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO ? VALUES (?,?)");
            //TODO: change to table name in database
            statement.setString(1,"MOVIE TABLE NAME GOES HERE");
            statement.setString(2, movie.getTitle());
            statement.setString(3, movie.getDescription());
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Review getReview(Movie movie){
        if(movie.isReviewed()){
            return movie.getReview();
        }else{
            throw new RuntimeException("That movie has not been reviewed yet");
        }
    }

    public double getOverallRating(Movie movie){
        return getReview(movie).getOverallRating();
    }
}
