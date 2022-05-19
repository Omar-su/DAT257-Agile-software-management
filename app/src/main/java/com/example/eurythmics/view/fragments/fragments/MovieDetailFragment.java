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

/**
 * This class represents the fragment for viewing a detailed description of an unrated movie.
 * This includes the movie's:
 * <p><ul>
 * <li> poster
 * <li> title
 * <li> genre
 * <li> release date
 * <li> duration/running time
 * <li> brief description
 * </ul><p>
 * If the user taps on the movie poster, the {@link com.example.eurythmics.view.fragments.fragments.MoviePosterFragment} will be opened and a full sized movie poster will appear.
 * The fragments also contains a heart shaped button and an add rating button. <p>
 * In this fragment a movie or series can be marked as favorite by clicking on the heart shaped button.
 * A filled in heart symbol indicates that the movie or series exists in the favorites collection {@link com.example.eurythmics.view.fragments.fragments.FavoritesCollectionFragment} <p>
 * When the user clicks on the Add rating button, the {@link com.example.eurythmics.view.fragments.fragments.EditRatingFragment} opens and from there, the user can create, change or delete a rating.
 * <p>Can be opened from {@link  com.example.eurythmics.view.fragments.fragments.RatingFragment}
 * @author Omar Suliman
 * @author Desirée Staaf, Eugene Dvoryankov, Ida Nordlund, Jenny Carlsson
 */
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
        poster.setOnClickListener(view -> {
            MovieModel currentMov = chosenMovie;
            Fragment fragment = new MoviePosterFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("poster", new MovieModel(currentMov));
            bundle.putString("fromWhichFragment", fromWhichFragment);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
        });
    }

    private void initLikeButton() {
        if (ms.isFavorite(chosenMovie.getMovie_id())){
            likeButton.setImageResource(R.drawable.ic_favorite);
        }else {
            likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        likeButton.setOnClickListener(view -> {

            // Add to favorites
            if (ms.isFavorite(chosenMovie.getMovie_id())){
                likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                ms.deleteFavoriteMovie(chosenMovie.getMovie_id());
            }else {
                likeButton.setImageResource(R.drawable.ic_favorite);
                ms.addFavorite(chosenMovie.getMovie_id());
            }

        });
    }

    private void initAddRatingView() {
        addRatingButton.setOnClickListener(view -> {
            Fragment fragment = new EditRatingFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie_rating", chosenMovie);
            bundle.putString("fromWhichFragment", fromWhichFragment);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();            });
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
            Fragment fragment = new RatedMoviesCollectionFragment();

            switch (fromWhichFragment){
                case "favorite": fragment = new FavoritesCollectionFragment();break;
                case "ratingView": fragment = new RatingFragment(); break;
                case "profile": fragment = new ProfileFragment(); break;
                case "ratedMovie": break;
            }
            Bundle bundle = new Bundle();
            chosenMovie.movieModel = chosenMovie;
            bundle.putParcelable(fromWhichFragment, chosenMovie);
            bundle.putString("fromWhichFragment", fromWhichFragment);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
        });
    }
}