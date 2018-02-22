package com.example.android.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Utilities.JsonUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;
import com.example.android.popularmovies.data.MovieDbContract;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.id.input;
import static com.example.android.popularmovies.R.id.tv_error_message_display;

public class ChildActivity extends AppCompatActivity implements MovieTrailerAdapter.ListItemClickListener{

    private static final String TAG = "ChildActivity";

    private TextView mMovieTitle;
    private TextView mMovieOverView;
    private TextView mMovieReleaseDate;
    private TextView mMovieRating;

    private ImageView mMovieImage;

    private MovieReviewAdapter mMovieReviewAdapter;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    private TextView mErrorMessageDisplay;

    private TextView mReviewTitle;
    private TextView mTrailerTitle;
    private RecyclerView mRecyclerViewReviews;
    private RecyclerView mRecyclerViewTrailers;

    private int mMovieID;
    private String mMovie_Title;
    private String mMovie_Overview;
    private String mMovie_PosterUrl;
    private String mMovie_Rating;
    private String mMovie_ReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message);
        mMovieTitle = (TextView) findViewById(R.id.movie_name);
        mMovieOverView = (TextView) findViewById(R.id.movie_overview);
        mMovieImage = (ImageView) findViewById(R.id.movie_Image);
        mMovieRating = (TextView) findViewById(R.id.movie_rating);
        mMovieReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        mRecyclerViewReviews = (RecyclerView) findViewById(R.id.movies_reviews);
        mReviewTitle = (TextView) findViewById(R.id.tv_reviews_title);
        mRecyclerViewTrailers = (RecyclerView) findViewById(R.id.movies_trailers);
        mTrailerTitle = (TextView) findViewById(R.id.tv_trailers_title);
        mMovieID = 0;
        ArrayList<String> movieData = new ArrayList<String>();

        //Get Intent of the activity which started this activity
        Intent intentThatlaunchedThisActivity = getIntent();

        //Using an Array list to pass data
        Bundle data = intentThatlaunchedThisActivity.getExtras();

        //Check if the bundle has MOVIE_ARRAY_LIST key
        if(data.containsKey("MOVIE_ARRAY_LIST"))
        {
            movieData = data.getStringArrayList("MOVIE_ARRAY_LIST");
            mMovie_Title = movieData.get(0);
            //Set Movie title
            mMovieTitle.setText("Movie Title: \n" + mMovie_Title);

            mMovie_PosterUrl = movieData.get(1);
            // Display image using Picaso
            Picasso.with(this).load(mMovie_PosterUrl).resize(240,200).into(this.mMovieImage);


            mMovie_Overview = movieData.get(2);
            //Set movie overview
            mMovieOverView.setText("Movie Overview: \n" + mMovie_Overview);

            mMovie_Rating = movieData.get(3);
            //Set movie rating
            mMovieRating.setText("Rating: " + mMovie_Rating);

            mMovie_ReleaseDate = movieData.get(4);
            //Set movie Release date
            mMovieReleaseDate.setText("Release Date: " + mMovie_ReleaseDate);

        }

        if(data.containsKey("MOVIE_ID"))
        {
            mMovieID = data.getInt("MOVIE_ID");
        }

        initializeView();
        loadReviewData();

    }



    private void loadReviewData()
    {
        new FetchMovieReviewDataTask().execute(mMovieID);
        new FetchMovieTrailerDataTask().execute(mMovieID);
    }

    // Initialize view
    private void initializeView()
    {
        mRecyclerViewReviews.setHasFixedSize(true);
        mRecyclerViewTrailers.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManagerReviews = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager layoutManagerTrailers = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

        mRecyclerViewReviews.setLayoutManager(layoutManagerReviews);
        mRecyclerViewTrailers.setLayoutManager(layoutManagerTrailers);

        mMovieReviewAdapter = new MovieReviewAdapter(this);
        mMovieTrailerAdapter = new MovieTrailerAdapter(this,this);

        mRecyclerViewReviews.setAdapter(mMovieReviewAdapter);
        mRecyclerViewTrailers.setAdapter(mMovieTrailerAdapter);
    }


    /**
     * This method will make the error message visible and hide the weather
     * View.
     */
    private void showReviewsErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerViewReviews.setVisibility(View.INVISIBLE);
        mRecyclerViewTrailers.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     */
    private void showReviewsData() {
        /* Show the data */
        mRecyclerViewReviews.setVisibility(View.VISIBLE);
        /* Then, hide the error */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    //This method will show trailer data
    private void showTrailerData()
    {
        mRecyclerViewTrailers.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }


    //Async Task to fetch review data
    public class FetchMovieReviewDataTask extends AsyncTask<Integer, Void, MovieReviews[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MovieReviews[] doInBackground(Integer... params) {

            if(params.length == 0)
                return null;

            URL movieReviewsUrl = NetworkUtils.buildReviewUrl(params[0]);

            try
            {
                String jsonMovieReviewData = NetworkUtils.getResponseFromHttpUrl(movieReviewsUrl);
                MovieReviews[] movieReviewData = JsonUtils.getMovieReviewDataFromMdbJson(jsonMovieReviewData);

                return movieReviewData;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieReviews[] movieReviews)
        {
            super.onPostExecute(movieReviews);

            if(movieReviews != null && !movieReviews.equals(""))
            {
                showReviewsData();
                ArrayList<MovieReviews> movieReviewList = new ArrayList<MovieReviews>();

                if(movieReviews.length > 0)
                    mReviewTitle.setVisibility(View.VISIBLE);
                else
                    mReviewTitle.setVisibility(View.INVISIBLE);

                for (int i = 0; i < movieReviews.length; i++) {
                    movieReviewList.add(movieReviews[i]);
                }

                mMovieReviewAdapter.setAdapterData(movieReviewList);
                mRecyclerViewReviews.setAdapter(mMovieReviewAdapter);
            }
            else
            {
                if(movieReviews == null)
                 Log.v(TAG,"MovieReview is null");
                else if(movieReviews.equals(""))
                    Log.v(TAG,"MovieReview is empty ");
                else
                    Log.v(TAG,"movie reviews has data ");

                showReviewsErrorMessage();
            }
        }
    }

    //This is an class for fetching trailer data in background
    public class FetchMovieTrailerDataTask extends AsyncTask<Integer, Void, MovieTrailers[]>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MovieTrailers[] doInBackground(Integer... params) {

            if(params.length == 0)
                return null;

            URL movieTrailersUrl = NetworkUtils.buildTrailerUrl(params[0]);
            Log.v(TAG,"movie Trailer url: " + movieTrailersUrl.toString());
            try
            {
                String jsonMovieTrailerData = NetworkUtils.getResponseFromHttpUrl(movieTrailersUrl);
                MovieTrailers[] movieTrailerData = JsonUtils.getMovieTrailerDataFromMdbJson(jsonMovieTrailerData);

                return movieTrailerData;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(MovieTrailers[] movieTrailers)
        {
            super.onPostExecute(movieTrailers);

            if(movieTrailers != null && !movieTrailers.equals(""))
            {
                showTrailerData();
                ArrayList<MovieTrailers> movieTrailerList = new ArrayList<MovieTrailers>();

                if(movieTrailers.length > 0)
                    mTrailerTitle.setVisibility(View.VISIBLE);
                else
                    mTrailerTitle.setVisibility(View.INVISIBLE);

                for (int i = 0; i < movieTrailers.length; i++) {
                    movieTrailerList.add(movieTrailers[i]);
                }

                mMovieTrailerAdapter.setAdapterData(movieTrailerList);
                mRecyclerViewTrailers.setAdapter(mMovieTrailerAdapter);
            }
            else
            {
                showReviewsErrorMessage();
            }
        }


    }


    @Override
    public void onListItemClicked(int itemClickedIndex)
    {
        Context context = this;

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mMovieTrailerAdapter.getMovieTrailerDetails(itemClickedIndex).getKey()));

        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(mMovieTrailerAdapter.getMovieTrailerDetails(itemClickedIndex).getTrailerUrl()));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    //This method is used to show the star button for making a movie as favorite
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.child_activity_menu,menu);
        return true;
    }

    //This method responds to star button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int menuItemSelected = item.getItemId();

        //Based on Menu item selected load data again
        switch(menuItemSelected)
        {


            case R.id.action_favorite :
                String[] array = {String.valueOf(mMovieID)};
                Cursor c = getContentResolver().query(MovieDbContract.MovieEntry.buildMovieUri((long)mMovieID),
                        null,
                        MovieDbContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                        array,
                        null);

                Log.v(TAG,"Number of entries received: " + String.valueOf(c.getCount()));

                if(c.getCount() == 1)
                {
                    int count = 0;
                    //Toast.makeText(this, "Clicked favorite already exists", Toast.LENGTH_SHORT).show();
                    count = getContentResolver().delete(MovieDbContract.MovieEntry.buildMovieUri((long) mMovieID),
                            MovieDbContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                            array);
                    Log.v(TAG,"NUmber of items deleted: " + String.valueOf(count));
                }
                    else
                 {
                    ContentValues contentValues = new ContentValues();
                    // Put the movie data into the ContentValues
                    contentValues.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_TITLE, mMovie_Title);
                    contentValues.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_ID, mMovieID);
                    contentValues.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_POSTER_URL, mMovie_PosterUrl);
                    contentValues.put(MovieDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, mMovie_Overview);
                    // Insert the content values via a ContentResolver
                    Uri uri = getContentResolver().insert(MovieDbContract.MovieEntry.CONTENT_URI, contentValues);
                }

                break;
            default:
        };


        return super.onOptionsItemSelected(item);
    }

}
