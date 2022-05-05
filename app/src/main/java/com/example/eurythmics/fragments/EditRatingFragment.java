package com.example.eurythmics.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eurythmics.R;
import com.example.eurythmics.api.models.MovieModel;

import java.util.MissingResourceException;

public class EditRatingFragment extends Fragment {


    MovieModel chosenMovie;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_rating,container,false);

        Bundle bundle = this.getArguments();

        if (bundle != null){
            chosenMovie = bundle.getParcelable("movie_rating");
            Log.d("TAG", chosenMovie.getTitle());

        } else {
            throw new MissingResourceException("No chosen transaction was sent with the fragment, hence fragment cannot be created", MovieModel.class.toString(), "CHOSEN_TRANSACTION" );
        }




        return view;
    }
}
