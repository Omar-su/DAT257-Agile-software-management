package com.example.eurythmics.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.models.MovieModel;

import java.util.MissingResourceException;

public class MovieDetailFragment extends Fragment {

    private ImageView poster;

    private TextView title, category, releaseDate;

    private MovieModel chosenMovie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_detail_view, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null){
            MovieModel movieModel = bundle.getParcelable("CHOSEN_TRANSACTION");
            chosenMovie = movieModel.movieModel;
        } else {
            throw new MissingResourceException("No chosen transaction was sent with the fragment, hence fragment cannot be created", MovieModel.class.toString(), "CHOSEN_TRANSACTION" );
        }


        title = view.findViewById(R.id.title_detail_view);
        category = view.findViewById(R.id.category_detail_view);
        releaseDate = view.findViewById(R.id.release_date_detail_view);
        poster = view.findViewById(R.id.poster_detail_view);


        getDataFromIntent();

        return view;

    }

    private void getDataFromIntent() {


        title.setText(chosenMovie.getTitle());
        releaseDate.setText(chosenMovie.getReleaseDate());


        Glide.with(this).load(Credentials.IMG_BASE_URL + chosenMovie.getPosterPath()).into(poster);

    }

}