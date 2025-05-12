package com.androidlearning.retrofit.model.respository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.androidlearning.R;
import com.androidlearning.retrofit.model.apiinterface.MovieAPI;
import com.androidlearning.retrofit.config.APIConfig;
import com.androidlearning.retrofit.model.pojo.Movie;
import com.androidlearning.retrofit.model.pojo.MovieResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepo {

    /**
     * used to abstract the data source details(web service, local DB, remote DB ...) and provide a clean API for
     * the ViewModel to fetch and manage data
     */

    private List<Movie> movieList;

    //use live data in ViewModel to fetch the data and display in UI
    private MutableLiveData<List<Movie>> movieLiveData;

    private Application application;

    public MovieRepo(Application application) {
        this.application = application;
        this.movieList = new ArrayList<>();
        this.movieLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getMovieLiveData() {
        MovieAPI movieAPI = APIConfig.getMovieAPI();

        //use Application context to read value from strings.xml for api_key
        Call<MovieResult> retrofitCall = movieAPI
                .getPopularMovies(application.getApplicationContext().getString(R.string.api_key));

        /**
         * enqueue() and execute() are 2 methods provided by the Call interface to send HTTP
         * request to a web service or API. They are used to initiate the request and obtain
         * the response from the server
         *
         * Differ in terms of how they handle the request and response
         *
         * enqueue(): is an asynchronous method for making HTTP request, typically used when
         * to perform the network request in the background thread and handle the response on
         * the main UI thread
         *
         * execute(): is an synchronous method for making HTTP request, typically used when
         * to perform the network request in the current thread, which is often discourage of
         * Android main UI thread
         */
        retrofitCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                //Success
                MovieResult movieResult = response.body();

                if(movieResult != null && movieResult.getMovies() != null) {
                    movieList = movieResult.getMovies();
                    /**
                     * setValue: call from main UI thread and set new values immediately
                     * If call from a background thread, it will throw an exception
                     *
                     * postValue: can be called from any thread
                     */
                    movieLiveData.setValue(movieList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable throwable) {
                Log.e("Movie Call Failed", Objects.requireNonNull(throwable.getMessage()));
            }
        });

        return movieLiveData;
    }
}
