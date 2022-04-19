package com.example.eurythmics.Movie;

import com.example.eurythmics.Review.Review;

public class Movie {
    private String title;
    private String description;
    private Review review;

    public Movie(String title){
        this.title = title;
    }

    public boolean isReviewed(){
        if(review == null){
            return false;
        }else{
            return true;
        }
    }

    public void setReview(Review review){
        this.review = review;
    }

    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public Review getReview(){return review;}

}
