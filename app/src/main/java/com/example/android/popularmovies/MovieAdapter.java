package com.example.android.popularmovies;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nagariarun on 12/10/17.
 *
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{
  private ArrayList<MovieDetails> mMovieList;
  private Context mContext;

  private final ListItemClickListener mOnClickListener;

  public MovieAdapter(Context context, ListItemClickListener listener)
  {
     mContext = context;
     mOnClickListener = listener;
  }

  //Method to set ArrayList in Adapter
    public void setAdapterData(ArrayList<MovieDetails> movies)
    {
        mMovieList = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutIdForListItem = R.layout.movie_list_item;

        LayoutInflater inflator = LayoutInflater.from(mContext);

        boolean shouldAttachToParentImmediately = false;

        View view = inflator.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);

        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    //Method to retrieve each movie information by index
    public MovieDetails getMovieDetails(int position)
    {
        MovieDetails result = mMovieList.get(position);
        return result;
    }

    //Binds data to actual UI
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        Picasso.with(mContext).load(mMovieList.get(position).getmMoviePosterUrl()).resize(240,200).into(holder.litsItemMoviePoster);
         holder.listItemMovieTitle.setText(mMovieList.get(position).getmMovieTitle());
    }

    //Method to return the total number of items in list
    @Override
    public int getItemCount() {

        if(mMovieList == null)
            return 0;

        return mMovieList.size();
    }

    //MovieViewHolder which holds each movie data
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView listItemMovieTitle;
        ImageView litsItemMoviePoster;

      public MovieViewHolder(View itemView)
      {
          super(itemView);
          listItemMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie);
          litsItemMoviePoster = (ImageView)itemView.findViewById(R.id.iv_poster);
          itemView.setOnClickListener(this);
      }

        @Override
        public void onClick(View v)
        {
           int indexOfItem = getAdapterPosition();
           mOnClickListener.onListItemClicked(indexOfItem);
        }
    }

    //An interface to handle List Item Clicks, this is implemented in MainActivity
    public interface ListItemClickListener
    {
        void onListItemClicked(int itemClickedIndex);
    }

}
