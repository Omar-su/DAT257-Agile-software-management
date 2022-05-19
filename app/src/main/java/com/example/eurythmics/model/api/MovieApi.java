package com.example.eurythmics.model.api;

import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The interface which is connected to the api and also can get the data from the api
 * @author Omar Sulaiman
 */
public interface MovieApi {

//    https://api.themoviedb.org/3/movie/popular?api_key=0279281a6298491c6675460bbefb7ccf

    /**
     * Calls the api to get the movies of a category
     * @param category the category
     * @param key The api key
     * @param pageNumber The number of the page
     * @return Returns the movies which match the category
     */
    @GET("3/movie/{category}?")
    Call<MovieSearchResponse> searchMovieByCategory(
            @Path("category") String category,
            @Query("api_key") String key,
            @Query("page") int pageNumber
    );


    // https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher&page=1

    /**
     * Calls the api to search for a movie by a name
     * @param key Api key
     * @param query The name of the movie
     * @param pageNumber The number of the result page
     * @return A list of match movies with similar names
     */
    @GET("3/search/movie?")
    Call<MovieSearchResponse> searchMovieByName(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int pageNumber
    );

    // https://api.themoviedb.org/3/movie/414906?api_key=0279281a6298491c6675460bbefb7ccf

    /**
     * Search for a movie by an id
     * @param id The movies id
     * @param key The api key
     * @return Returns the specific movie if found
     */
    @GET("3/movie/{id}?")
    Call<MovieModel> searchMovieDetailById(
            @Path("id") int id,
            @Query("api_key") String key
    );


}
