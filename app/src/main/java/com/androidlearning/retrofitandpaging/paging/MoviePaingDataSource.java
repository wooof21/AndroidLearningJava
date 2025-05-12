package com.androidlearning.retrofitandpaging.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.androidlearning.retrofitandpaging.model.api.APIClient;
import com.androidlearning.retrofitandpaging.model.pojo.PMovie;
import com.androidlearning.retrofitandpaging.model.pojo.PMovieRes;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePaingDataSource extends RxPagingSource<Integer, PMovie> {



    @NonNull
    @Override
    public Single<LoadResult<Integer, PMovie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        /**
         * If pageNumber is already there, then initiate page variable with it.
         * Otherwise, get the first page
         */
        try {
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;

            return APIClient.getApiInterface().getPopularMoviesByPages(page)
                    .subscribeOn(Schedulers.io())
                    .map(PMovieRes::getMovies)
                    .map(pMovies -> toLoadResult(pMovies, page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            return Single.just(new LoadResult.Error<>(e));
        }
    }

    private LoadResult<Integer, PMovie> toLoadResult(List<PMovie> movies, int page) {
        /**
         * If not in first page, passing both previous page and next page
         * means there are data load on previous and next page
         *
         * page+1: preparing the next page
         */
        return new LoadResult.Page<>(movies, page == 1 ? null : page - 1, page + 1);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, PMovie> pagingState) {
        return 0;
    }
}
