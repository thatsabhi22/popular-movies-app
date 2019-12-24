package com.udacity.popularmoviesstage2app.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private int id;
    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String voterAverage;

    public Movie(String title, String posterPath, String releaseDate,
                 String overview, String voterAverage) {

        String poster_base_url = "https://image.tmdb.org/t/p/w185";
        this.title = title;
        this.posterPath = poster_base_url + posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voterAverage = voterAverage;
    }

    public Movie(int id, String title, String posterPath, String overview,
                 String releaseDate, String voterAverage) {
        String poster_base_url = "https://image.tmdb.org/t/p/w185";
        this.id = id;
        this.title = title;
        this.posterPath = poster_base_url + posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voterAverage = voterAverage;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
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
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(voterAverage);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoterAverage() {
        return voterAverage;
    }
}
