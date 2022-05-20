package com.example.eurythmics.view.fragments.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.eurythmics.R;
import com.example.eurythmics.view.fragments.adapters.SliderAdapter;
import com.example.eurythmics.model.api.models.MovieModel;
import com.example.eurythmics.viewmodels.HomeListViewModel;

import java.util.List;

/**
 This class represents the fragment for home screen. Here, the user can:
 * <p><ul>
 * <li>access the rated movies by clicking on the Movies button. This opens the {@link com.example.eurythmics.view.fragments.fragments.RatedMoviesCollectionFragment}
 * <li>access the rated TV series by clicking on the TV series button. This opens the {@link com.example.eurythmics.view.fragments.fragments.RatedSeriesCollectionFragment}
 * <li>access the favorite movies and TV series by clicking on the Favorites button. This opens the {@link com.example.eurythmics.view.fragments.fragments.FavoritesCollectionFragment}
 * </ul><p>
 * There is also an upcoming movies carousel. It is possible scroll the carousel to left and right, however nothing happens if you click on a movie poster.
 * @author Omar Suliman
 */
public class HomeFragment extends Fragment {

    private ImageButton movie_collection_button;
    private ImageButton series_collection_button;
    private ImageButton favorites_collection_button;
    HomeListViewModel homeListViewModel;
    private ViewPager2 viewPager2;

    private SliderAdapter sliderAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        init_movie_collection_button(view);
        init_series_collection_button(view);
        init_favorites_collection_button(view);
        homeListViewModel = new ViewModelProvider(this).get(HomeListViewModel.class);
        viewPager2 = view.findViewById(R.id.viewImageSlider);
        configureSliderAdapter();
        observerAnyChangeCat();

        searchUpcomingMovies("upcoming",1);

        return view;
    }

    private void configureSliderAdapter() {
        sliderAdapter = new SliderAdapter();
        viewPager2.setAdapter(sliderAdapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1- Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(transformer);
    }

    private void searchUpcomingMovies(String q, int i) {
        homeListViewModel.searchUpcomingMovie(q,i);
    }

    private void observerAnyChangeCat() {
        homeListViewModel.getUpcomingMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // observing for any data change
                if (movieModels!=null){
                    sliderAdapter.setMovies(movieModels);
                }

            }
        });
    }


    private void init_movie_collection_button(View view){

        movie_collection_button = view.findViewById(R.id.movie_collection_button);
        movie_collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RatedMoviesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }

    private void init_series_collection_button(View view){
        series_collection_button = view.findViewById(R.id.series_collection_button);
        series_collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RatedSeriesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }

    private void init_favorites_collection_button(View view){
        favorites_collection_button = view.findViewById(R.id.favorites_collection_button);
        favorites_collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FavoritesCollectionFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, fragment).commit();
            }
        });
    }


}