package com.cphs.moviez.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.cphs.moviez.R;
import com.cphs.moviez.databinding.MovieItemBinding;
import com.cphs.moviez.model.Movie;

import android.content.Context;
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
  private Context mContext;
  private String mImageBaseUrl;

  public MovieAdapter(List<Movie> movieList, Context context) {
    mMovieList = movieList;
    mContext = context;
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
  public void onBindViewHolder(ViewHolder holder, int position) {
    Glide.with(mContext).load(mImageBaseUrl + mMovieList.get(position).getPosterPath())
        .into(holder.getMovieItemBinding().imageView);
    holder.getMovieItemBinding().imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

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
