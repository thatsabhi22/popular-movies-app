package com.udacity.popularmoviesstage2app.tasks;

import android.content.res.Resources;

import com.udacity.popularmoviesstage2app.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface MoviesWebService {
    /**
     * URL for movies data from the MoviesDB data-set
     */
     String BASE_REQUEST_URL = "http://api.themoviedb.org/3/movie/";

    /**
     * API KEY URL Query for Movies DB API
     */
    String API_KEY_QUERY = "?api_key=";

    /**
     * sort_type variable determines the movie types filter
     */
    String sort_type = "popular";

    String API_KEY_QUERY_VALUE = Resources.getSystem().getString(R.string.api_key);

    String movies_request_url = BASE_REQUEST_URL + sort_type + API_KEY_QUERY + API_KEY_QUERY_VALUE;

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(movies_request_url)
            .build();





}
