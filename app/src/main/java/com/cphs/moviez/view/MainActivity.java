package com.cphs.moviez.view;

import java.util.ArrayList;
import java.util.List;

import com.cphs.moviez.BuildConfig;
import com.cphs.moviez.R;
import com.cphs.moviez.adapter.MovieAdapter;
import com.cphs.moviez.databinding.ActivityMainBinding;
import com.cphs.moviez.model.GridSpace;
import com.cphs.moviez.model.Movie;
import com.cphs.moviez.model.Page;
import com.cphs.moviez.network.Client;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  private MovieAdapter mMovieAdapter;
  private List<Movie> mMovieList;
  private ActivityMainBinding mActivityMainBinding;
  private ProgressDialog mProgressDialog;
  private RecyclerView.LayoutManager layoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    mMovieList = new ArrayList<>();
    mMovieAdapter = new MovieAdapter(mMovieList, this);
    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setMessage(getString(R.string.please_wait));
    initToolbar();

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      mActivityMainBinding.rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
    } else if (getResources()
        .getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
      mActivityMainBinding.rvMovies.setLayoutManager(new GridLayoutManager(this, 4));
    }
    GridSpace space = new GridSpace(4);
    mActivityMainBinding.rvMovies.addItemDecoration(space);
    mActivityMainBinding.rvMovies.setAdapter(mMovieAdapter);
    mActivityMainBinding.rvMovies.setHasFixedSize(true);

    getPopular();
  }

  private void initToolbar() {
    setSupportActionBar(mActivityMainBinding.toolbar);
    getSupportActionBar().setTitle(getString(R.string.app_name));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.popular:
        getPopular();
        return true;
      case R.id.top_rated:
        getTopRated();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void getPopular() {
    mProgressDialog.show();
    Client.getService().getPopular(BuildConfig.API_KEY).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Page>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {
            // No implementation required
          }

          @Override
          public void onNext(@NonNull Page page) {
            mActivityMainBinding.tvSortedBy.setText(R.string.popular);
            mMovieAdapter.setMovieList(page.getMovieList());
            mProgressDialog.dismiss();
          }

          @Override
          public void onError(@NonNull Throwable e) {
            AlertDialog builder =
                new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage())
                    .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        getPopular();
                        dialog.dismiss();
                      }
                    }).create();
            builder.show();
            mProgressDialog.dismiss();
          }

          @Override
          public void onComplete() {
            // No implementation required
          }
        });
  }

  private void getTopRated() {
    mProgressDialog.show();
    Client.getService().getTopRated(BuildConfig.API_KEY).observeOn(Schedulers.newThread())
        .subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Page>() {
          @Override
          public void onSubscribe(@NonNull Disposable d) {
            // No implementation required
          }

          @Override
          public void onNext(@NonNull Page page) {
            mActivityMainBinding.tvSortedBy.setText(R.string.top_rated);
            mMovieAdapter.setMovieList(page.getMovieList());
            mProgressDialog.dismiss();
          }

          @Override
          public void onError(@NonNull Throwable e) {
            AlertDialog builder =
                new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage())
                    .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        getPopular();
                        dialog.dismiss();
                      }
                    }).create();
            builder.show();
            mProgressDialog.dismiss();
          }

          @Override
          public void onComplete() {
            // No implementation required
          }
        });
  }
}
