package com.example.eurythmics.model.api.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eurythmics.model.api.AppExecutors;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.model.api.MovieApi;
import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.response.MovieSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A class which handles all api calls
 * @author Omar Sulaiman
 */
public class MovieApiClient {

    private static MovieApiClient instance;


    private MutableLiveData<List<MovieModel>> mMovies;

    private MutableLiveData<List<MovieModel>> moviesCategory;

    private MutableLiveData<List<MovieModel>> ratedMovies;



    //Making a global runnable request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    //Making a global runnable request
    private RetrieveMoviesRunnableCategory retrieveMoviesRunnableCategory;



    public static MovieApiClient getInstance() {
        if (instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        moviesCategory = new MutableLiveData<>();
        ratedMovies = new MutableLiveData<>();
    }

    /**
     * A method which returns a live data list which can be observed
     * @return An immutable list
     */
    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    /**
     * A method which returns a live data list which can be observed
     * @return An immutable list
     */
    public LiveData<List<MovieModel>> getMoviesCategory(){
        return moviesCategory;
    }

    /**
     * A method which returns a live data list which can be observed
     * @return An immutable list
     */
    public LiveData<List<MovieModel>> getRatedMovies(){return ratedMovies;}


    /**
     * Searches for movies
     * @param query The movie that is being searched for
     * @param pageNumber which page
     */
    public void searchMoviesApi(String query, int pageNumber){
        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Canceling the retrofit call
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS );

    }


    /**
     * A method which searches for movies with a specific category
     * @param filterQ What category
     * @param pageNumber Which page
     */
    public void searchMoviesApiByCategory(String filterQ, int pageNumber){
        if (retrieveMoviesRunnableCategory != null){
            retrieveMoviesRunnableCategory = null;
        }

        retrieveMoviesRunnableCategory = new RetrieveMoviesRunnableCategory(filterQ, pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableCategory);

        // Runs parallel
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Canceling the retrofit call
                myHandler2.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS );

    }

    /**
     * Searches for movies to get their details
     * @param ids The ids of the movies
     */
    public void searchRatedMovies(List<Integer> ids){
        List<MovieModel> movieModels = new ArrayList<>();
        for (int id : ids){
            MovieModel movieModel = searchMoviesApiById(id);
            movieModels.add(movieModel);
        }
        ratedMovies.postValue(movieModels);
    }


    /**
     * Searches for a specific movie to get it's detail
     * @param id The id of the movie
     */
    public MovieModel searchMoviesApiById(int id){
        MovieApi movieApi = ServiceApi.getMovieApi();
        try {
            Response<MovieModel> movieModelCall = movieApi.searchMovieDetailById(id, Credentials.API_KEY).execute();
            if (movieModelCall.body() != null) {
                return movieModelCall.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * A class which holds the responsibility of executing the api calls in parallel
     */
    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private boolean cancelRequest;
        private int pageNumber;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.cancelRequest = false;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {

            // getting the response
            try{
                Response<MovieSearchResponse> response2 = getMovies(query,pageNumber).execute();

                if (cancelRequest){
                    return;
                }

                if (response2.code() == 200){
                    assert response2.body() != null;
                    List<MovieModel> movies = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if (pageNumber==1){
                        mMovies.postValue(movies);
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(movies);
                        mMovies.postValue(currentMovies);
                    }
                }else {
                    assert response2.errorBody() != null;
                    String error = response2.errorBody().string();
                    Log.d("TAG","Error" + error);
                    mMovies.postValue(null);
                }


            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }




        }


        // Search method/query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return ServiceApi.getMovieApi().searchMovieByName(Credentials.API_KEY, query, pageNumber);
        }

        private void cancelRequest(){
            Log.d("TAG", "Cancelling request");
            cancelRequest = true;
        }



    }


    /**
     * A class which holds the responsibility of executing the api calls in parallel
     */
    private class RetrieveMoviesRunnableCategory implements Runnable{

        private String query;
        private boolean cancelRequest;
        private int pageNumber;

        public RetrieveMoviesRunnableCategory(String filterQ, int pageNumber) {
            this.query = filterQ;
            this.cancelRequest = false;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {

            // getting the response
            try{
                Response<MovieSearchResponse> response = getMovies(query,pageNumber).execute();

                if (cancelRequest){
                    return;
                }

                if (response.code() == 200){
                    assert response.body() != null;
                    List<MovieModel> movies = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber==1){
                        moviesCategory.postValue(movies);
                    }else {
                        List<MovieModel> currentMovies = moviesCategory.getValue();
                        currentMovies.addAll(movies);
                        moviesCategory.postValue(currentMovies);
                    }
                }else {
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
                    Log.d("TAG","Error" + error);
                    moviesCategory.postValue(null);
                }


            } catch (IOException e) {
                e.printStackTrace();
                moviesCategory.postValue(null);
            }




        }


        // Search method/query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return ServiceApi.getMovieApi().searchMovieByCategory(query, Credentials.API_KEY, pageNumber);
        }

        private void cancelRequest(){
            Log.d("TAG", "Cancelling request");
            cancelRequest = true;
        }



    }



}
