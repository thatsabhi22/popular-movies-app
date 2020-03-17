package com.udacity.popularmoviesstage2app.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.udacity.popularmoviesstage2app.models.Movie;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private List<Movie> movieList;
    private MovieRepository movieRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        movieRepository = MovieRepository.getInstance();
        movieList = movieRepository.getMovies();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
