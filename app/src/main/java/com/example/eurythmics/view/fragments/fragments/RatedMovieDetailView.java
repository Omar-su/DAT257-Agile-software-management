package com.example.eurythmics.view.fragments.fragments;

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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eurythmics.model.api.models.MovieService;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Review.Review;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.model.api.models.MovieModel;

import java.util.MissingResourceException;

/**
 * This class represents the fragment for viewing a detailed description, as well as, the rating of a rated movie.
 * The detailed description includes:
 * <p><ul>
 * <li> poster
 * <li> title
 * <li> genre
 * <li> release date
 * <li> duration/running time
 * <li> brief description
 * </ul><p>
 *
 * The rating consists of:
 * <p><ul>
 * <li> Overall rating
 * <li> character rating
 * <li> score rating
 * <li> story rating
 * <li> scenery rating
 * <li> notes box
 * </ul><p>
 * To edit the rating, click on the edit rating button that looks like a pen. This will take the user to the {@link com.example.eurythmics.view.fragments.fragments.EditRatingFragment} <p>
 * If the user taps on the movie poster, the {@link com.example.eurythmics.view.fragments.fragments.MoviePosterFragment} will be opened and a full sized movie poster will appear.
 * The fragments also contains a heart shaped button and an add rating button. <p>
 * In this fragment a movie or series can be marked as favorite by clicking on the heart shaped button.
 * A filled in heart symbol indicates that the movie or series exists in the favorites collection {@link com.example.eurythmics.view.fragments.fragments.FavoritesCollectionFragment} <p>
 * When the user clicks on the Add rating button, the {@link com.example.eurythmics.view.fragments.fragments.EditRatingFragment} opens and from there, the user can create, change or delete a rating.
 * <p>Can be opened from {@link  com.example.eurythmics.view.fragments.fragments.RatingFragment}
 * @author Omar Suliman
 * @author Desirée Staaf, Eugene Dvoryankov, Ida Nordlund, Jenny Carlsson
 */
public class RatedMovieDetailView extends Fragment implements OnBackButtonClickListener{
    private ImageView poster, likeButton;

    private TextView title, category, releaseDate, durationTime,
            storyRating, charactersRating, scoreRating, sceneryRating, overallRating, notes;

    private ImageView editRating;

    private MovieModel chosenMovie;

    private ImageButton backButton;

    private MovieService ms;

    private String fromWhichFragment;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_with_existing_rating_view, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null){
            MovieModel movieModel = bundle.getParcelable("ratedMovie");
            chosenMovie = movieModel.movieModel;
            fromWhichFragment = bundle.getString("fromWhichFragment");
        } else {
            throw new MissingResourceException("No chosen transaction was sent with the fragment, hence fragment cannot be created", MovieModel.class.toString(), "CHOSEN_TRANSACTION" );
        }


        title = view.findViewById(R.id.title_detail_view);
        releaseDate = view.findViewById(R.id.release_date_detail_view);
        poster = view.findViewById(R.id.poster_detail_view);
        category = view.findViewById(R.id.category_detail_view);
        storyRating = view.findViewById(R.id.story_value_movie_with_existing);
        charactersRating = view.findViewById(R.id.chracters_value_movie_with_existing);
        scoreRating= view.findViewById(R.id.score_value_movie_with_existing);
        sceneryRating = view.findViewById(R.id.scenery_value_movie_with_existing);
        overallRating = view.findViewById(R.id.overall_rating_value_movie_with_existing);
        notes = view.findViewById(R.id.notes_value_movie_with_existing);
        likeButton = view.findViewById(R.id.likeButton2);
        durationTime = view.findViewById(R.id.rated_detail_durationTime);
        backButton = view.findViewById(R.id.backButton_existing_movie);
        editRating = view.findViewById(R.id.edit_rating);
        ms = MovieService.getMovieService();

        initBackButton();

        initEditRating();

        initLikeButton();

        initRatingView();
        initPoster();

        getDataFromIntent();

        return view;

    }


    private void initEditRating() {
        editRating.setOnClickListener(view -> {
            Fragment fragment = new EditRatingFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie_rating", chosenMovie);
            bundle.putString("fromWhichFragment", fromWhichFragment);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
        });
    }

    private void initPoster() {
        poster.setOnClickListener(view -> {
            MovieModel currentMov = chosenMovie;
            Fragment fragment = new MoviePosterFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("rated_poster", new MovieModel(currentMov));
            bundle.putString("fromWhichFragment", fromWhichFragment);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
        });
    }

    private void getDataFromIntent() {
        title.setText(chosenMovie.getTitle());
        releaseDate.setText(chosenMovie.getReleaseDate());
        Glide.with(this).load(Credentials.IMG_BASE_URL + chosenMovie.getPosterPath()).into(poster);
        category.setText(chosenMovie.getCategory());
        Pair<Integer, Integer> hoursMinutes = getTime(chosenMovie.getDuration());
        durationTime.setText(hoursMinutes.first + "h " + hoursMinutes.second + "m");
    }

    private void initRatingView() {
        Review review = ms.getReview(chosenMovie.getMovie_id());
        storyRating.setText("" + review.getStoryRating());
        charactersRating.setText("" + review.getCharactersRating());
        scoreRating.setText("" + review.getScoreRating());
        sceneryRating.setText("" + review.getSceneryRating());
        overallRating.setText("" + review.getOverallRating());
        notes.setText(review.getThoughts());

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
                // TODO Check if movie is favorite
                //TODO Add movie to favorite

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
        backButton.setOnClickListener(view -> {
            Fragment fragment;
            switch (fromWhichFragment) {
                case "favorite":
                    fragment = new FavoritesCollectionFragment();
                    switchScenes(fragment);
                    break;
                case "ratingView":
                    fragment = new RatingFragment();
                    switchScenes(fragment);
                    break;
                case "ratedMovies":
                    fragment = new RatedMoviesCollectionFragment();
                    switchScenes(fragment);
                    break;
                case "profile":
                    fragment = new ProfileFragment();
                    switchScenes(fragment);
                    break;
            }
        });
    }

    private void switchScenes(Fragment fragment){
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
    }
}
