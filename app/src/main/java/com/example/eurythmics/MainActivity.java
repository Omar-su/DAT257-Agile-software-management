package com.example.eurythmics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;

import com.example.eurythmics.databinding.ActivityMainBinding;
import com.example.eurythmics.view.fragments.fragments.HomeFragment;
import com.example.eurythmics.view.fragments.fragments.ProfileFragment;
import com.example.eurythmics.view.fragments.fragments.RatingFragment;
import com.example.eurythmics.model.api.models.MovieService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.eurythmics.model.api.database.DataBaseManager;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private BottomNavigationView bottomNav;

    DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialise com.example.eurythmics.model.api.database manager and thus com.example.eurythmics.model.api.database
        dataBaseManager = new DataBaseManager(this);
        //dataBaseManager.onUpgrade(dataBaseManager.getWritableDatabase(), 1, 2);

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