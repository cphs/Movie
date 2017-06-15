package com.cphs.moviez.network;

import com.cphs.moviez.api.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cesario.siringoringo on 14/06/2017.
 */

public class Client {
  private static Retrofit retrofit = null;

  public static Retrofit getClient() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
          .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofit;
  }

  public static API getService() {
    return getClient().create(API.class);
  }
}
