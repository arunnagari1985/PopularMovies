package com.example.android.popularmovies.data;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
/**
 * Created by nagariarun on 2/14/18.
 */

public class MovieDbContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "movies";

    public static final String PATH_MOVIE = "movies//#";
    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class MovieEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final Uri CONTENT_URI_MOVIE =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static Uri buildMovieUri(Long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        // Task table and column names
        public static final String TABLE_NAME = "movies";

        // TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_POSTER_URL = "movie_poster_url";
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

    }

}
