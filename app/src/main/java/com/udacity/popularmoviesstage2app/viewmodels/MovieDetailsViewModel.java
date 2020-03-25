package com.udacity.popularmoviesstage2app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.popularmoviesstage2app.models.Review;
import com.udacity.popularmoviesstage2app.models.Trailer;
import com.udacity.popularmoviesstage2app.utils.MovieRepository;

import java.util.List;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieRepository = MovieRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Trailer>> getMovieTrailers(String id) {
        return movieRepository.getMovieTrailers(id);
    }

    public LiveData<List<Review>> getMovieReviews(String id) {
        return movieRepository.getMovieReviews(id);
    }
}
