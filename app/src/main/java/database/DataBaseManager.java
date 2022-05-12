package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.eurythmics.Review.Review;

/**
 * Provides the database required for storing movie, rating and category information,
 * as well as handling the database's inserting, removing, and requesting data.
 */
public class DataBaseManager extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "moviesDatabase";
    private static final int DATABSE_VERSION = 6;

    private static final String TABLE_MOVIES = "movies";
    private static final String TABLE_REVIEWS = "reviews";

    private static final String COLUMN_MOVIES_ID = "id";
    private static final String COLUMN_MOVIES_DESCRIPTION = "description";

    private static final String COLUMN_REVIEW_STORY = "storyRating";
    private static final String COLUMN_REVIEW_CHARACTERS = "charactersRating";
    private static final String COLUMN_REVIEW_SCORE = "scoreRating";
    private static final String COLUMN_REVIEW_SCENERY = "sceneryRating";
    private static final String COLUMN_REVIEW_OVERALL = "overallRating";
    private static final String COLUMN_REVIEW_THOUGHTS = "thoughts";


    /**
     * Generates a database for storing the created tables
     * @param context is the main activity
     */
    public DataBaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createMovieTable(sqLiteDatabase);
        createReviewsTable(sqLiteDatabase);
    }

    private void createMovieTable(SQLiteDatabase sqLiteDatabase) {
        String sMovieTable = "CREATE TABLE "+ TABLE_MOVIES +" ( " +
                COLUMN_MOVIES_ID + " TEXT PRIMARY KEY, " +
                COLUMN_MOVIES_DESCRIPTION + " TEXT" +
                " )";

        sqLiteDatabase.execSQL(sMovieTable);
    }

    private void createReviewsTable(SQLiteDatabase sqLiteDatabase) {
        String sReviewTable = "CREATE TABLE "+ TABLE_REVIEWS +" ( " +
                COLUMN_MOVIES_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_REVIEW_STORY + " REAL, " +
                COLUMN_REVIEW_CHARACTERS + " REAL, " +
                COLUMN_REVIEW_SCORE + " REAL, " +
                COLUMN_REVIEW_SCENERY + " REAL, " +
                COLUMN_REVIEW_OVERALL + " REAL, " +
                COLUMN_REVIEW_THOUGHTS + " TEXT)"
                ;

        sqLiteDatabase.execSQL(sReviewTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        System.out.println("Updated the database");
        if(oldV != newV){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
            onCreate(sqLiteDatabase);
        }
    }

    public boolean addMovie(String title, String description){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOVIES_ID, title);
        contentValues.put(COLUMN_MOVIES_DESCRIPTION, description);

        return sqLiteDatabase.insert(TABLE_MOVIES, null, contentValues) != -1;
    }

    public boolean addReview(int movieID, double storyRating, double charactersRating,
                             double scoreRating, double sceneryRating,
                             double overallRating, String thoughts){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOVIES_ID, movieID);
        contentValues.put(COLUMN_REVIEW_STORY, storyRating);
        contentValues.put(COLUMN_REVIEW_CHARACTERS, charactersRating);
        contentValues.put(COLUMN_REVIEW_SCORE, scoreRating);
        contentValues.put(COLUMN_REVIEW_SCENERY, sceneryRating);
        contentValues.put(COLUMN_REVIEW_OVERALL, overallRating);
        contentValues.put(COLUMN_REVIEW_THOUGHTS, thoughts);

        return sqLiteDatabase.insert(TABLE_REVIEWS, null, contentValues) != -1;
    }

    public boolean editReview(int movieID, double storyRating, double charactersRating,
                             double scoreRating, double sceneryRating,
                             double overallRating, String thoughts){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOVIES_ID, movieID);
        contentValues.put(COLUMN_REVIEW_STORY, storyRating);
        contentValues.put(COLUMN_REVIEW_CHARACTERS, charactersRating);
        contentValues.put(COLUMN_REVIEW_SCORE, scoreRating);
        contentValues.put(COLUMN_REVIEW_SCENERY, sceneryRating);
        contentValues.put(COLUMN_REVIEW_OVERALL, overallRating);
        contentValues.put(COLUMN_REVIEW_THOUGHTS, thoughts);

        return sqLiteDatabase.update(TABLE_REVIEWS, contentValues, COLUMN_MOVIES_ID + "=" + movieID, null) != -1;
    }

    public boolean deleteReview(Review review) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_REVIEWS, COLUMN_MOVIES_ID + "=" + review.getMovieID(), null) > 0;
    }
    public Cursor getAllMovies(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_MOVIES, null);
    }

    public Cursor getAllReviews(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_REVIEWS, null);
    }

}
