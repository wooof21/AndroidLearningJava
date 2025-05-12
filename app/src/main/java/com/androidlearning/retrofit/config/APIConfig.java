package com.androidlearning.retrofit.config;

import com.androidlearning.retrofit.model.apiinterface.MovieAPI;

public class APIConfig {

    /**
     * Acts as a central configuration point for defining how HTTP requests and responses
     * should be handled
     *
     */

    public static MovieAPI getMovieAPI() {
        //use retrofit instance to create the MovieAPI interface
        return RetrofitConfig.getRetrofitInstance().create(MovieAPI.class);
    }
}
