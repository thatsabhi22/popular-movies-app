package com.udacity.popularmoviesstage2app.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderKt;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.adpaters.MoviesGridAdapter;
import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.utils.MoviesViewModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.udacity.popularmoviesstage2app.utils.QueryUtils.isOnline;

public class MainActivity extends AppCompatActivity {

    /**
     * URL for movies data from the MoviesDB data-set
     */
    private static final String BASE_REQUEST_URL = "http://api.themoviedb.org/3/movie/";
    /**
     * API KEY URL Query for Movies DB API
     */
    private static final String API_KEY_QUERY = "?api_key=";

    private static final int NUM_OF_COLUMNS = 2;
    /**
     * sort_type variable determines the movie types filter
     */
    private static String sort_type = "popular";
    /**
     * API KEY Value URL Query for Movies DB API
     */
    private String API_KEY_QUERY_VALUE;
    private String movies_request_url = "";
    private RecyclerView moviesGridRecyclerView;
    private TextView noInternetTextView;
    private Button retryInternetBtn;
    private MoviesGridAdapter moviesGridAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoviesViewModel moviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_KEY_QUERY_VALUE = getResources().getString(R.string.api_key);

        moviesGridRecyclerView = findViewById(R.id.MoviesGridRecyclerView);
        noInternetTextView = findViewById(R.id.noInternetTV);
        retryInternetBtn = findViewById(R.id.retryInternetBtn);

        initMoviesViewModel();
        setTitle("Popular Movies Stage 2");

        retryInternetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
    }

    private void initMoviesViewModel() {
        moviesViewModel = ViewModelProviders.of(this)
                .get(MoviesViewModel.class);
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

                moviesGridAdapter = new MoviesGridAdapter(this, new ArrayList<Movie>());
                moviesGridRecyclerView.setAdapter(moviesGridAdapter);
                mLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
                moviesGridRecyclerView.setLayoutManager(mLayoutManager);
                movies_request_url = BASE_REQUEST_URL + sort_type + API_KEY_QUERY + API_KEY_QUERY_VALUE;

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
                break;
            case R.id.srt_top_rated:
                sort_type = "top_rated";
                break;
        }
        movies_request_url = BASE_REQUEST_URL + sort_type + API_KEY_QUERY + API_KEY_QUERY_VALUE;
        refreshMovieResults();
        return true;
    }

    public void refreshMovieResults() {
        moviesGridAdapter.setData(new ArrayList<Movie>());
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
