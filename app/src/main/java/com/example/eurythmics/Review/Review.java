package com.example.eurythmics.Review;

import com.example.eurythmics.Movie.Movie;

import java.text.DecimalFormat;

public class Review {
    private Movie movie;
    private double storyRating, charactersRating, scoreRating, sceneryRating, overallRating;
    private String thoughts;

    public Review(Movie movie, double storyRating, double charactersRating,
                  double scoreRating, double sceneryRating,
                  double overallRating, String thoughts){
        this.movie = movie;
        setOverallRating(overallRating);
        setStoryRating(storyRating);
        setCharactersRating(charactersRating);
        setScoreRating(scoreRating);
        setSceneryRating(sceneryRating);
        this.thoughts = thoughts;
    }

    public void setStoryRating(double rating){
        if(rating <= 10 & rating >= 0){
            storyRating = Math.round(rating*10.0)/10.0;
        }
    }

    public void setCharactersRating(double rating){
        if(rating <= 10 & rating >= 0){
            charactersRating = Math.round(rating*10.0)/10.0;
        }
    }

    public void setScoreRating(double rating){
        if(rating <= 10 & rating >= 0){
            scoreRating = Math.round(rating*10.0)/10.0;
        }
    }

    public void setSceneryRating(double rating){
        if(rating <= 10 & rating >= 0){
            sceneryRating = Math.round(rating*10.0)/10.0;
        }
    }

    public void setOverallRating(double rating){
        if(rating <= 10 & rating >= 0){
            overallRating = Math.round(rating*10.0)/10.0;
        }
    }

    public double getStoryRating(){return storyRating;}
    public double getCharactersRating(){return charactersRating;}
    public double getScoreRating(){return scoreRating;}
    public double getSceneryRating(){return sceneryRating;}
    public double getOverallRating(){return overallRating;}
    public String getThoughts(){return thoughts;}
    public Movie getMovie(){return movie;}
}
