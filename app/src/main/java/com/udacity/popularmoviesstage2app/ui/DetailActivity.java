package com.udacity.popularmoviesstage2app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.adpaters.TrailerAdapter;
import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.models.Trailer;
import com.udacity.popularmoviesstage2app.viewmodels.MovieDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    MovieDetailsViewModel movieDetailsViewModel;
    RecyclerView trailersRecyclerView, reviewsGridRecyclerView;
    TextView ratingTV, releaseDateTV, descriptionTV, movie_title_tv;
    ImageView posterIV, backdropIV;
    AppBarLayout appBarLayout;
    Observer<List<Trailer>> trailerObserver;
    TrailerAdapter trailerAdapter;
    private List<Trailer> trailerList;

//    private Observer<List<Trailer>> trailerObserver = trailer -> {
//        if (trailer != null) {
//            if (trailer.size() > 0) {
//                trailersRecyclerView.setVisibility(View.VISIBLE);
//            }
//            trailerAdapter.swapMovies(trailer);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setBackgroundResource(R.drawable.backdrop);

        initDetailViewModel();
        trailerList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        trailersRecyclerView = findViewById(R.id.trailersRecyclerView);
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

            movieDetailsViewModel
                    .getMovieTrailers(String.valueOf(movie.getId()))
                    .observe(this, trailerObserver);
            movieDetailsViewModel
                    .getMovieReviews(String.valueOf(movie.getId()));
            //.observe(this,reviewObserver);;

            trailerAdapter = new TrailerAdapter(this, trailerList);
            trailersRecyclerView.setAdapter(trailerAdapter);

            RecyclerView.LayoutManager trailerLayoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            trailersRecyclerView.setLayoutManager(trailerLayoutManager);
        }
    }

    private void initDetailViewModel() {
        trailerObserver =
                trailers -> {
                    trailerList.clear();
                    trailerList.addAll(trailers);

                    if (trailerAdapter == null) {
                        trailerAdapter = new
                                TrailerAdapter(DetailActivity.this, trailerList);
                    } else {
                        trailerAdapter.notifyDataSetChanged();
                    }
                };
        movieDetailsViewModel = ViewModelProviders.of(this)
                .get(MovieDetailsViewModel.class);
    }

//    private Observer<List<Review>> reviewObserver = reviews -> {
//        if (reviews != null) {
//
//            if (reviews.size() > 0) {
//                reviewTV.setVisibility(View.VISIBLE);
//                reviewRecycler.setVisibility(View.VISIBLE);
//            }
//
//            reviewAdapter.swapMovies(reviews);
//        }
//    };

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}
