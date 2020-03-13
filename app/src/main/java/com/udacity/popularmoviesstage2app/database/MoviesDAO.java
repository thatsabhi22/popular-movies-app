package com.udacity.popularmoviesstage2app.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.udacity.popularmoviesstage2app.models.Movie;

import java.util.List;

@Dao
public interface MoviesDAO {

    @Insert
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM Movies ORDER BY id DESC")
    LiveData<List<Movie>> loadAllMovies();
}
