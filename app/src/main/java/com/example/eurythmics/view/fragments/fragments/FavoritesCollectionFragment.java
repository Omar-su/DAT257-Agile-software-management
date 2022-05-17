package com.example.eurythmics.view.fragments.fragments;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
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
import android.widget.RadioGroup;


import com.example.eurythmics.model.api.models.FilterOption;
import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.models.SortOptions;
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
    private Button filterButton, sortButton;
    private Dialog filterDialog, sortDialog;

    private boolean filterIsActivated = false;

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

        filterButton = view.findViewById(R.id.favorite_filter_button);
        sortButton = view.findViewById(R.id.favorite_sort_button);

        ms = MovieService.getMovieService();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initSearchBar();


        configureRecycleView();

        observeChange();

        initRecycleView();

        initFilterDialog();
        initFilterButton();

        initSortDialog();
        initSortButton();

        return view;
    }

    private void observeChange() {
        viewModel.getRatedMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                for (MovieModel movieModel : movieModels){
                    movieModel.setCategory(movieModel.getCategoryFromDetailMov());
                }
                viewModel.setListMovies(movieModels);
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

    private void initSortButton() {
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialog.show();
            }
        });
    }

    private void initSortDialog() {
        sortDialog = new Dialog(getActivity());
        sortDialog.setContentView(R.layout.sort_dialog);
        RadioGroup sortRadioGroup = sortDialog.findViewById(R.id.sort_radio_group);
        sortRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.default_radio:
                        setDeActivatedColor(sortButton);
                        viewModel.sortBy(SortOptions.DEFAULT);
                        break;
                    case R.id.newest_date_radio:
                        setDeActivatedColor(sortButton);
                        viewModel.sortBy(SortOptions.NEWEST_DATE);
                        break;

                    case R.id.oldest_date_radio:
                        viewModel.sortBy(SortOptions.OLDEST_DATE);
                        setActivatedColor(sortButton);
                        break;

                    case R.id.highestRating_radio:
                        viewModel.sortBy(SortOptions.HIGHEST_RATING);
                        setActivatedColor(sortButton);
                        break;

                    case R.id.durationTime_radio:
                        viewModel.sortBy(SortOptions.DURATION_TIME);
                        setActivatedColor(sortButton);
                        break;
                }
                viewAdapter.setmMovies(viewModel.getFilterList());
                sortDialog.cancel();
            }
        });
    }

    private void initFilterButton() {
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.show();
            }
        });
    }

    private void initFilterDialog() {
        filterDialog = new Dialog(getActivity());
        filterDialog.setContentView(R.layout.filter_dialog);
        RadioGroup filterRadio = filterDialog.findViewById(R.id.filter_radio_group);
        filterRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.default_radio:
                        filterIsActivated = false;
                        setDeActivatedColor(filterButton);
                        viewModel.filter(FilterOption.DEFAULT);
                        break;
                    case R.id.action_radio:
                        filterIsActivated = false;
                        setDeActivatedColor(filterButton);
                        viewModel.filter(FilterOption.ACTION);

                        break;
                    case R.id.adventure_radio:
                        filterIsActivated = true;
                        setActivatedColor(filterButton);
                        viewModel.filter(FilterOption.ADVENTURE);
                        break;

                    case R.id.comedy_radio:
                        filterIsActivated = true;
                        setActivatedColor(filterButton);
                        viewModel.filter(FilterOption.COMEDY);
                        break;
                }
                viewAdapter.setmMovies(viewModel.getFilterList());
                filterDialog.cancel();
            }
        });

    }

    private void setActivatedColor(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.teal_200)));
    }

    private void setDeActivatedColor(Button button) {
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffd6d7d7")));
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