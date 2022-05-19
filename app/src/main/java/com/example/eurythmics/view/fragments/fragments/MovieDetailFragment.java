package com.example.eurythmics.view.fragments.fragments;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.model.api.models.MovieModel;

import java.util.MissingResourceException;

public class MovieDetailFragment extends Fragment implements OnBackButtonClickListener {

    private ImageView poster, likeButton;

    private TextView title, category, releaseDate, durationTime, description;

    private Button addRatingButton;

    private MovieModel chosenMovie;

    private ImageButton backButtonDetailMov;

    MovieService ms;

    private String fromWhichFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_detail_view, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null){
            MovieModel movieModel = bundle.getParcelable("CHOSEN_TRANSACTION");
            chosenMovie = movieModel.movieModel;
            fromWhichFragment = bundle.getString("fromWhichFragment");

        } else {
            throw new MissingResourceException("No chosen transaction was sent with the fragment, hence fragment cannot be created", MovieModel.class.toString(), "CHOSEN_TRANSACTION" );
        }

        ms = MovieService.getMovieService();

        title = view.findViewById(R.id.title_detail_view);
        category = view.findViewById(R.id.category_detail_view);
        releaseDate = view.findViewById(R.id.release_date_detail_view);
        poster = view.findViewById(R.id.poster_detail_view);
        addRatingButton = view.findViewById(R.id.addrating_detail_view);
        durationTime = view.findViewById(R.id.durationTime);

        likeButton = view.findViewById(R.id.likeButton);
        description = view.findViewById(R.id.description_field_detail);
        backButtonDetailMov = view.findViewById(R.id.backButtonDetailMov);

        initLikeButton();

        initAddRatingView();

        initBackButton();

        getDataFromIntent();

        intiPoster();

        return view;

    }


    private void intiPoster() {
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieModel currentMov = chosenMovie;
                Fragment fragment = new MoviePosterFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("poster", new MovieModel(currentMov));
                bundle.putString("fromWhichFragment", fromWhichFragment);
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }

    private void initLikeButton() {
        if (ms.isFavorite(chosenMovie.getMovie_id())){
            likeButton.setImageResource(R.drawable.ic_favorite);
        }else {
            likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Add to favorites
                if (ms.isFavorite(chosenMovie.getMovie_id())){
                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    ms.deleteFavoriteMovie(chosenMovie.getMovie_id());
                }else {
                    likeButton.setImageResource(R.drawable.ic_favorite);
                    ms.addFavorite(chosenMovie.getMovie_id());
                }

            }
        });
    }

    private void initAddRatingView() {
        addRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EditRatingFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("movie_rating", chosenMovie);
                bundle.putString("fromWhichFragment", fromWhichFragment);
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();            }
        });
    }

    private void getDataFromIntent() {

        String categoryName = chosenMovie.getCategory();
        category.setText(categoryName);

        title.setText(chosenMovie.getTitle());
        releaseDate.setText(chosenMovie.getReleaseDate());

        Pair<Integer, Integer> hoursMinutes = getTime(chosenMovie.getDuration());
        durationTime.setText(hoursMinutes.first + "h " + hoursMinutes.second + "m");
        description.setText(chosenMovie.getOverview());

        Glide.with(this).load(Credentials.IMG_BASE_URL + chosenMovie.getPosterPath()).into(poster);
    }

    private Pair<Integer, Integer> getTime(int duration) {
        int minuteInHour = 60;

        int hours = duration/minuteInHour;
        int minutes = duration % minuteInHour;
        Log.d("tag", "hours = " + hours + "minutes = " + minutes);

        Pair<Integer, Integer> hMinute = new Pair<>(hours, minutes);

        return hMinute;

    }

    @Override
    public void initBackButton() {
        backButtonDetailMov.setOnClickListener(view -> {
            if (fromWhichFragment.equals("favorite")){
                Fragment fragment = new FavoritesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }else if (fromWhichFragment.equals("ratingView")){
                Fragment fragment = new RatingFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }

        });
    }
}