package com.example.eurythmics.api.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eurythmics.AppExecutors;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.MovieApi;
import com.example.eurythmics.api.models.MovieModel;
import com.example.eurythmics.api.response.MovieResponse;
import com.example.eurythmics.api.response.MovieSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

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

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesCategory(){
        return moviesCategory;
    }

    public LiveData<List<MovieModel>> getRatedMovies(){return ratedMovies;}



    // Method that will be called in other classes
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


    // Method that will be called in other classes
    public void searchMoviesApiByCategory(String filterQ, int pageNumber){
        if (retrieveMoviesRunnableCategory != null){
            retrieveMoviesRunnableCategory = null;
        }

        retrieveMoviesRunnableCategory = new RetrieveMoviesRunnableCategory(filterQ, pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnableCategory);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Canceling the retrofit call
                myHandler2.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS );

    }

    public void searchRatedMovies(List<Integer> ids){
        List<MovieModel> movieModels = new ArrayList<>();
        for (int id : ids){
            MovieModel movieModel = searchMoviesApiById(id);
            movieModels.add(movieModel);
        }
        ratedMovies.postValue(movieModels);
    }


    // Method that will be called in other classes
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



    // retrieving data from rest api by runnable class
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

    // retrieving data from rest api by runnable class
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
