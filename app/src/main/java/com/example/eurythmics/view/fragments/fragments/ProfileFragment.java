package com.example.eurythmics.view.fragments.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.eurythmics.R;

public class ProfileFragment extends Fragment {
    private ImageView profilePicture;

    public ProfileFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePicture = view.findViewById(R.id.profile_picture);
        Glide.with(this).load("@drawable/rectangle_3");


        return view;
    }
}