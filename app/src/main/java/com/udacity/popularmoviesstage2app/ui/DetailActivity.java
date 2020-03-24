package com.udacity.popularmoviesstage2app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.models.Movie;

public class DetailActivity extends AppCompatActivity {

    RecyclerView trailersGridRecyclerView, reviewsGridRecyclerView;
    TextView ratingTV, releaseDateTV, descriptionTV, movie_title_tv;
    ImageView posterIV;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setBackgroundResource(R.drawable.backdrop);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

//        trailersGridRecyclerView = findViewById(R.id.trailersGridRecyclerView);
//        reviewsGridRecyclerView = findViewById(R.id.reviewsGridRecyclerView);
        ratingTV = findViewById(R.id.movie_rating_tv);
        releaseDateTV = findViewById(R.id.release_date_tv);
        descriptionTV = findViewById(R.id.movie_description_tv);
        posterIV = findViewById(R.id.movie_poster_iv);
        movie_title_tv = findViewById(R.id.movie_title_tv);

        Movie movie = intent.getParcelableExtra("movie");

        if (movie == null) {
            closeOnError();
        } else {
            setTitle(movie.getTitle());
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
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}
