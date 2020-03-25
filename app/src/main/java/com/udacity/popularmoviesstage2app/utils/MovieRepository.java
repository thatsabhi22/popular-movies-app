package com.udacity.popularmoviesstage2app.utils;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.udacity.popularmoviesstage2app.database.MoviesDatabase;
import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.models.Review;
import com.udacity.popularmoviesstage2app.models.Trailer;
import com.udacity.popularmoviesstage2app.tasks.MoviesWebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private MoviesWebService moviesWebService;
    private MoviesDatabase mDatabase;

    private MovieRepository(Context context) {
        moviesWebService = MoviesWebService.retrofit.create(MoviesWebService.class);
        mDatabase = MoviesDatabase.getInstance(context);
    }

    public static MovieRepository getInstance(Context context) {
        return new MovieRepository(context);
    }

    public LiveData<List<Movie>> getMovies(String sort_type) {
        final MutableLiveData<List<Movie>> data = new MutableLiveData<>();
        String API_KEY = "5dae56b7517d66c0d3da2e78ad58bc23";

        Call<String> call = moviesWebService.getMovies(sort_type, API_KEY);
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
        return QueryUtils.extractMoviesFromJson(movieListResponse);
    }

    private ArrayList<Trailer> processMovieTrailersResponse(String movieTrailersResponse) {
        return QueryUtils.extractMovieTrailersFromJson(movieTrailersResponse);
    }

    private ArrayList<Review> processMovieReviewsResponse(String movieReviewsResponse) {
        return QueryUtils.extractMovieReviewsFromJson(movieReviewsResponse);
    }

    public LiveData<List<Movie>> getMovieListFromDB() {
        return mDatabase.moviesDAO().loadAllMovies();
    }

    public LiveData<List<Trailer>> getMovieTrailers(String id) {
        final MutableLiveData<List<Trailer>> data = new MutableLiveData<>();
        String API_KEY = "5dae56b7517d66c0d3da2e78ad58bc23";

        Call<String> call = moviesWebService.getMoviesTrailers(id, API_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String movieTrailerResponse = response.body();
                    if (movieTrailerResponse != null) {
                        data.setValue(processMovieTrailersResponse(movieTrailerResponse));
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

    public LiveData<List<Review>> getMovieReviews(String id) {
        final MutableLiveData<List<Review>> data = new MutableLiveData<>();
        String API_KEY = "5dae56b7517d66c0d3da2e78ad58bc23";

        Call<String> call = moviesWebService.getMovieReviews(id, API_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String movieReviewResponse = response.body();
                    if (movieReviewResponse != null) {
                        data.setValue(processMovieReviewsResponse(movieReviewResponse));
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
}
