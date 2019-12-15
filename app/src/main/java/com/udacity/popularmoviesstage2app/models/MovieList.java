package com.udacity.popularmoviesstage2app.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieList implements Parcelable {

    public static final Creator<MovieList> CREATOR = new Creator<MovieList>() {
        @Override
        public MovieList createFromParcel(Parcel in) {
            return new MovieList(in);
        }

        @Override
        public MovieList[] newArray(int size) {
            return new MovieList[size];
        }
    };
    final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
    public String title;
    public String posterPath;
    public String overview;
    public String releaseDate;
    public String voterAverage;

    public MovieList(String title, String posterPath, String releaseDate,
                     String overview, String voterAverage) {
        this.title = title;
        this.posterPath = POSTER_BASE_URL + posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voterAverage = voterAverage;
    }

    protected MovieList(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voterAverage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(voterAverage);
    }
}
