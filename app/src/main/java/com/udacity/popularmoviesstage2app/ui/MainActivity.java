package com.udacity.popularmoviesstage2app.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmoviesstage2app.R;
import com.udacity.popularmoviesstage2app.adpaters.MoviesGridAdapter;
import com.udacity.popularmoviesstage2app.models.MovieList;
import com.udacity.popularmoviesstage2app.tasks.MovieLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.udacity.popularmoviesstage2app.utils.QueryUtils.isOnline;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<MovieList>> {

    /**
     * URL for movies data from the MoviesDB data-set
     */
    private static final String BASE_REQUEST_URL = "http://api.themoviedb.org/3/movie/";
    /**
     * API KEY URL Query for Movies DB API
     */
    private static final String API_KEY_QUERY = "?api_key=";
    /**
     * Constant value for the movie loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int MOVIE_LOADER_ID = 1;
    private static final int NUM_OF_COLUMNS = 2;
    /**
     * sort_type variable determines the movie types filter
     */
    private static String sort_type = "popular";
    /**
     * API KEY Value URL Query for Movies DB API
     */
    private String API_KEY_QUERY_VALUE;
    LoaderManager loaderManager;
    private String movies_request_url = "";
    private RecyclerView moviesGridRecyclerView;
    private TextView noInternetTextView;
    private Button retryInternetBtn;
    private MoviesGridAdapter moviesGridAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_KEY_QUERY_VALUE = getResources().getString(R.string.api_key);

        moviesGridRecyclerView = findViewById(R.id.MoviesGridRecyclerView);
        noInternetTextView = findViewById(R.id.noInternetTV);
        retryInternetBtn = findViewById(R.id.retryInternetBtn);

        setTitle("Popular Movies Stage 2");

        retryInternetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
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

                moviesGridAdapter = new MoviesGridAdapter(this, new ArrayList<MovieList>());
                moviesGridRecyclerView.setAdapter(moviesGridAdapter);
                mLayoutManager = new GridLayoutManager(this, NUM_OF_COLUMNS);
                moviesGridRecyclerView.setLayoutManager(mLayoutManager);
                movies_request_url = BASE_REQUEST_URL + sort_type + API_KEY_QUERY + API_KEY_QUERY_VALUE;

                // Get a reference to the LoaderManager, in order to interact with loaders.
                loaderManager = getLoaderManager();

                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Loader<List<MovieList>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new MovieLoader(this, movies_request_url);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieList>> loader, List<MovieList> movies) {
        moviesGridAdapter.setData(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<MovieList>> loader) {
        // Loader reset, so we can clear out our existing data.
        moviesGridAdapter.setData(new ArrayList<MovieList>());
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
        moviesGridAdapter.setData(new ArrayList<MovieList>());
        getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
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
