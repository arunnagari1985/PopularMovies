package com.example.android.popularmovies.Utilities;

import android.util.Log;

import com.example.android.popularmovies.MovieDetails;

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
}
