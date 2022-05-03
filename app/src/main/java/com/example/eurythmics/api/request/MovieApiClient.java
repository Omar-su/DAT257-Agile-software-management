package com.example.eurythmics.api.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eurythmics.AppExecutors;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.models.MovieModel;
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

    //Making a global runnable request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance() {
        if (instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }



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
                Response<MovieSearchResponse> response = getMovies(query,pageNumber).execute();

                if (cancelRequest){
                    return;
                }

                if (response.code() == 200){
                    assert response.body() != null;
                    List<MovieModel> movies = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if (pageNumber==1){
                        mMovies.postValue(movies);
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(movies);
                        mMovies.postValue(currentMovies);
                    }
                }else {
                    assert response.errorBody() != null;
                    String error = response.errorBody().string();
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



}
