package com.example.android.popularmovies.Utilities;

import android.util.Log;

import com.example.android.popularmovies.MovieDetails;
import com.example.android.popularmovies.MovieReviews;
import com.example.android.popularmovies.MovieTrailers;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.HttpURLConnection;

import static android.content.ContentValues.TAG;

/**
 * Created by nagariarun on 12/13/17.
 */

public final class JsonUtils {

    /*
    * This meathod is used to parse JSON data and gather required information
    */
    public static MovieDetails[] getDataFromMdbJson(String jsonString) throws JSONException
    {
        MovieDetails[] parsedMovieDBData = null;

        if(jsonString == null)
            return null;

        JSONObject movieListJson;
        movieListJson = new JSONObject(jsonString);

        JSONArray results = movieListJson.getJSONArray("results");
        parsedMovieDBData = new MovieDetails[results.length()];


        //Iterate through all the returned results to gather required information
        for(int i=0; i<results.length(); i++)
        {
            JSONObject temp = results.getJSONObject(i);
            MovieDetails movieD = new MovieDetails();
            movieD.setmMoviePosterUrl("http://image.tmdb.org/t/p/w185/" + temp.getString("poster_path"));
            movieD.setmMovieTitle(temp.getString("title"));
            movieD.setmMovieOverview(temp.getString("overview"));
            movieD.setmMovieID(temp.getInt("id"));
            parsedMovieDBData[i] = movieD;
        }

        return parsedMovieDBData;
    }
    //Method to get review data from Json
    public static MovieReviews[] getMovieReviewDataFromMdbJson(String jsonString) throws JSONException
    {
      MovieReviews[] parsedMovieReviews = null;

        if(jsonString == null)
            return null;

        JSONObject movieReviewListJson;
        movieReviewListJson = new JSONObject(jsonString);

        JSONArray results = movieReviewListJson.getJSONArray("results");
        parsedMovieReviews = new MovieReviews[results.length()];

        //Iterate through all the returned results to gather review information

        for(int i=0; i<results.length(); i++)
        {
            JSONObject temp = results.getJSONObject(i);
            MovieReviews mReview = new MovieReviews();
            mReview.setAuthor(temp.getString("author"));
            mReview.setContent(temp.getString("content"));
            parsedMovieReviews[i] = mReview;
        }

        return parsedMovieReviews;
    }
    //Method to get trailer data from Json
    public static MovieTrailers[] getMovieTrailerDataFromMdbJson(String jsonString) throws JSONException
    {
        MovieTrailers[] parsedMovieTrailer = null;

        if(jsonString == null)
            return null;

        JSONObject movieTrailerListJson;
        movieTrailerListJson = new JSONObject(jsonString);

        JSONArray results = movieTrailerListJson.getJSONArray("results");
        parsedMovieTrailer = new MovieTrailers[results.length()];

        for(int i=0; i<results.length(); i++)
        {
            JSONObject temp = results.getJSONObject(i);
            MovieTrailers mTrailer = new MovieTrailers();
            mTrailer.setId(temp.getString("id"));
            mTrailer.setKey(temp.getString("key"));
            mTrailer.setPosterUrl("https://img.youtube.com/vi/" + temp.getString("key") + "/mqdefault.jpg");
            mTrailer.setTrailerUrl("https://www.youtube.com/watch?v=" + temp.getString("key"));
            parsedMovieTrailer[i] = mTrailer;
        }

        return parsedMovieTrailer;
    }

}
