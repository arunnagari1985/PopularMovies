package com.example.android.popularmovies;

import static android.R.attr.name;

/**
 * Created by nagariarun on 2/6/18.
 */

public class MovieTrailers {

    private String mId;
    private String mKey;
    private String mTrailerUrl;
    private String mPosterUrl;
    private String mSite;

    public MovieTrailers(String id, String key, String trailerUrl, String site, String posterUrl)
    {
        mId = id;
        mKey = key;
        mTrailerUrl = trailerUrl;
        mSite = site;
        mPosterUrl = posterUrl;
    }

    public MovieTrailers()
    {
        mId = "";
        mKey = "";
        mTrailerUrl = "";
        mPosterUrl = "";
        mSite = "";
    }

    public void setId(String id)
    {
       mId = id;
    }


    public void setKey(String key)
    {
        mKey = key;
    }

    public void setTrailerUrl(String trailerUrl)
    {
        mTrailerUrl = trailerUrl;
    }

    public void setSite(String site)
    {
        mSite = site;
    }

    public void setPosterUrl(String posterUrl)
    {
        mPosterUrl = posterUrl;
    }


    public String getId()
    {
        return mId;
    }


    public String getKey()
    {
        return mKey;
    }

    public String getTrailerUrl()
    {
        return mTrailerUrl;
    }

    public String getSite()
    {
        return mSite;
    }

    public String getPosterUrl()
    {
        return mPosterUrl;
    }

}
