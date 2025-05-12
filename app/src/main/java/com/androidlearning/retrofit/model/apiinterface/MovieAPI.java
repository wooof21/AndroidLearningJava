package com.androidlearning.retrofit.model.apiinterface;

import com.androidlearning.retrofit.model.pojo.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("movie/popular")
    Call<MovieResult> getPopularMovies(@Query("api_key") String apiKey);
}
