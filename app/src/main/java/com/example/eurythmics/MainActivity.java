package com.example.eurythmics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;



import android.os.Bundle;

import com.example.eurythmics.api.models.MovieModel;
import com.example.eurythmics.databinding.ActivityMainBinding;
import com.example.eurythmics.fragments.HomeFragment;
import com.example.eurythmics.fragments.ProfileFragment;
import com.example.eurythmics.fragments.RatingFragment;
import com.example.eurythmics.viewmodels.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private BottomNavigationView bottomNav;

    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        bottomNav = findViewById(R.id.bottomNav);
        // start app with displaying Home Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayout_main, new HomeFragment()).commit();


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);


        observerAnyChange();

        // init compoments
        initBottomNavigationOnClick();



    }




    private void observerAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observing for any data change

            }
        });
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