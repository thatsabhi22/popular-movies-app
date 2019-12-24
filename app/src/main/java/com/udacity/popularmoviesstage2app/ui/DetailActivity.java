package com.udacity.popularmoviesstage2app.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager.LoaderCallbacks;

import com.squareup.picasso.Picasso;
import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.models.Review;
import com.udacity.popularmoviesstage2app.models.Trailer;
import com.udacity.popularmoviesstage2app.tasks.MovieLoader;
import com.udacity.popularmoviesstage2app.tasks.ReviewLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    RecyclerView trailersGridRecyclerView, reviewsGridRecyclerView;
    TextView movieTitleTV, ratingTV, releaseDateTV, descriptionTV;
    ImageView posterIV;
    LoaderManager loaderManager;
    String movies_reviews_request_url = "";

    /**
     * Constant value for the movie loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int MOVIE_REVIEWS_LOADER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        trailersGridRecyclerView = findViewById(R.id.trailersGridRecyclerView);
        reviewsGridRecyclerView = findViewById(R.id.reviewsGridRecyclerView);
        movieTitleTV = findViewById(R.id.movie_title_tv);
        ratingTV = findViewById(R.id.movie_rating_tv);
        releaseDateTV = findViewById(R.id.release_date_tv);
        descriptionTV = findViewById(R.id.movie_description_tv);
        posterIV = findViewById(R.id.movie_poster_iv);

        Movie movie = intent.getParcelableExtra("movie");

        if (movie == null) {
            closeOnError();
        } else {
            movieTitleTV.setText(movie.getTitle());
            Picasso.get()
                    .load(movie.getPosterPath())
                    .fit()
                    .error(R.drawable.ic_videocam_black_48dp)
                    .placeholder(R.drawable.ic_videocam_black_48dp)
                    .into(posterIV);
            ratingTV.setText(String.valueOf(movie.getVoterAverage()));
            releaseDateTV.setText(movie.getReleaseDate());
            descriptionTV.setText(movie.getOverview());
        }

        getExtraMovieDetails(movie.getId());
    }

    private void getExtraMovieDetails(int id) {



    }

//    private void getMoreDetails(String id) {
//        String reviewQuery = id + File.separator + "reviews";
//        String trailerQuery = id + File.separator + "videos";
//        SearchURLs searchURLs = new SearchURLs(
//                NetworkUtils.buildUrl(reviewQuery, getText(R.string.api_key).toString()),
//                NetworkUtils.buildUrl(trailerQuery, getText(R.string.api_key).toString())
//        );
//        new ReviewsQueryTask().execute(searchURLs);
//    }
//
//    // AsyncTask to perform query
//    public class ReviewsQueryTask extends AsyncTask<SearchURLs, Void, ResultsStrings> {
//        @Override
//        protected ResultsStrings doInBackground(SearchURLs... params) {
//            URL reviewsearchUrl = params[0].reviewSearchUrl;
//            URL trailersearchUrl = params[0].trailerSearchUrl;
//
//            String reviewResults = null;
//            try {
//                reviewResults = NetworkUtils.getResponseFromHttpUrl(reviewsearchUrl);
//                reviewList = JsonUtils.parseReviewsJson(reviewResults);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            String trailerResults = null;
//            try {
//                trailerResults = NetworkUtils.getResponseFromHttpUrl(trailersearchUrl);
//                trailerList = JsonUtils.parseTrailersJson(trailerResults);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            ResultsStrings results = new ResultsStrings(reviewResults,trailerResults);
//
//            return results;
//        }
//
//        @Override
//        protected void onPostExecute(ResultsStrings results) {
//            String searchResults = results.reviewString;
//            if (searchResults != null && !searchResults.equals("")) {
//                reviewList = JsonUtils.parseReviewsJson(searchResults);
//                populateDetails();
//            }
//        }
//    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
    }

}
