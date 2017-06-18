package com.cphs.moviez.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.cphs.moviez.R;
import com.cphs.moviez.databinding.MovieItemBinding;
import com.cphs.moviez.model.Movie;
import com.cphs.moviez.view.DetailActivity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cesario.siringoringo on 14/06/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
  private List<Movie> mMovieList;
  private Activity mActivity;
  private String mImageBaseUrl;

  public MovieAdapter(List<Movie> movieList, Activity activity) {
    mMovieList = movieList;
    mActivity = activity;
    mImageBaseUrl = "http://image.tmdb.org/t/p/w500";
  }

  public void setMovieList(List<Movie> movieList) {
    mMovieList = movieList;
    notifyDataSetChanged();
  }

  public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
    return new MovieAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, final int position) {
    Glide.with(mActivity).load(mImageBaseUrl + mMovieList.get(position).getPosterPath())
        .centerCrop().fitCenter().dontAnimate().into(holder.getMovieItemBinding().imageView);

    holder.getMovieItemBinding().imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(mActivity, DetailActivity.class);
        i.putExtra("detail", mMovieList.get(position));
        mActivity.startActivity(i);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mMovieList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private MovieItemBinding mMovieItemBinding;

    public ViewHolder(View itemView) {
      super(itemView);
      mMovieItemBinding = DataBindingUtil.bind(itemView);
    }

    public MovieItemBinding getMovieItemBinding() {
      return mMovieItemBinding;
    }
  }

}
