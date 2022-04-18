package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides the database required for storing movie, rating and category information,
 * as well as handling the database's inserting, removing, and requesting data.
 */
public class DataBaseManager extends SQLiteOpenHelper{

    /**
     * Generates a database for storing the created tables
     * @param context is the main activity
     */
    public DataBaseManager(@Nullable Context context) {
        super(context, null, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createRatingTable(sqLiteDatabase);
    }

    private void createRatingTable(SQLiteDatabase sqLiteDatabase) {
        String createTableRating = " CREATE TABLE " + "RATING_TABLE" +
                " ( " + "RATING_VALUE" + " INTEGER "
                + " ) ";

        sqLiteDatabase.execSQL(createTableRating);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

}
