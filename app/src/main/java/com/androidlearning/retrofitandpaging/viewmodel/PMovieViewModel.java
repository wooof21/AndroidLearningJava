package com.androidlearning.retrofitandpaging.viewmodel;

import android.content.Intent;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.androidlearning.retrofitandpaging.model.pojo.PMovie;
import com.androidlearning.retrofitandpaging.paging.MoviePaingDataSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class PMovieViewModel extends ViewModel {

    /**
     * https://kdmalviyan.medium.com/introduction-to-rxjava-flowable-179274bf998c
     * What is a Flowable?
     *
     * A Flowable is similar to an Observable in that it represents a stream of data or events
     * that can be observed and processed by Observers.
     * However, Flowable adds the ability to handle backpressure,
     * which is crucial when dealing with large amounts of data or slow consumers.
     *
     * Backpressure refers to the mechanism of controlling the flow of data
     * so that the consumer can process it at its own pace, avoiding overwhelming or dropping data.
     */
    public Flowable<PagingData<PMovie>> moviePagingDataFlowable;

    public PMovieViewModel() {
        init();
    }

    private void init() {
        //Defining Paging Source
        MoviePaingDataSource dataSource = new MoviePaingDataSource();

        //Create new Pager config
        Pager<Integer, PMovie> pager = new Pager<>(
                new PagingConfig(
                20, 20, false, 20, 20*499),

                () -> dataSource);

        //Initializing Flowable
        moviePagingDataFlowable = PagingRx.getFlowable(pager);
        CoroutineScope scope = ViewModelKt.getViewModelScope(this);

        PagingRx.cachedIn(moviePagingDataFlowable, scope);
    }
}
