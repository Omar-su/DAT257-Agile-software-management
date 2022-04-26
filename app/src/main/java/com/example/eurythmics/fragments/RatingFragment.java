package com.example.eurythmics.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eurythmics.R;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.MovieApi;
import com.example.eurythmics.api.models.MovieModel;
import com.example.eurythmics.api.request.ServiceApi;
import com.example.eurythmics.api.response.MovieSearchResponse;
import com.example.eurythmics.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RatingFragment extends Fragment {


    // Search view
    private SearchView searchBar;

    //next button
    private Button nextButton;

    private MovieListViewModel movieListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        searchBar = view.findViewById(R.id.action_search);
        nextButton = view.findViewById(R.id.next_but);



        initSearchBar();
        initNextButton();

        observerAnyChange();
        return view;
    }

    private void initNextButton() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, new EditRatingFragment()).commit();

            }
        });
    }

    private void initSearchBar() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchMovieApi("batman");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //getRetrofitResponse(s);
                return false;
            }
        });
    }

    private void getRetrofitResponse(String searchString) {

        MovieApi movieApi = ServiceApi.getMovieApi();

        Call<MovieSearchResponse> movieCategory = movieApi.searchMovieByName( Credentials.API_KEY, searchString);

        movieCategory.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200){

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movieModel: movies){
                        Log.d("Tag", "successful  =============" + movieModel.getTitle());
                    }

                }else {
                    try {
                        makeToast(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                makeToast("failed attempt to search, try again");
            }
        });




    }

    private void observerAnyChange() {
        movieListViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observing for any data change
                if (movieModels!=null){
                    for (MovieModel movieModel: movieModels){
                        Log.d("TAG", "onchanged" + movieModel.getTitle());
                    }
                }

            }
        });
    }




    private void searchMovieApi(String query){
        movieListViewModel.searchMovieApi(query);
    }



    //Method to make a Toast. Use to test
    Toast t;
    private void makeToast(String s){
        if(t != null) {
            t.cancel();
        }
        t = Toast.makeText(requireActivity().getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }
}