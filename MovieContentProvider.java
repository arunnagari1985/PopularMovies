package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import static android.R.attr.id;
import static android.content.ContentValues.TAG;
import static com.example.android.popularmovies.data.MovieDbContract.MovieEntry.TABLE_NAME;

/**
 * Created by nagariarun on 2/15/18.
 */

public class MovieContentProvider extends ContentProvider {

    //An integer constant for a directory of movies
    public static final int MOVIES = 100;

    public static final int MOVIE_WITH_ID = 101;
    // Member variable for a MovieDbHelper that's initialized in the onCreate() method
    private MovieDbHelper mMovieDbHelper;

    private static UriMatcher sUriMatcher = buildUriMatcher();

    /* initialize a DbHelper to gain access to it.
     */
    @Override
    public boolean onCreate() {

        Context context = getContext();
        mMovieDbHelper = new MovieDbHelper(context);
        return true;
    }


    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //Add Uri match for directory of movies
        uriMatcher.addURI(MovieDbContract.AUTHORITY,MovieDbContract.PATH_MOVIES,MOVIES);

        //Add Uri match for single movie
        uriMatcher.addURI(MovieDbContract.AUTHORITY,MovieDbContract.PATH_MOVIES + "/#",MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        // Get access to the movie database (to write new data to)
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        //URI matching code to identify the match for the movies directory
        int match = sUriMatcher.match(uri);

        Log.v(TAG,"This is Insert: " + uri.toString());
        Uri returnUri; // URI to be returned

        switch (match) {
            case MOVIES:
                // Insert new values into the database
                // Inserting values into tasks table
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    Log.v(TAG,"Reached Success in Movie Database");
                    returnUri = ContentUris.withAppendedId(MovieDbContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            //Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();

        // URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);

        Log.v(TAG,"This is Query: " + uri.toString() + " " + match);

        Cursor retCursor;

        // Query for the tasks directory and a default case
        switch (match) {
            // Query for the tasks directory
            case MOVIES:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_WITH_ID:

                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI

                // Construct a query as you would normally, passing in the selection/args
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case MOVIE_WITH_ID:
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(TABLE_NAME, selection, new String[]{selectionArgs[0]});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //Notify the resolver of a change, return the number of items deleted
        if (tasksDeleted != 0) {
            // Task deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


}
