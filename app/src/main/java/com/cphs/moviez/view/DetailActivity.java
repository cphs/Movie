package com.cphs.moviez.view;

import com.bumptech.glide.Glide;
import com.cphs.moviez.R;
import com.cphs.moviez.databinding.ActivityDetailBinding;
import com.cphs.moviez.model.Movie;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DetailActivity extends AppCompatActivity {
  private ActivityDetailBinding mActivityDetailBinding;
  private String mImageBaseUrl = "http://image.tmdb.org/t/p/w500";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
    initToolbar();

    Intent intent = getIntent();
    Movie movie = (Movie) intent.getSerializableExtra("detail");

    mActivityDetailBinding.tbCustom.toolbar.setTitle(movie.getTitle());
    mActivityDetailBinding.tvRating.setText(String.valueOf(movie.getVoteAverage()));
    mActivityDetailBinding.tvRelease.setText(movie.getReleaseDate());
    mActivityDetailBinding.tvSynopsis.setText(movie.getOverview());
    Glide.with(this).load(mImageBaseUrl + movie.getPosterPath()).centerCrop().fitCenter()
        .dontAnimate().into(mActivityDetailBinding.ivPoster);
  }

  private void initToolbar() {
    setSupportActionBar(mActivityDetailBinding.tbCustom.toolbar);
    mActivityDetailBinding.tbCustom.toolbar
        .setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });
    getSupportActionBar().setTitle(getString(R.string.app_name));
  }

}
