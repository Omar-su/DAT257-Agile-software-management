package com.example.eurythmics.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a movie
 */
public class MovieModel implements Parcelable {

    private String title;
    private String poster_path;
    private String release_date;
    private int movie_id;
    private float vote_average;
    private String overview;
    private List<Integer> genre_ids;
    public MovieModel movieModel;

    private String category;


    protected MovieModel(String title, String posterPath, String releaseDate, int movie_id, float voteAverage, String movie_overView, List<Integer> genre_ids) {
        this.title = title;
        this.poster_path = posterPath;
        this.release_date = releaseDate;
        this.movie_id = movie_id;
        this.vote_average = voteAverage;
        this.overview = movie_overView;
        this.genre_ids = genre_ids;
    }


    public MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        movie_id = in.readInt();
        vote_average = in.readFloat();
        overview = in.readString();
        this.genre_ids = new ArrayList<Integer>();
        in.readList(genre_ids, genre_ids.getClass().getClassLoader());
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public MovieModel(MovieModel selectedMovie) {
        this.movieModel = selectedMovie;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public float getVoteAverage() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public List<Integer> getGenre_ids(){return genre_ids;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeInt(movie_id);
        parcel.writeFloat(vote_average);
        parcel.writeString(overview);
        parcel.writeList(genre_ids);
    }
}
