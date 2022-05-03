package com.example.eurythmics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.models.MovieModel;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title, category, releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_detail_view);

        poster = findViewById(R.id.poster_detail_view);
        title = findViewById(R.id.title_detail_view);
        category = findViewById(R.id.category_detail_view);
        releaseDate = findViewById(R.id.release_date_detail_view);

        getDataFromIntent();

    }

    private void getDataFromIntent() {

        if (getIntent().hasExtra("movie")){

            MovieModel movieModel = (MovieModel) getIntent().getParcelableExtra("movie");

            title.setText(movieModel.getTitle());
            releaseDate.setText(movieModel.getReleaseDate());


            Glide.with(this).load(Credentials.IMG_BASE_URL + movieModel.getPosterPath()).into(poster);



        }
    }

}