package com.androidlearning.retrofit.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    /**
     * Retrofit 2 — How to Use Dynamic Urls for Requests
     * https://futurestud.io/tutorials/retrofit-2-how-to-use-dynamic-urls-for-requests
     *
     * provide settings like base URL, converters and other configs necessary for making API request
     */

    private static String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //define converters to serialize and deserialize the JSON data
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
