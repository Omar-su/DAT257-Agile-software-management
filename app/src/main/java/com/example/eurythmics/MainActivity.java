package com.example.eurythmics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.eurythmics.databinding.ActivityMainBinding;
import com.example.eurythmics.fragments.HomeFragment;
import com.example.eurythmics.fragments.ProfileFragment;
import com.example.eurythmics.fragments.RatingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.eurythmics.Movie.*;

import database.DataBaseManager;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private BottomNavigationView bottomNav;

    DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialise database manager and thus database
        dataBaseManager = new DataBaseManager(this);

        //initialise movie service
        MovieService ms = new MovieService(dataBaseManager);


        bottomNav = findViewById(R.id.bottomNav);
        // start app with displaying Home Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayout_main, new HomeFragment()).commit();





        // init compoments
        initBottomNavigationOnClick();



    }

    private  void  initBottomNavigationOnClick(){

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    break;

                case R.id.nav_rating:
                    fragment = new RatingFragment();
                    break;

                case R.id.nav_profile:
                    fragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            return true;
        });

    }


}