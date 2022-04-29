package com.example.eurythmics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import database.DataBaseManager;

public class EditRatingFragment extends Fragment {

    DataBaseManager dbHelper;
    private EditText overallRating;
    private EditText notes;
    private Button btnSave;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_edit_rating,container,false);
        dbHelper = new DataBaseManager(null);
        overallRating = view.findViewById(R.id.overallRatingLabel);
        notes = view.findViewById(R.id.notesRatingLabel);
        btnSave = view.findViewById(R.id.saveButton);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newOverallRating = overallRating.getText().toString();
                String newNotes = notes.getText().toString();
                if (overallRating.length() != 0 && notes.length() != 0){
                    AddRating("test", newOverallRating, newNotes);
                }
            }
        });
        return view;
    }
    //TODO: make this method get the title of the movie being rated
    public void AddRating(String movieTitle, String overallRating, String notes) {
        //boolean insertData = dbHelper.addReview(overallRating, notes);

    }

}
