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
 * Created by nagariarun on 2/10/18.
 * This is a movie review adapter created for recycle view of movie reviews
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewViewHolder> {

    private ArrayList<MovieReviews> mMovieReviewList;
    private Context mContext;

    public MovieReviewAdapter(Context context)
    {
        mContext = context;
    }

    //Method to set ArrayList in Adapter
    public void setAdapterData(ArrayList<MovieReviews> mReviews)
    {
        mMovieReviewList = mReviews;
    }

    @Override
    public MovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutIdForListItem = R.layout.movie_review_list_item;

        LayoutInflater inflator = LayoutInflater.from(mContext);

        boolean shouldAttachToParentImmediately = false;

        View view = inflator.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);

        MovieReviewViewHolder viewHolder = new MovieReviewViewHolder(view);

        return viewHolder;
    }

    //Method to retrieve each movie information by index
    public MovieReviews getMovieReviewDetails(int position)
    {
        MovieReviews result = mMovieReviewList.get(position);
        return result;
    }

    //Binds data to actual UI
    @Override
    public void onBindViewHolder(MovieReviewViewHolder holder, int position)
    {
        String author = mMovieReviewList.get(position).getAuthor();

        holder.reviewAuthor.setText(mMovieReviewList.get(position).getAuthor());
        holder.reviewContent.setText(mMovieReviewList.get(position).getContent());
    }

    //Method to return the total number of items in list
    @Override
    public int getItemCount() {

        if(mMovieReviewList == null)
            return 0;

        return mMovieReviewList.size();
    }

    //MovieViewHolder which holds each movie data
    class MovieReviewViewHolder extends RecyclerView.ViewHolder
    {
        TextView reviewAuthor;
        TextView reviewContent;

        public MovieReviewViewHolder(View itemView)
        {
            super(itemView);
            reviewAuthor = (TextView) itemView.findViewById(R.id.review_author);
            reviewContent = (TextView)itemView.findViewById(R.id.review_content);

        }

    }

}
