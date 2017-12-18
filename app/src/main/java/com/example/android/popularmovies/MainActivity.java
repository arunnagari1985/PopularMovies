package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Utilities.JsonUtils;
import com.example.android.popularmovies.Utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{

    private static final String TAG = "MainActivity";

    private final int SORT_BY_POPULARITY = 1;

    private final int SORT_BY_TOP_RATING = 2;

    private MovieAdapter mMovieAdapter;

    private RecyclerView mRecyclerView;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get reference to recycle view, progress bar and Text View
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        //Initialize the view
        initializeView();

        //Load movie data sorted by Popularity
        loadData(SORT_BY_POPULARITY);


    }
    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     */
    private void showMovieData()
    {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        /* Then, make sure the movie data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    //Start background Async task
    private void loadData(int data)
    {
        new FetchMovieDataTask().execute(data);
    }

    private void initializeView()
    {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mMovieAdapter = new MovieAdapter(this, this);
        mRecyclerView.setAdapter(mMovieAdapter);

    }



    public class FetchMovieDataTask extends AsyncTask<Integer, Void, MovieDetails[]>
    {
        //Set ProgressBar to visible
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        //This function includes all the back ground tasks
        @Override
        protected MovieDetails[] doInBackground(Integer... params) {

            if(params.length == 0)
                return null;

            URL sortedUrl = NetworkUtils.buildUrl(params[0]);

            Log.v(TAG,"Obtained Url is " + sortedUrl.toString());
            try
            {
              String jsonMovieData = NetworkUtils.getResponseFromHttpUrl(sortedUrl);
              MovieDetails[] simpleUrls = JsonUtils.getDataFromMdbJson(jsonMovieData);
              return simpleUrls;
            }
            catch(Exception e)
            {
               e.printStackTrace();
               return null;
            }
        }

        // This function is executed after back ground task is completed
        @Override
        protected void onPostExecute(MovieDetails[] movieStrings)
        {
            super.onPostExecute(movieStrings);

            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if(movieStrings != null && !movieStrings.equals(""))
            {
                showMovieData();
                ArrayList<MovieDetails> movieList = new ArrayList<MovieDetails>();

                for (int i = 0; i < movieStrings.length; i++) {
                    movieList.add(movieStrings[i]);
                }

                mMovieAdapter.setAdapterData(movieList);
                mRecyclerView.setAdapter(mMovieAdapter);
            }
            else
            {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                showErrorMessage();
            }
        }
    }

    //Inflate your menu resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int menuItemSelected = item.getItemId();

        //Based on Menu item selected load data again
        switch(menuItemSelected)
        {
            case R.id.search_by_pop :
                loadData(SORT_BY_POPULARITY);
                break;
            case R.id.search_by_top :
                loadData(SORT_BY_TOP_RATING);
                break;
            default:
        };


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClicked(int itemClickedIndex)
    {
        Context context = this;

        //Destination Activity to be launched
        Class destinationContext = childActivity.class;

        Intent intent = new Intent(this, destinationContext);

        //Build an array list to be sent across to new activity
        String movieTitle = mMovieAdapter.getMovieDetails(itemClickedIndex).getmMovieTitle();
        String moviePoster = mMovieAdapter.getMovieDetails(itemClickedIndex).getmMoviePosterUrl();
        String movieOverView = mMovieAdapter.getMovieDetails(itemClickedIndex).getmMovieOverview();

        //Create an array list to store movie information
        ArrayList<String> movieInfo = new ArrayList<String>();
        movieInfo.add(movieTitle);
        movieInfo.add(moviePoster);
        movieInfo.add(movieOverView);

        //Create a bundle object to package array list
        Bundle movieBundle = new Bundle();
        movieBundle.putStringArrayList("MOVIE_ARRAY_LIST",movieInfo);

        //Put data in intent
        intent.putExtras(movieBundle);

        //Launch the new activity
        startActivity(intent);

    }
}
