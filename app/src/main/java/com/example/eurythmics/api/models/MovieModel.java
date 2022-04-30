package com.example.eurythmics.api.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class representing a movie
 */
public class MovieModel implements Parcelable {

    private String title;
    private String poster_path;
    private String releaseDate;
    private int movie_id;
    private float voteAverage;
    private String overview;
    private int runtime;


    public MovieModel(String title, String posterPath, String releaseDate, int movie_id, float voteAverage, String movie_overView, int runtime) {
        this.title = title;
        this.poster_path = posterPath;
        this.releaseDate = releaseDate;
        this.movie_id = movie_id;
        this.voteAverage = voteAverage;
        this.overview = movie_overView;
        this.runtime = runtime;
    }


    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        releaseDate = in.readString();
        movie_id = in.readInt();
        voteAverage = in.readFloat();
        overview = in.readString();
        runtime = in.readInt();
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

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public int getRuntime(){return runtime;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(releaseDate);
        parcel.writeInt(movie_id);
        parcel.writeFloat(voteAverage);
        parcel.writeString(overview);
    }
}
