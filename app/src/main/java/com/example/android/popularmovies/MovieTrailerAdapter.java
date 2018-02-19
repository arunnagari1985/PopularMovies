package com.example.android.popularmovies;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by nagariarun on 2/11/18.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder>
{

    private ArrayList<MovieTrailers> mMovieTrailerList;

    private Context mContext;

    private final ListItemClickListener mOnClickListener;

    public MovieTrailerAdapter(Context context, ListItemClickListener listener)
    {
        mContext = context;
        mOnClickListener = listener;
    }

    //Method to set ArrayList in Adapter
    public void setAdapterData(ArrayList<MovieTrailers> trailers)
    {
        mMovieTrailerList = trailers;
    }

    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutIdForListItem = R.layout.movie_trailer_list_item;

        LayoutInflater inflator = LayoutInflater.from(mContext);

        boolean shouldAttachToParentImmediately = false;

        View view = inflator.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);

        MovieTrailerViewHolder viewHolder = new MovieTrailerViewHolder(view);

        return viewHolder;
    }

    //Method to retrieve each movie information by index
    public MovieTrailers getMovieTrailerDetails(int position)
    {
        MovieTrailers result = mMovieTrailerList.get(position);
        return result;
    }

    //Binds data to actual UI
    @Override
    public void onBindViewHolder(MovieTrailerViewHolder holder, int position)
    {
        Log.v(TAG,"onBindViewHolder position: " + position);

        MovieTrailers temp = mMovieTrailerList.get(position);
        String posterPath = null;


        if(temp != null)
          posterPath = temp.getPosterUrl();

        if(posterPath != null) {
            Picasso.with(mContext).load(posterPath).resize(120, 100).into(holder.listItemMovieTrailerPoster);
        }

    }

    //Method to return the total number of items in list
    @Override
    public int getItemCount() {

        if(mMovieTrailerList == null)
            return 0;

        return mMovieTrailerList.size();
    }

    //MovieViewHolder which holds each movie data
    class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView listItemMovieTrailerPoster;


        public MovieTrailerViewHolder(View itemView)
        {
            super(itemView);
            listItemMovieTrailerPoster = (ImageView) itemView.findViewById(R.id.iv_trailer_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int indexOfItem = getAdapterPosition();
            mOnClickListener.onListItemClicked(indexOfItem);
        }
    }
    //An interface to handle List Item Clicks, this is implemented in ChildActivity
    public interface ListItemClickListener
    {
        void onListItemClicked(int itemClickedIndex);
    }
}
