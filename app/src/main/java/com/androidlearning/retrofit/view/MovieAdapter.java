package com.androidlearning.retrofit.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.MovieItemBinding;
import com.androidlearning.retrofit.model.pojo.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_item,
                parent,
                false
        );

        return new ViewHolder(binding);
    }

    /**
     * Called when the recycler view wants to bind data to the ViewHolder and display it
     * on UI
     * responsible for updating the views within the ViewHolder with data from a specific position
     * in the data set
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        //linking DataBinding with current Movie object
        holder.binding.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private MovieItemBinding binding;

        public ViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
