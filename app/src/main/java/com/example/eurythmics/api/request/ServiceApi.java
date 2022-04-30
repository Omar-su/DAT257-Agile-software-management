package com.example.eurythmics.api.request;

import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceApi {


    // A retrofit builder constructor
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(Credentials.BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }




}
