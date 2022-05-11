package com.example.eurythmics.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.models.MovieModel;

import java.util.MissingResourceException;

public class MovieDetailFragment extends Fragment {

    private ImageView poster, likeButton;

    private TextView title, category, releaseDate, durationTime;

    private Button addRatingButton;

    private MovieModel chosenMovie;

    private boolean isFavorite = false;



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
        addRatingButton = view.findViewById(R.id.addrating_detail_view);
        durationTime = view.findViewById(R.id.durationTime);

        likeButton = view.findViewById(R.id.likeButton);

        initLikeButton();

        initAddRatingView();


        getDataFromIntent();

        return view;

    }

    private void initLikeButton() {
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Check if movie is favorite
                //TODO Add movie to favorite

                // Add to favorites
                if (!isFavorite){
                    likeButton.setImageResource(R.drawable.ic_favorite);
                    isFavorite = true;
                }else {
                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    isFavorite = false;
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

}