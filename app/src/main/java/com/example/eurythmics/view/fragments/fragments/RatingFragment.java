package com.example.eurythmics.view.fragments.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.R;
import com.example.eurythmics.view.fragments.adapters.OnMovieCardListener;
import com.example.eurythmics.view.fragments.adapters.RatingRecycleViewAdapter;
import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.viewmodels.MovieListViewModel;

import java.util.List;


public class RatingFragment extends Fragment implements OnMovieCardListener {


    // Search view
    private SearchView searchBar;



    // recycle view
    private RecyclerView recyclerView;

    private RatingRecycleViewAdapter ratingViewAdapter;

    private MovieListViewModel movieListViewModel;

    private boolean isCategorySearch = true;

    private MovieModel chosenMovie;

    private MovieService ms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        searchBar = view.findViewById(R.id.action_search);

        recyclerView = view.findViewById(R.id.profile_recycle_view);
        ms = MovieService.getMovieService();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initSearchBar();


        configureRecycleView();
        observerAnyChange();
        observerAnyChangeCat();

        // Show popular movies first page
        searchMovieApiByCategory("popular",1);



        return view;
    }



    private void initSearchBar() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                isCategorySearch = false;
                searchMovieApi(s,1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                isCategorySearch = false;
                searchMovieApi(s,1);
                return false;
            }
        });
    }



    private void observerAnyChange() {
        movieListViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observing for any data change
                if (movieModels!=null){
                    ratingViewAdapter.setmMovies(movieModels);

                }

            }
        });
    }

    private void observerAnyChangeCat() {
        movieListViewModel.getMoviesByCategory().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observing for any data change
                if (movieModels!=null){
                    ratingViewAdapter.setmMovies(movieModels);

                }

            }
        });
    }


    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query, pageNumber);
    }

    private void searchMovieApiByCategory(String filterQ, int pageNumber){
        movieListViewModel.searchMovieApiByCategory(filterQ, pageNumber);
    }

    private MovieModel searchMovieApiByID(int id){
        return movieListViewModel.searchMovieApiById(id);
    }

    // Initializing recycle view and adding list items
    private void configureRecycleView(){
        // Live data can not be passed to a constructor
        ratingViewAdapter = new RatingRecycleViewAdapter(this);
        recyclerView.setAdapter(ratingViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Recycle view pagination
        // Loading next page of api results
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    // Here we need to display the rest of pages from the api
                    if (isCategorySearch){
                        movieListViewModel.searchNextPageCategory();
                    }else {
                        movieListViewModel.searchNextPage();
                    }
                }
            }
        });
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


    @Override
    public void onMovieClick(int position) {
        MovieModel currentMov = ratingViewAdapter.getSelectedMovie(position);
        int id = currentMov.getMovie_id();


        chosenMovie = searchMovieApiByID(id);

        if (chosenMovie == null) {
            makeToast("The movie was not found by the api, Please try again later");
            return;
        }
        currentMov.setDuration(chosenMovie.getDuration());

        makeToast(String.valueOf(currentMov.getDuration()));

        Bundle bundle = new Bundle();
        Fragment fragment;
        String s = "";
        if (ms.isReviewed(currentMov.getMovie_id())){
            fragment = new RatedMovieDetailView();
            s = "ratedMovie";
        }else {
            fragment = new MovieDetailFragment();
            s = "CHOSEN_TRANSACTION";
        }

        bundle.putParcelable(s, new MovieModel(currentMov));
        bundle.putString("fromWhichFragment", "ratingView");
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();

    }
}