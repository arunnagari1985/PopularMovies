package com.example.android.popularmovies;

/**
 * Created by nagariarun on 12/10/17.
 */

public class MovieDetails
{
    private String mMovieTitle;
    private String mMovieOverview;
    private int mMovieID;
    private String mMoviePosterUrl;
    private String mMovieRating;
    private String mMovieReleaseDate;

    public MovieDetails()
    {
        //Add Movie ID
        mMovieID = 0;
        mMoviePosterUrl = null;
        mMovieTitle = "";
        mMovieOverview = "";
        mMovieReleaseDate = "";
        mMovieRating = "";
    }

    public void setmMoviePosterUrl(String imgUrl)
    {
        mMoviePosterUrl = imgUrl;
    }
    public void setmMovieTitle(String title)
    {
        mMovieTitle = title;
    }

    public void setmMovieOverview(String overview)
    {
        mMovieOverview = overview;
    }

    public void setmMovieID(int id)
    {
        mMovieID = id;
    }

    public String getmMoviePosterUrl()
    {
      return mMoviePosterUrl;
    }

    public String getmMovieTitle()
    {
        return mMovieTitle;
    }

    public String getmMovieOverview()
    {
        return mMovieOverview;
    }

    public int getmMovieID()
    {
        return mMovieID;
    }

    public String getMovieRating()
    {
        return mMovieRating;
    }

    public void setMovieRating(String rating)
    {
        mMovieRating = rating;
    }

    public String getMovieReleaseDate()
    {
        return mMovieReleaseDate;
    }

    public void setMovieReleaseDate(String date)
    {
      mMovieReleaseDate = date;
    }
}

