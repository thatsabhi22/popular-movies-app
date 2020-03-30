package com.udacity.popularmoviesstage2app.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.adpaters.MoviesGridAdapter;
import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.viewmodels.MoviesListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.udacity.popularmoviesstage2app.utils.QueryUtils.isOnline;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_OF_COLUMNS = 2;
    String sort_type = "popular";
    Observer<List<Movie>> movieObserver;
    ProgressBar mProgressBar;
    /**
     * API KEY Value URL Query for Movies DB API
     */
    private RecyclerView moviesGridRecyclerView;
    private TextView noInternetTextView;
    private Button retryInternetBtn;
    private MoviesGridAdapter moviesGridAdapter;
    private MoviesListViewModel moviesListViewModel;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesGridRecyclerView = findViewById(R.id.MoviesGridRecyclerView);
        noInternetTextView = findViewById(R.id.noInternetTV);
        retryInternetBtn = findViewById(R.id.retryInternetBtn);
        mProgressBar = findViewById(R.id.progress_bar);

        initMoviesViewModel();
        movieList = new ArrayList<>();
        setTitle("Popular Movies Stage 2");

        retryInternetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
    }

    private void initMoviesViewModel() {
        movieObserver =
                movies -> {
                    movieList.clear();
                    movieList.addAll(movies);

                    if (moviesGridAdapter == null) {
                        moviesGridAdapter = new
                                MoviesGridAdapter(MainActivity.this, movieList);
                    } else {
                        moviesGridAdapter.notifyDataSetChanged();
                    }
                };
        moviesListViewModel = ViewModelProviders.of(this)
                .get(MoviesListViewModel.class);
        moviesListViewModel.getMovieList(sort_type).observe(MainActivity.this, movieObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            boolean isOffline = new CheckOnlineStatus().execute().get();
            if (isOffline) {
                noInternetTextView.setVisibility(View.VISIBLE);
                retryInternetBtn.setVisibility(View.VISIBLE);
                moviesGridRecyclerView.setVisibility(View.GONE);
            } else {
                noInternetTextView.setVisibility(View.GONE);
                retryInternetBtn.setVisibility(View.GONE);
                moviesGridRecyclerView.setVisibility(View.VISIBLE);

                moviesGridAdapter = new MoviesGridAdapter(this, movieList);
                moviesGridRecyclerView.setAdapter(moviesGridAdapter);
                RecyclerView.LayoutManager mLayoutManager
                        = new GridLayoutManager(this, NUM_OF_COLUMNS);
                moviesGridRecyclerView.setLayoutManager(mLayoutManager);
                moviesGridRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.srt_pprty:
                sort_type = "popular";
                moviesListViewModel.getMovieList(sort_type)
                        .observe(MainActivity.this, movieObserver);
                break;
            case R.id.srt_top_rated:
                sort_type = "top_rated";
                moviesListViewModel.getMovieList(sort_type)
                        .observe(MainActivity.this, movieObserver);
                break;
            case R.id.srt_favorites:
                sort_type = "favorites";
                moviesListViewModel.getMovieListFromDB()
                        .observe(MainActivity.this, movieObserver);
                break;
        }
        return true;
    }

    public static class CheckOnlineStatus extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            //This is a background thread, when it finishes executing will return the result from your function.
            Boolean isOffline;
            isOffline = isOnline();
            return isOffline;
        }
    }
}
