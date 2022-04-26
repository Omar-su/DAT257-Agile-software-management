package com.example.eurythmics.Movie;

import com.example.eurythmics.Review.Review;

public class Movie {
    private String title;
    private String description;

    public Movie(String title){
        this.title = title;
    }

    public Movie(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle(){return title;}
    public String getDescription(){return description;}

}
