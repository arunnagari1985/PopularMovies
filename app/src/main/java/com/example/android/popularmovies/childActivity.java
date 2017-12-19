package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChildActivity extends AppCompatActivity {

    private TextView mMovieTitle;
    private TextView mMovieOverView;
    private ImageView mMovieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        mMovieTitle = (TextView) findViewById(R.id.movie_name);
        mMovieOverView = (TextView) findViewById(R.id.movie_overview);
        mMovieImage = (ImageView) findViewById(R.id.movie_Image);

        ArrayList<String> movieData = new ArrayList<String>();

        //Get Intent of the activity which started this activity
        Intent intentThatlaunchedThisActivity = getIntent();

        //Using an Array list to pass data
        Bundle data = intentThatlaunchedThisActivity.getExtras();

        //Check if the bundle has MOVIE_ARRAY_LIST key
        if(data.containsKey("MOVIE_ARRAY_LIST"))
        {
            movieData = data.getStringArrayList("MOVIE_ARRAY_LIST");

            //Set Movie title
            mMovieTitle.setText("Movie Title: \n" + movieData.get(0));

            // Display image using Picaso
            Picasso.with(this).load(movieData.get(1)).resize(240,200).into(this.mMovieImage);

            //Set movie overview
            mMovieOverView.setText("Movie Overview: \n" + movieData.get(2));
        }

    }
}
