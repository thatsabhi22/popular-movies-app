package com.udacity.popularmoviesstage2app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.viewmodels.MovieDetailsViewModel;

public class DetailActivity extends AppCompatActivity {

    MovieDetailsViewModel movieDetailsViewModel;
    RecyclerView trailersGridRecyclerView, reviewsGridRecyclerView;
    TextView ratingTV, releaseDateTV, descriptionTV, movie_title_tv;
    ImageView posterIV, backdropIV;
    AppBarLayout appBarLayout;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setBackgroundResource(R.drawable.backdrop);

        movieDetailsViewModel = ViewModelProviders.of(this)
                .get(MovieDetailsViewModel.class);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        trailersGridRecyclerView = findViewById(R.id.trailersGridRecyclerView);
        reviewsGridRecyclerView = findViewById(R.id.reviewsGridRecyclerView);
        ratingTV = findViewById(R.id.movie_rating_tv);
        releaseDateTV = findViewById(R.id.release_date_tv);
        descriptionTV = findViewById(R.id.movie_description_tv);
        posterIV = findViewById(R.id.movie_poster_iv);
        movie_title_tv = findViewById(R.id.movie_title_tv);
        backdropIV = findViewById(R.id.backdrop_poster_iv);

        Movie movie = intent.getParcelableExtra("movie");

        if (movie == null) {
            closeOnError();
        } else {
            setTitle(movie.getTitle());
            Picasso.get()
                    .load(movie.getBackdropPath())
                    .into(backdropIV);
            backdropIV.setVisibility(View.VISIBLE);

            Picasso.get()
                    .load(movie.getPosterPath())
                    .fit()
                    .error(R.drawable.ic_videocam_black_48dp)
                    .placeholder(R.drawable.ic_videocam_black_48dp)
                    .into(posterIV);

            movie_title_tv.setText(movie.getTitle());
            ratingTV.setText(String.valueOf(movie.getVoterAverage()));
            releaseDateTV.setText(movie.getReleaseDate().trim());
            descriptionTV.setText(movie.getOverview());

            movieDetailsViewModel.getMovieTrailers(String.valueOf(movie.getId()));
            movieDetailsViewModel.getMovieReviews(String.valueOf(movie.getId()));
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}
