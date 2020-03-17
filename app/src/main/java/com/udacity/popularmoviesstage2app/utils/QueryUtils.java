package com.udacity.popularmoviesstage2app.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.popularmoviesstage2app.models.Movie;
import com.udacity.popularmoviesstage2app.models.Review;
import com.udacity.popularmoviesstage2app.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving movies data from Movies DB.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Movies dataset and return an {@link Movie} object to represent a single movie.
     */
    public static ArrayList<Movie> fetchMoviesData(String requestUrl) {
        String jsonResponse = fetchData(requestUrl);
        // Return the {@link Event}
        return extractMoviesFromJson(jsonResponse);
    }

    /**
     * Query the Movie Reviews dataset and return an {@link Review} object to represent a single movie.
     */
    public static ArrayList<Review> fetchMovieReviewsData(String requestUrl) {
        String jsonResponse = fetchData(requestUrl);
        // Return the {@link Event}
        return extractMovieReviewsFromJson(jsonResponse);
    }

    /**
     * Query the Movies dataset and return an {@link Trailer} object to represent a single movie.
     */
    public static ArrayList<Trailer> fetchMovieTrailerData(String requestUrl) {
        String jsonResponse = fetchData(requestUrl);
        // Return the {@link Event}
        return extractMovieTrailersFromJson(jsonResponse);
    }

    private static String fetchData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Return an {@link Movie} object by parsing out information
     * about the first movie from the input moviesJSON string.
     */
    public static ArrayList<Movie> extractMoviesFromJson(String moviesJSON) {
        ArrayList<Movie> movies = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(moviesJSON);
            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");

            // If there are results in the features array
            if (moviesArray.length() > 0) {
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    int id = movie.optInt("id");
                    String posterPath = movie.optString("poster_path");
                    String title = movie.optString("title");
                    String releaseDate = movie.optString("release_date");
                    String overview = movie.optString("overview");
                    Double voterAverage = movie.optDouble("vote_average");
                    String backdropPath = movie.optString("backdrop_path");

                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy");
                    String MovieYear = dateFormat.format(date1);

                    String voterAverageStr = voterAverage + "/10";

                    Movie movieObject = new Movie(id, title, posterPath, MovieYear, overview,
                            voterAverageStr,backdropPath);
                    movies.add(movieObject);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the movies JSON results", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movies;
    }


    /**
     * Return an {@link Review} object by parsing out information
     * about the Reviews from the input reviewsJSON string.
     */
    private static ArrayList<Review> extractMovieReviewsFromJson(String reviewsJSON) {

        return null;

    }

    /**
     * Return an {@link Trailer} object by parsing out information
     * about the first movie from the input trailersJSON string.
     */
    private static ArrayList<Trailer> extractMovieTrailersFromJson(String trailersJSON) {

        return null;
    }

    /**
     * No Network Condition handled based on link below
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     */

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return false;
        } catch (IOException e) {
            return true;
        }
    }
}