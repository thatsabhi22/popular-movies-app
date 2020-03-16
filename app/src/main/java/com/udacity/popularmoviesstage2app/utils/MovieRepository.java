package com.udacity.popularmoviesstage2app.utils;

import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.tasks.MoviesWebService;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final MovieRepository ourInstance = new MovieRepository();
    public List<Movie> movieList;

    private MovieRepository() {
        movieList = new ArrayList<>();
    }

    public static MovieRepository getInstance() {
        return ourInstance;
    }

    public String getMovies() {
        MoviesWebService moviesWebService =
                MoviesWebService.retrofit.create(MoviesWebService.class);
        //String API_KEY = Resources.getSystem().getResourceName(R.string.api_key);
        String API_KEY = "5dae56b7517d66c0d3da2e78ad58bc23";

        Call<String> call = moviesWebService.getMovies(API_KEY);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Tangho", "inside response");
                if (response.isSuccessful()) {
                    //newsData.setValue(response.body());
                    Log.d("Tangho", "inside success");
                    String mResponse = response.body();

                    if (mResponse != null) {
                        mResponse.length();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.d("Tangho", "Failure happened" );
            }
        });
        return "";
    }
}
