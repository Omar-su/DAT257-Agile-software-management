package com.example.eurythmics.view.fragments.adapters;

/**
 * An interface that has an on movie click method
 * @author Omar Sulaiman
 */
public interface OnMovieCardListener {

    /**
     * A method to implement the action after clicking on a movie card
     * @param position the position of the movie in the recycle view
     */
    void onMovieClick(int position);
}
