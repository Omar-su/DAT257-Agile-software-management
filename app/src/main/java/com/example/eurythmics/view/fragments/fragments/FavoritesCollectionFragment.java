package com.example.eurythmics.view.fragments.fragments;

import android.os.Bundle;

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
import android.widget.Button;


import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.R;
import com.example.eurythmics.view.fragments.adapters.OnMovieCardListener;
import com.example.eurythmics.view.fragments.adapters.RatingRecycleViewAdapter;
import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.viewmodels.RatedMoviesViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FavoritesCollectionFragment extends Fragment implements OnMovieCardListener {


    // recycle view
    private RecyclerView recyclerView;

    private RatingRecycleViewAdapter viewAdapter;

    private SearchView searchBar;
    private Button filter, sort;

    private RatedMoviesViewModel viewModel;
    MovieService ms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites_collection, container, false);

        viewModel = new ViewModelProvider(this).get(RatedMoviesViewModel.class);

        recyclerView =  view.findViewById(R.id.favorite_recycleView);

        searchBar = view.findViewById(R.id.favorite_searchBar);

        filter = view.findViewById(R.id.favorite_filter_button);
        sort = view.findViewById(R.id.favorite_sort_button);

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
                for (MovieModel movieModel : movieModels){
                    movieModel.setCategory(movieModel.getCategoryFromDetailMov());
                }
                viewAdapter.setmMovies(movieModels);
            }
        });
    }


    private void initRecycleView() {

        List<Integer> ids = ms.getFavoritesFromDB();

        viewModel.searchRatedMovies(ids);


    }

    private void initSearchBar() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void configureRecycleView() {
        viewAdapter = new RatingRecycleViewAdapter(this);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    public void onMovieClick(int position) {
        MovieModel currentMov = viewAdapter.getSelectedMovie(position);
        currentMov.setCategory(currentMov.getCategoryFromDetailMov());

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
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
    }
}