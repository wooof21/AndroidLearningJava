package com.androidlearning.retrofit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.androidlearning.retrofit.model.pojo.Movie;
import com.androidlearning.retrofit.model.respository.MovieRepo;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    /**
     * ViewModel expose data to UI component using LiveData and other observable mechanisms
     *
     * ViewModel: suitable for non-Android-specific logic
     * AndroidViewModel: used when ViewModel class needs to access Android-specific components(ApplicationContext)
     */

    private MovieRepo movieRepo;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        this.movieRepo = new MovieRepo(application);
    }

    //LiveData
    public LiveData<List<Movie>> getPopularMovies() {
        return movieRepo.getMovieLiveData();
    }
}
