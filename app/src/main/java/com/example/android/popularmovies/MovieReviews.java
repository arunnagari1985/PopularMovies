package com.example.android.popularmovies;

/**
 * Created by nagariarun on 2/6/18.
 */

public class MovieReviews
{
    private String mContent;
    private String mAuthor;

    public MovieReviews()
    {
        mContent = "";
        mAuthor = "";
    }

    public MovieReviews(String content, String author)
    {
        mContent = content;
        mAuthor = author;
    }

    public void setContent(String content)
    {
        mContent = content;
    }

    public void setAuthor(String author)
    {
        mAuthor = author;
    }

    public String getContent()
    {
        return mContent;
    }

    public String getAuthor()
    {
        return mAuthor;
    }
}
