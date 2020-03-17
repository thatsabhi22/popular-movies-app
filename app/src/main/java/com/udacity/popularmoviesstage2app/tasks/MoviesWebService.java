package com.udacity.popularmoviesstage2app.tasks;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MoviesWebService {
    /**
     * URL for movies data from the MoviesDB data-set
     */
    String BASE_REQUEST_URL = "http://api.themoviedb.org/3/movie/";

    /**
     * sort_type variable determines the movie types filter
     */
    String SORT_TYPE = "popular";

    OkHttpClient.Builder okhttpclientbuilder = new OkHttpClient.Builder();

    HttpLoggingInterceptor httplogger = new HttpLoggingInterceptor();

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BASE_REQUEST_URL)
            .client(okhttpclientbuilder
                    .addInterceptor(httplogger.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build())
            .build();

    @Headers("Content-Type: text/html")
    @GET(SORT_TYPE)
    Call<String> getMovies(@Query("api_key") String value);
}
