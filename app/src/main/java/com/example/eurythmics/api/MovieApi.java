package com.example.eurythmics.api;

import com.example.eurythmics.api.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

//    https://api.themoviedb.org/3/movie/popular?api_key=0279281a6298491c6675460bbefb7ccf

    @GET("3/movie/{category}")
    Call<MovieSearchResponse> searchMovieByCategory(
            @Path("category") String category,
            @Query("api_key") String key
    );


    // https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovieByName(
            @Query("api_key") String key,
            @Query("query") String query
    );


}
