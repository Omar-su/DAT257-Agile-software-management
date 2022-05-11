package com.example.eurythmics.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.models.MovieModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.MissingResourceException;

import database.DataBaseManager;

public class EditRatingFragment extends Fragment {

    DataBaseManager dbHelper;
    private TextView storyRating, charactersRating, scoreRating, sceneryRating, overallRating;
    private ImageButton incrementStory, incrementCharacters, incrementScore, incrementScenery, incrementOverall;
    private ImageButton decrementStory, decrementCharacters, decrementScore, decrementScenery, decrementOverall;
    private TextView notes;
    private Button btnSave;
    private TextView movieTitle;
    private ImageView moviePoster;


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

        dbHelper = new DataBaseManager(null);

        init(view);

        return view;
    }

    private void init(View view) {
        storyRating = view.findViewById(R.id.storyNumberPicker).findViewById(R.id.number);
        incrementStory = view.findViewById(R.id.storyNumberPicker).findViewById(R.id.increment);
        decrementStory = view.findViewById(R.id.storyNumberPicker).findViewById(R.id.decrement);

        charactersRating = view.findViewById(R.id.characterNumberPicker).findViewById(R.id.number);
        incrementCharacters = view.findViewById(R.id.characterNumberPicker).findViewById(R.id.increment);
        decrementCharacters = view.findViewById(R.id.characterNumberPicker).findViewById(R.id.decrement);

        scoreRating = view.findViewById(R.id.scoreNumberPicker).findViewById(R.id.number);
        incrementScore = view.findViewById(R.id.scoreNumberPicker).findViewById(R.id.increment);
        decrementScore = view.findViewById(R.id.scoreNumberPicker).findViewById(R.id.decrement);

        sceneryRating = view.findViewById(R.id.sceneryNumberPicker).findViewById(R.id.number);
        incrementScenery = view.findViewById(R.id.sceneryNumberPicker).findViewById(R.id.increment);
        decrementScenery = view.findViewById(R.id.sceneryNumberPicker).findViewById(R.id.decrement);

        overallRating = view.findViewById(R.id.overallRatingNumberPicker).findViewById(R.id.number);
        incrementOverall = view.findViewById(R.id.overallRatingNumberPicker).findViewById(R.id.increment);
        decrementOverall = view.findViewById(R.id.overallRatingNumberPicker).findViewById(R.id.decrement);

        // Movie title
        movieTitle = view.findViewById(R.id.movieTitleRating);
        movieTitle.setText(chosenMovie.getTitle());

        // Movie poster
        moviePoster = view.findViewById(R.id.moviePosterRating);
        Glide.with(this).load(Credentials.IMG_BASE_URL + chosenMovie.getPosterPath()).into(moviePoster);

        notes = view.findViewById(R.id.textInputEditText);

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
                String newOverallRating = overallRating.getText().toString();
                String newNotes = notes.getText().toString();
                Log.d("TAG", "------" + newNotes);
                Toast toast = Toast.makeText(getContext(), newNotes, Toast.LENGTH_LONG);
                toast.show();
                if (overallRating.length() != 0 && notes.length() != 0){
                    //AddRating(/** the movie title */, newOverallRating, newNotes);
                }
            }
        });
    }


    //TODO: make this method get the title of the movie being rated
    public void AddRating(String movieTitle, String overallRating, String notes) {
        //boolean insertData = dbHelper.addReview(overallRating, notes);

    }

    //rounds to nearest value with a defined number of decimals
    public static double round(double value, int decimals) {
        if (decimals < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



}
