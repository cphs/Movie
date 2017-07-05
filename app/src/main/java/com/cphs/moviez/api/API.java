package com.cphs.moviez.api;

import com.cphs.moviez.model.Page;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cesario.siringoringo on 14/06/2017.
 */

public interface API {
  @GET("movie/popular")
  Observable<Page> getPopular(@Query("api_key") String apiKey);

  @GET("movie/top_rated")
  Observable<Page> getTopRated(@Query("api_key") String apiKey);
}
