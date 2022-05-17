package com.example.eurythmics.view.fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.view.fragments.adapters.RatingViewHolder;

import java.util.MissingResourceException;

public class MoviePosterFragment extends Fragment {

    private ImageView poster;
    private MovieModel chosenMovie;
    private boolean isRated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.full_poster_fragment, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null){
            MovieModel movieModel;
            if (bundle.getParcelable("poster") == null){
                movieModel = bundle.getParcelable("rated_poster");
                isRated = true;
            }else {
                movieModel = bundle.getParcelable("poster");
            }
            chosenMovie = movieModel.movieModel;
        } else {
            throw new MissingResourceException("No chosen transaction was sent with the fragment, hence fragment cannot be created", MovieModel.class.toString(), "CHOSEN_TRANSACTION" );
        }

        poster = view.findViewById(R.id.full_poster);

        Glide.with(poster.getContext()).load(Credentials.IMG_BASE_URL + chosenMovie.getPosterPath()).into(poster);

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieModel currentMov = chosenMovie;
                Fragment fragment;
                Bundle bundle = new Bundle();
                if (isRated){
                    fragment = new RatedMovieDetailView();
                    bundle.putParcelable("ratedMovie", new MovieModel(currentMov));
                }else {
                    fragment = new MovieDetailFragment();
                    bundle.putParcelable("CHOSEN_TRANSACTION", new MovieModel(currentMov));
                }
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });


        return view;

    }


}
