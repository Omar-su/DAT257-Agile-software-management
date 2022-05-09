package com.example.eurythmics.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.eurythmics.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ImageButton movie_collection_button;
    private ImageButton series_collection_button;
    private ImageButton favorites_collection_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init_movie_collection_button(view);
        init_series_collection_button(view);
        init_favorites_collection_button(view);

        return view;
    }

    private void init_movie_collection_button(View view){

        movie_collection_button = view.findViewById(R.id.movie_collection_button);
        movie_collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RatedMoviesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }

    private void init_series_collection_button(View view){
        series_collection_button = view.findViewById(R.id.series_collection_button);
        series_collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RatedSeriesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }

    private void init_favorites_collection_button(View view){
        favorites_collection_button = view.findViewById(R.id.favorites_collection_button);
        favorites_collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FavoritesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }


}