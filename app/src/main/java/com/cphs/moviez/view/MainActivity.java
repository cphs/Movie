package com.cphs.moviez.view;

import java.util.ArrayList;
import java.util.List;

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
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  private MovieAdapter mMovieAdapter;
  private List<Movie> mMovieList;
  private ActivityMainBinding mActivityMainBinding;
  private String apiKey = "YOUR_API_KEY";
  private ProgressDialog mProgressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    mMovieList = new ArrayList<>();
    mMovieAdapter = new MovieAdapter(mMovieList, this);
    mProgressDialog = new ProgressDialog(this);
    mProgressDialog.setMessage(getString(R.string.please_wait));
    initToolbar();
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
    GridSpace space = new GridSpace(4);
    mActivityMainBinding.rvMovies.setLayoutManager(layoutManager);
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
    Client.getService().getPopular(apiKey).enqueue(new Callback<Page>() {
      @Override
      public void onResponse(Call<Page> call, Response<Page> response) {
        if (response.isSuccessful()) {
          mActivityMainBinding.tvSortedBy.setText(R.string.popular);
          mMovieAdapter.setMovieList(response.body().getMovieList());
        }
        mProgressDialog.dismiss();
      }

      @Override
      public void onFailure(Call<Page> call, Throwable t) {
        mProgressDialog.dismiss();
        AlertDialog builder = new AlertDialog.Builder(MainActivity.this).setMessage(t.getMessage())
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
    });
  }

  private void getTopRated() {
    mProgressDialog.show();
    Client.getService().getTopRated(apiKey).enqueue(new Callback<Page>() {
      @Override
      public void onResponse(Call<Page> call, Response<Page> response) {
        if (response.isSuccessful()) {
          mActivityMainBinding.tvSortedBy.setText(R.string.top_rated);
          mMovieAdapter.setMovieList(response.body().getMovieList());
        }
        mProgressDialog.dismiss();
      }

      @Override
      public void onFailure(Call<Page> call, Throwable t) {
        AlertDialog builder = new AlertDialog.Builder(MainActivity.this).setMessage(t.getMessage())
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
    });
  }
}
