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

    public MovieDetails()
    {
        mMovieID = 0;
        mMoviePosterUrl = null;
        mMovieTitle = "";
        mMovieOverview = "";
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
}
