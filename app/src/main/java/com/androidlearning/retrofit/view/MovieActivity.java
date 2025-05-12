package com.androidlearning.retrofit.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityMovieBinding;
import com.androidlearning.retrofit.model.pojo.Movie;
import com.androidlearning.retrofit.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    private List<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActivityMovieBinding binding;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);

        movies = new ArrayList<>();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        getPopularMovies();

        swipeRefreshLayout = binding.movieListSwipetorefreshLayout;
        //used to specify the colors of swipe ro refresh loading indicator
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this::getPopularMovies);
    }

    private void getPopularMovies() {
        viewModel.getPopularMovies().observe(this, moviesLiveData -> {
            movies = moviesLiveData;
            renderMoviesInRecyclerView();
            //dismiss loading spinner when refreshing done
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void renderMoviesInRecyclerView() {
        recyclerView = binding.movieListRecyclerView;

        adapter = new MovieAdapter(movies);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //display Movie Item in Grid manner and with 2 columns in each row
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //notify an adapter associated with a RecyclerView that
        //the underlying dataset has changed and it should refresh
        //the displayed items in RecyclerView
        adapter.notifyDataSetChanged();
    }

}