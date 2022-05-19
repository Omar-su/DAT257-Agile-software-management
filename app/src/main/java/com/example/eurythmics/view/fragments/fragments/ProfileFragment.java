package com.example.eurythmics.view.fragments.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.view.fragments.adapters.HomeRecycleViewAdapter;
import com.example.eurythmics.view.fragments.adapters.OnMovieCardListener;
import com.example.eurythmics.view.fragments.adapters.RatingRecycleViewAdapter;
import com.example.eurythmics.viewmodels.HomeListViewModel;
import com.example.eurythmics.viewmodels.RatedMoviesViewModel;

import java.util.List;


/**
 * This class represents the fragment for the user profile.
 * The profile consists of a profile picture, a username,
 * an average rating, a settings button and a description.
 * None of these features are working, there are merely a mockup.
 * The profile fragment also consists of a working list of 5 top rated movies.
 * The list can be between 0-5 items long.
 * @author Desirée Staaf, Eugene Dvoryankov, Ida Nordlund, Jenny Carlsson
 * @author Omar Suliman
 */
public class ProfileFragment extends Fragment implements OnMovieCardListener {
    private ImageView profilePicture;

    private RecyclerView recyclerView;
    private HomeRecycleViewAdapter viewAdapter;
    private RatedMoviesViewModel viewModel;
    private MovieService ms;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profile_picture);

        viewModel = new ViewModelProvider(this).get(RatedMoviesViewModel.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Glide.with(this).load("@drawable/rectangle_3");

        recyclerView = view.findViewById(R.id.profile_recycle_view);

        ms = MovieService.getMovieService();


        configureRecycleView();

        observeChange();

        initRecycleView();


        return view;
    }

    private void initRecycleView() {
        List<Integer> ids = ms.getTop5Rated();

        viewModel.searchRatedMovies(ids);
    }

    private void configureRecycleView() {
        viewAdapter = new HomeRecycleViewAdapter(this);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observeChange() {
        viewModel.getRatedMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                viewAdapter.setSavedMovies(movieModels);
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        MovieModel currentMov = viewAdapter.getSelectedMovie(position);
        currentMov.setCategory(currentMov.getCategoryFromDetailMov());

        Fragment fragment = new RatedMovieDetailView();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ratedMovie", new MovieModel(currentMov));
        bundle.putString("fromWhichFragment", "profile");
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
    }
}