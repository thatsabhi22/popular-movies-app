package com.udacity.popularmoviesstage2app.tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import com.udacity.popularmoviesstage2app.models.Review;
import com.udacity.popularmoviesstage2app.utils.QueryUtils;

import java.util.List;

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MovieLoader.class.getName();

    /**
     * Query URL
     */
    private String mReviewUrl;

    public ReviewLoader(Context context, String url) {
        super(context);
        mReviewUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Review> loadInBackground() {
        if (TextUtils.isEmpty(mReviewUrl)) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies.
        List<Review> reviews = QueryUtils.fetchMovieReviewsData(mReviewUrl);
        return reviews;
    }
}
