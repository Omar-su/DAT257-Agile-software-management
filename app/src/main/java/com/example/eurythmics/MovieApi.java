package com.example.eurythmics;

import com.example.eurythmics.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    // https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );


}
