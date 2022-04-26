package com.example.eurythmics.Review;

import com.example.eurythmics.Movie.Movie;

import java.text.DecimalFormat;

public class Review {
    private Movie movie;
    private double overallRating;
    private String thoughts;

    public Review(Movie movie, double overallRating, String thoughts){
        this.movie = movie;
        setOverallRating(overallRating);
        this.thoughts = thoughts;
    }

    public void setOverallRating(double rating){
        if(rating <= 10 & rating >= 0){
            overallRating = Math.round(rating*10.0)/10.0;
        }
    }

    public double getOverallRating(){return overallRating;}
    public String getThoughts(){return thoughts;}
    public Movie getMovie(){return movie;}
}
