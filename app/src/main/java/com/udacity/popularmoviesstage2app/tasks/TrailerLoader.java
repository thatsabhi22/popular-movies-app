package com.udacity.popularmoviesstage2app.tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import com.udacity.popularmoviesstage2app.models.Review;
import com.udacity.popularmoviesstage2app.models.Trailer;
import com.udacity.popularmoviesstage2app.utils.QueryUtils;

import java.util.List;

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MovieLoader.class.getName();

    /**
     * Query URL
     */
    private String mTrailerUrl;

    public TrailerLoader(Context context, String url) {
        super(context);
        mTrailerUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Trailer> loadInBackground() {
        if (TextUtils.isEmpty(mTrailerUrl)) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies.
        List<Trailer> trailers = QueryUtils.fetchMovieTrailerData(mTrailerUrl);
        return trailers;
    }
}
