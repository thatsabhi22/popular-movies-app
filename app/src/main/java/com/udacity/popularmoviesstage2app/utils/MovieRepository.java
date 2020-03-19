package com.udacity.popularmoviesstage2app.utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.tasks.MoviesWebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.popularmoviesstage2app.utils.QueryUtils.extractMoviesFromJson;

public class MovieRepository {

    private static final MovieRepository ourInstance = new MovieRepository();
    MoviesWebService moviesWebService;

    private MovieRepository() {
        moviesWebService = MoviesWebService.retrofit.create(MoviesWebService.class);
    }

    public static MovieRepository getInstance() {
        return ourInstance;
    }

    public LiveData<List<Movie>> getMovies(String sort_type) {
        final MutableLiveData<List<Movie>> data = new MutableLiveData<>();
        String API_KEY = "5dae56b7517d66c0d3da2e78ad58bc23";

        Call<String> call = moviesWebService.getMovies(sort_type,API_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String movieListResponse = response.body();
                    if (movieListResponse != null) {
                        data.setValue(processMovieListResponse(movieListResponse));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Tangho", "Failure happened fetching movies");
            }
        });
        return data;
    }

    private ArrayList<Movie> processMovieListResponse(String movieListResponse) {
        return extractMoviesFromJson(movieListResponse);
    }
}
