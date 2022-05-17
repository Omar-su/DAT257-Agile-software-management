package com.example.eurythmics.model.api.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A class representing a movie
 */
public class MovieModel implements Parcelable {

    private String title;
    private String poster_path;
    private String release_date;
    @SerializedName("id") private int movie_id;
    private float vote_average;
    private String overview;
    private List<Integer> genre_ids;
    @SerializedName("runtime") private int duration;
    public MovieModel movieModel;
    private String category;
    private List<Genre> genres;
    private double overAllRating;


    protected MovieModel(String title, String posterPath, String releaseDate, int movie_id, float voteAverage, String movie_overView, List<Integer> genre_ids, int duration, List<Genre> genres) {
        this.title = title;
        this.poster_path = posterPath;
        this.release_date = releaseDate;
        this.movie_id = movie_id;
        this.vote_average = voteAverage;
        this.overview = movie_overView;
        this.genre_ids = genre_ids;
        this.duration = duration;
        this.genres = genres;
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
        duration = in.readInt();
        this.genres = new ArrayList<Genre>();
        in.readList(genres, genres.getClass().getClassLoader());
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

    public int getDuration(){return duration;}

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

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private String getYear(String date) {
        return date.substring(0, 4);

    }
    private String getMonth(String date) {
        return date.substring(5,7);
    }
    private String getDay(String date) {
        return date.substring(8);
    }

    public Date getDate(String releaseD) {
        // creating a Calendar object
        Calendar c1 = Calendar.getInstance();

        // set Month
        // MONTH starts with 0 i.e. ( 0 - Jan)
        c1.set(Calendar.MONTH, Integer.parseInt(getMonth(releaseD)));

        // set Date
        c1.set(Calendar.DATE, Integer.parseInt(getDay(releaseD)));

        // set Year
        c1.set(Calendar.YEAR, Integer.parseInt(getYear(releaseD)));

        return c1.getTime();
    }



    public String getCategoryFromDetailMov(){
        if (this.genres != null){
            return genres.get(0).getName();
        }
        return "Undefined";
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
        parcel.writeInt(duration);
    }
}
