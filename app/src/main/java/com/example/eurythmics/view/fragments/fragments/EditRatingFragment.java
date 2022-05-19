package com.example.eurythmics.view.fragments.fragments;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eurythmics.model.api.models.MovieService;
import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.model.api.Review.Review;
import com.example.eurythmics.model.api.Credentials;
import com.example.eurythmics.view.fragments.adapters.InputFilterMinMax;
import com.example.eurythmics.model.api.models.MovieModel;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.MissingResourceException;

/**
 * This class represents the fragment for creating, editing and deleting a rating for the chosen movie.
 * Here, the user can:
 * <p><ul>
 * <li>specify values for a new rating and save it
 * <li>change an existing rating's values, with all the values automatically loaded into the fragment
 * <li>cancel this operation
 * <li>delete this rating
 * </ul><p>
 * Opened from {@link  com.example.eurythmics.view.fragments.fragments.MovieDetailFragment} <p>
 * Opened from {@link  com.example.eurythmics.view.fragments.fragments.RatedMovieDetailView}
 * @author Desirée Staaf, Eugene Dvoryankov, Ida Nordlund, Jenny Carlsson
 * @author Fabian Flaa, Omar Suliman, Oscar Palmqvist
 *
 */
public class EditRatingFragment extends Fragment {

    private EditText storyRating, charactersRating, scoreRating, sceneryRating, overallRating;
    private ImageButton incrementStory, incrementCharacters, incrementScore, incrementScenery, incrementOverall;
    private ImageButton decrementStory, decrementCharacters, decrementScore, decrementScenery, decrementOverall;
    private ImageButton btnDelete;
    private TextInputEditText notes;
    private Button btnSave, cancelButton;
    private TextView movieTitle;
    private ImageView moviePoster;

    private String fromWhichFragment;

    MovieModel chosenMovie;
    MovieService ms;

    /**
     * Method that runs when the fragment is being created.
     * Connects the fragment xml file to the fragment class and initializes the fragment's components.
     * @return returns the view
     */
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_rating,container,false);

        Bundle bundle = this.getArguments();
        ms = MovieService.getMovieService();

        if (bundle != null){
            chosenMovie = bundle.getParcelable("movie_rating");
            fromWhichFragment = bundle.getString("fromWhichFragment");

        } else {
            throw new MissingResourceException("No chosen transaction was sent with the fragment, hence fragment cannot be created", MovieModel.class.toString(), "CHOSEN_TRANSACTION" );
        }

        init(view);
        initCancelButton();
        checkForExistingReview(chosenMovie);

        return view;
    }

    private void init(View view) {

        storyRating = view.findViewById(R.id.storyNumberPicker).findViewById(R.id.number);
        incrementStory = view.findViewById(R.id.storyNumberPicker).findViewById(R.id.increment);
        decrementStory = view.findViewById(R.id.storyNumberPicker).findViewById(R.id.decrement);
        storyRating.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        charactersRating = view.findViewById(R.id.characterNumberPicker).findViewById(R.id.number);
        incrementCharacters = view.findViewById(R.id.characterNumberPicker).findViewById(R.id.increment);
        decrementCharacters = view.findViewById(R.id.characterNumberPicker).findViewById(R.id.decrement);
        charactersRating.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        scoreRating = view.findViewById(R.id.scoreNumberPicker).findViewById(R.id.number);
        incrementScore = view.findViewById(R.id.scoreNumberPicker).findViewById(R.id.increment);
        decrementScore = view.findViewById(R.id.scoreNumberPicker).findViewById(R.id.decrement);
        scoreRating.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        sceneryRating = view.findViewById(R.id.sceneryNumberPicker).findViewById(R.id.number);
        incrementScenery = view.findViewById(R.id.sceneryNumberPicker).findViewById(R.id.increment);
        decrementScenery = view.findViewById(R.id.sceneryNumberPicker).findViewById(R.id.decrement);
        sceneryRating.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        overallRating = view.findViewById(R.id.overallRatingNumberPicker).findViewById(R.id.number);
        incrementOverall = view.findViewById(R.id.overallRatingNumberPicker).findViewById(R.id.increment);
        decrementOverall = view.findViewById(R.id.overallRatingNumberPicker).findViewById(R.id.decrement);
        overallRating.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        notes = view.findViewById(R.id.editTextInput);

        // Movie title
        movieTitle = view.findViewById(R.id.movieTitleRating);
        movieTitle.setText(chosenMovie.getTitle());

        // Movie poster
        moviePoster = view.findViewById(R.id.moviePosterRating);
        Glide.with(this).load(Credentials.IMG_BASE_URL + chosenMovie.getPosterPath()).into(moviePoster);

        btnSave = view.findViewById(R.id.saveButton);
        btnDelete = view.findViewById(R.id.deleteReviewButton);
        cancelButton = view.findViewById(R.id.cancelButton);



        //implement all buttons
        incrementStory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(storyRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count+=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //(round defined further down in this class), updates the displayed value.
                storyRating.setText("" + round(count,1));
            }
        });

        decrementStory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(storyRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count-=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //(round defined further down in this class), updates the displayed value.
                storyRating.setText("" + round(count,1));
            }
        });

        incrementCharacters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(charactersRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count+=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //(round defined further down in this class), updates the displayed value.
                charactersRating.setText("" + round(count,1));
            }
        });

        decrementCharacters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(charactersRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count-=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //(round defined further down in this class), updates the displayed value.
                charactersRating.setText("" + round(count,1));
            }
        });

        incrementScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(scoreRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count+=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //(round defined further down in this class), updates the displayed value.
                scoreRating.setText("" + round(count,1));
            }
        });

        decrementScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(scoreRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count-=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //(round defined further down in this class), updates the displayed value.
                scoreRating.setText("" + round(count,1));
            }
        });

        incrementScenery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(sceneryRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count+=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //round defined in this class
                sceneryRating.setText("" + round(count,1));
            }
        });

        decrementScenery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(sceneryRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count-=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //round defined in this class
                sceneryRating.setText("" + round(count,1));
            }
        });

        incrementOverall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(overallRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count+=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //round defined in this class
                overallRating.setText("" + round(count,1));
            }
        });

        decrementOverall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                double count = 0;
                //if a value is inputed already, the counter skips to that value
                try {
                    count = Double.parseDouble(overallRating.getText().toString());
                } catch (NumberFormatException nfe) {}

                count-=0.1;

                //max and min scores
                if (count > 10) count = 10;
                else if (count < 0) count = 0;

                //round defined in this class
                overallRating.setText("" + round(count,1));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double newStoryRating = 0;
                double newCharactersRating = 0;
                double newScoreRating = 0;
                double newSceneryRating = 0;
                double newOverallRating = 0;
                if (overallRating.length() != 0) {
                    try {
                        newStoryRating = Double.parseDouble(storyRating.getText().toString());
                    } catch (NumberFormatException nfe) {}

                    try {
                        newCharactersRating = Double.parseDouble(charactersRating.getText().toString());
                    } catch (NumberFormatException nfe) {}

                    try {
                        newScoreRating = Double.parseDouble(scoreRating.getText().toString());
                    } catch (NumberFormatException nfe) {}

                    try {
                        newSceneryRating = Double.parseDouble(sceneryRating.getText().toString());
                    } catch (NumberFormatException nfe) {}

                    try {
                        newOverallRating = Double.parseDouble(overallRating.getText().toString());
                    } catch (NumberFormatException nfe) {}

                    String newNotes = notes.getText().toString();

                    Log.d("TAG", "------" + newNotes);

                    ms.addReview( chosenMovie.getMovie_id(), newStoryRating, newCharactersRating,
                            newScoreRating, newSceneryRating, newOverallRating, newNotes);

                    // isReviewed needs Movie Class object, we have MovieModel.
                    // this confirms that the movie is actually added to the list of reviewed ones,
                    // meaning it was successfully saved.

                    if (ms.isReviewed(chosenMovie.getMovie_id())) {
                        Toast toast = Toast.makeText(getContext(), "Review successfully saved!", Toast.LENGTH_LONG);
                        toast.show();
                        Fragment fragment = new RatedMovieDetailView();
                        Bundle bundle = new Bundle();
                        chosenMovie.movieModel = chosenMovie;
                        bundle.putParcelable("ratedMovie", chosenMovie);
                        bundle.putString("fromWhichFragment", fromWhichFragment);
                        fragment.setArguments(bundle);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
                    }

                    //sends you back to movie detail fragment after saving review
                    //Fragment fragment = new RatingFragment();

                    // ideally want it to go back to MovieDetailFragment, but doesnt seem to work.


                }
                else {
                    Toast toast = Toast.makeText(getContext(), "An overall rating is required in order to submit!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        btnDelete.setOnClickListener(view1 -> {
            if (ms.isReviewed(chosenMovie.getMovie_id())) {
                ms.removeReview(chosenMovie.getMovie_id());
            }
            //check if actually removed
            if (!ms.isReviewed(chosenMovie.getMovie_id())) {
                Toast toast = Toast.makeText(getContext(), "Review successfully deleted", Toast.LENGTH_LONG);
                toast.show();

                storyRating.setText("");
                charactersRating.setText("");
                scoreRating.setText("");
                sceneryRating.setText("");
                overallRating.setText("");
                notes.setText("");
            }
            else {
                Toast toast = Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void initCancelButton() {
        cancelButton.setOnClickListener(view -> {
            Toast toast = Toast.makeText(getContext(), "Review canceled", Toast.LENGTH_LONG);
            toast.show();
            Fragment fragment;
            String s = "";
            if (!ms.isReviewed(chosenMovie.getMovie_id())){
                fragment = new MovieDetailFragment();
                s = "CHOSEN_TRANSACTION";
            }else {
                fragment = new RatedMovieDetailView();
                s = "ratedMovie";
            }
            Bundle bundle = new Bundle();
            chosenMovie.movieModel = chosenMovie;
            bundle.putParcelable(s, chosenMovie);
            bundle.putString("fromWhichFragment", fromWhichFragment);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
        });
    }

    public void checkForExistingReview(MovieModel movieModel) {
        if(ms.isReviewed(movieModel.getMovie_id())) {
            Review existingReview = ms.getReview(movieModel.getMovie_id());

            storyRating.setText("" + existingReview.getStoryRating());
            charactersRating.setText("" + existingReview.getCharactersRating());
            scoreRating.setText("" + existingReview.getScoreRating());
            sceneryRating.setText("" + existingReview.getSceneryRating());
            overallRating.setText("" + existingReview.getOverallRating());
            notes.setText("" + existingReview.getThoughts());
        }
    }

    //rounds to nearest value with a defined number of decimals
    public static double round(double value, int decimals) {
        if (decimals < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



}
