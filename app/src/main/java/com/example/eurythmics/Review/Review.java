package com.example.eurythmics.Review;

import com.example.eurythmics.Movie.Movie;

import java.text.DecimalFormat;

public class Review {
    private String title;
    private double overallRating;
    private String thoughts;

    public Review(Movie movie, double overallRating){
        this.title = movie.getTitle();
        setOverallRating(overallRating);
    }

    public void setOverallRating(double rating){
        if(rating <= 10 & rating >= 0){
            overallRating = Math.round(rating*10.0)/10.0;
        }
    }

    public double getOverallRating(){return overallRating;}
    public String getThoughts(){return thoughts;}
    public String getTitle(){return title;}
}
