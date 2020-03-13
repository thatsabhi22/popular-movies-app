package com.udacity.popularmoviesstage2app.utils;

import com.udacity.popularmoviesstage2app.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    public List<Movie> movieList;

    private static final MovieRepository ourInstance = new MovieRepository();

    public static MovieRepository getInstance() {
        return ourInstance;
    }

    private MovieRepository() {

        movieList = new ArrayList<>();

    }
}
