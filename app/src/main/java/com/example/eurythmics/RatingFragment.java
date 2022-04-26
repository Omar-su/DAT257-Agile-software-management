package com.example.eurythmics;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eurythmics.api.Credentials;
import com.example.eurythmics.api.MovieApi;
import com.example.eurythmics.api.models.MovieModel;
import com.example.eurythmics.api.request.ServiceApi;
import com.example.eurythmics.api.response.MovieSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Search view
    private SearchView searchBar;

    //next button
    private Button nextButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RatingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ratingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatingFragment newInstance(String param1, String param2) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);



        searchBar = view.findViewById(R.id.action_search);
        nextButton = view.findViewById(R.id.next_but);

        initSearchBar();
        initNextButton();
        return view;
    }

    private void initNextButton() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout_main, new EditRatingFragment()).commit();

            }
        });
    }

    private void initSearchBar() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getRetrofitResponse(s);
                return false;
            }
        });
    }

    private void getRetrofitResponse(String searchString) {

        MovieApi movieApi = ServiceApi.getMovieApi();

        Call<MovieSearchResponse> movieCategory = movieApi.searchMovieByName( Credentials.API_KEY, searchString);

        movieCategory.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200){

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movieModel: movies){
                        Log.d("Tag", "successful  =============" + movieModel.getTitle());
                    }

                }else {
                    try {
                        makeToast(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                makeToast("failed attempt to search, try again");
            }
        });







    }



    //Method to make a Toast. Use to test
    Toast t;
    private void makeToast(String s){
        if(t != null) {
            t.cancel();
        }
        t = Toast.makeText(requireActivity().getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }
}