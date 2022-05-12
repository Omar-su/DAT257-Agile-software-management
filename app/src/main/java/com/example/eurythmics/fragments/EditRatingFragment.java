package com.example.eurythmics.fragments;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eurythmics.Movie.Movie;
import com.example.eurythmics.Movie.MovieService;
import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.Review.Review;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.adapters.InputFilterMinMax;
import com.example.eurythmics.api.models.MovieModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.MissingResourceException;

import database.DataBaseManager;

public class EditRatingFragment extends Fragment {

    DataBaseManager dbHelper;
    private EditText storyRating, charactersRating, scoreRating, sceneryRating, overallRating;
    private ImageButton incrementStory, incrementCharacters, incrementScore, incrementScenery, incrementOverall;
    private ImageButton decrementStory, decrementCharacters, decrementScore, decrementScenery, decrementOverall;
    private TextInputEditText notes;
    private Button btnSave;
    private TextView movieTitle;
    private ImageView moviePoster;


    MovieModel chosenMovie;
    MovieService ms;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_rating,container,false);

        Bundle bundle = this.getArguments();
        ms = MovieService.getMovieService();

        if (bundle != null){
            chosenMovie = bundle.getParcelable("movie_rating");
            Log.d("TAG", chosenMovie.getTitle());

        } else {
            throw new MissingResourceException("No chosen transaction was sent with the fragment, hence fragment cannot be created", MovieModel.class.toString(), "CHOSEN_TRANSACTION" );
        }

        dbHelper = new DataBaseManager(null);

        init(view);
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

        // Movie title
        movieTitle = view.findViewById(R.id.movieTitleRating);
        movieTitle.setText(chosenMovie.getTitle());

        // Movie poster
        moviePoster = view.findViewById(R.id.moviePosterRating);
        Glide.with(this).load(Credentials.IMG_BASE_URL + chosenMovie.getPosterPath()).into(moviePoster);

        notes = view.findViewById(R.id.editTextInput);

        btnSave = view.findViewById(R.id.saveButton);

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

                    ms.addReview( chosenMovie.getTitle(), newStoryRating, newCharactersRating,
                            newScoreRating, newSceneryRating, newOverallRating, newNotes);

                    // isReviewed needs Movie Class object, we have MovieModel.
                    // this confirms that the movie is actually added to the list of reviewed ones,
                    // meaning it was successfully saved.
                    Movie currentMovie = new Movie(chosenMovie.getTitle(), chosenMovie.getOverview());

                    if (ms.isReviewed(currentMovie)) {
                        Toast toast = Toast.makeText(getContext(), "Successfully saved rating!", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    //sends you back to movie detail fragment after saving review
                    Fragment fragment = new RatingFragment();

                    // ideally want it to go back to MovieDetailFragment, but doesnt seem to work.

                    //Fragment fragment = new MovieDetailFragment();
                    //Bundle bundle = new Bundle();
                    //bundle.putParcelable("CHOSEN_TRANSACTION", chosenMovie);
                    //fragment.setArguments(bundle);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
                }
                else {
                    Toast toast = Toast.makeText(getContext(), "An overall rating is required in order to submit!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    public void checkForExistingReview(MovieModel movieModel) {
        Movie currentMovie = new Movie(movieModel.getTitle(), movieModel.getOverview());
        if(ms.isReviewed(currentMovie)) {
            Review existingReview = ms.getReview(currentMovie);

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
