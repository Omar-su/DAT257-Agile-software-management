package com.example.eurythmics.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.eurythmics.Movie.MovieService;
import com.example.eurythmics.R;
import com.example.eurythmics.adapters.HomeRecycleViewAdapter;
import com.example.eurythmics.adapters.OnMovieCardListener;
import com.example.eurythmics.adapters.RatingRecycleViewAdapter;
import com.example.eurythmics.api.models.MovieModel;
import com.example.eurythmics.viewmodels.MovieListViewModel;
import com.example.eurythmics.viewmodels.RatedMoviesViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RatedMoviesCollectionFragment extends Fragment implements OnMovieCardListener {


    // recycle view
    private RecyclerView recyclerView;

    private HomeRecycleViewAdapter viewAdapter;

    private SearchView searchBar;
    private Button filter, sort;

    private RatedMoviesViewModel viewModel;
    MovieService ms;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rated_movies_collection, container, false);

        viewModel = new ViewModelProvider(this).get(RatedMoviesViewModel.class);

        recyclerView = view.findViewById(R.id.rated_movies_recycleview);

        searchBar = view.findViewById(R.id.ratedMovies_searchBar);

        filter = view.findViewById(R.id.movies_filter_button);
        sort = view.findViewById(R.id.series_sort_button);

        ms = MovieService.getMovieService();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initSearchBar();


        configureRecycleView();

        observeChange();

        initRecycleView();


        return view;
    }

    private void observeChange() {
        viewModel.getRatedMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                viewAdapter.setSavedMovies(movieModels);
            }
        });
    }


    private void initRecycleView() {

        List<Integer> ids = ms.getAllMovieIds();

        viewModel.searchRatedMovies(ids);


    }

    private void initSearchBar() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void configureRecycleView() {
        viewAdapter = new HomeRecycleViewAdapter(this);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    public void onMovieClick(int position) {
        MovieModel currentMov = viewAdapter.getSelectedMovie(position);
        currentMov.setCategory(currentMov.getCategoryFromDetailMov());

        Fragment fragment = new RatedMovieDetailView();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ratedMovie", new MovieModel(currentMov));
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
    }
}