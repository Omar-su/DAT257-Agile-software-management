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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initButtons(view);
        return view;
    }

    private void initButtons(View view){

        movie_collection_button = view.findViewById(R.id.movie_collection_button);
        movie_collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RatedMoviesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }
}