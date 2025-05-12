package com.androidlearning.retrofitandpaging.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityPagingBinding;
import com.androidlearning.retrofit.view.MovieAdapter;
import com.androidlearning.retrofitandpaging.adapter.PMovieAdapter;
import com.androidlearning.retrofitandpaging.adapter.PMovieLoadStateAdapter;
import com.androidlearning.retrofitandpaging.utils.GridSpace;
import com.androidlearning.retrofitandpaging.utils.MovieComparator;
import com.androidlearning.retrofitandpaging.viewmodel.PMovieViewModel;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class PagingActivity extends AppCompatActivity {

    private PMovieViewModel pMovieViewModel;
    private ActivityPagingBinding binding;
    private PMovieAdapter pMovieAdapter;
    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Before setContentView, create ViewBinding object
         */
        binding = ActivityPagingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        pMovieAdapter = new PMovieAdapter(new MovieComparator(), requestManager);

        pMovieViewModel = new ViewModelProvider(this).get(PMovieViewModel.class);

        initRecyclerViewAndAdapter();

        //subscribing to paging data
        pMovieViewModel.moviePagingDataFlowable.subscribe(pMoviePagingData -> {
            pMovieAdapter.submitData(getLifecycle(), pMoviePagingData);
        });
    }

    private void initRecyclerViewAndAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        binding.movieListRecyclerView.setLayoutManager(gridLayoutManager);

        binding.movieListRecyclerView.addItemDecoration(new GridSpace(2, 20, true));

        //scroll down to load next page
        binding.movieListRecyclerView.setAdapter(pMovieAdapter.withLoadStateFooter(
                new PMovieLoadStateAdapter(view -> {
                    pMovieAdapter.retry();
                })
        ));

        //set a grid span to set progressBar at the center
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return pMovieAdapter.getItemViewType(position) == PMovieAdapter.LOADING_ITEM ? 1 : 2;
            }
        });
    }
}