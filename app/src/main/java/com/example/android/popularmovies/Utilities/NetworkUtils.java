package com.example.android.popularmovies.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by nagariarun on 12/12/17.
 */

public class NetworkUtils {

    final static String MOVIE_DB_BASE_URL =
            "http://api.themoviedb.org/3/movie/";
    /*
     * The sort field. By popularity and top rated
     * Default: is by popularity
     */

    final static String sortByPopularity = "popular";
    final static String sortByTopRated = "top_rated";
    final static String API_KEY = "?api_key=845d8e429f3a3b9b3ca29de84d55cbb1";

    /**
     * Builds the URL used to query MovieDB.
     * sortBy input is used to make a query argument to url
     * @return The URL to use to query the MovieDB.
     */
    public static URL buildUrl(int sortBy)
    {
        String movieURL = null;

        switch(sortBy)
        {
            case 1:
                movieURL = MOVIE_DB_BASE_URL + sortByPopularity + API_KEY;
                break;
            case 2:
                movieURL = MOVIE_DB_BASE_URL + sortByTopRated + API_KEY;
                break;
            default:
                movieURL = MOVIE_DB_BASE_URL + sortByPopularity + API_KEY;
        }

        Uri builtUri = Uri.parse(movieURL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildReviewUrl(int movieId)
    {
        String movieURL = null;
        movieURL = MOVIE_DB_BASE_URL + movieId + "/reviews" + API_KEY;

        Uri builtUri = Uri.parse(movieURL).buildUpon().build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTrailerUrl(int movieId)
    {
        String movieURL = null;
        movieURL = MOVIE_DB_BASE_URL + movieId + "/videos" + API_KEY;

        Uri builtUri = Uri.parse(movieURL).buildUpon().build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = null;

        Log.v(TAG,"Reached Network Utils");
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(3000);
            urlConnection.setConnectTimeout(300);
            InputStream in = urlConnection.getInputStream();
            Log.v(TAG,"Reached try block in Network Utils");
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally
        {
            urlConnection.disconnect();
        }
    }
}
