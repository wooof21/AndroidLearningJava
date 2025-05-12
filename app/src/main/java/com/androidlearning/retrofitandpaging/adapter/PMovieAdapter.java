package com.androidlearning.retrofitandpaging.adapter;

import static com.androidlearning.retrofitandpaging.utils.Constants.BASE_URL;
import static com.androidlearning.retrofitandpaging.utils.Constants.IMG_BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.databinding.PagingMovieItemBinding;
import com.androidlearning.retrofitandpaging.model.pojo.PMovie;
import com.bumptech.glide.RequestManager;

import kotlin.coroutines.CoroutineContext;

public class PMovieAdapter extends PagingDataAdapter<PMovie, PMovieAdapter.PMovieViewHolder> {

    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 1;

    private RequestManager glide;

    public PMovieAdapter(@NonNull DiffUtil.ItemCallback<PMovie> diffCallback, RequestManager glide) {
        super(diffCallback);
        this.glide = glide;
    }

    @NonNull
    @Override
    public PMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PMovieViewHolder(PagingMovieItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    //https://proandroiddev.com/android-paging-library-with-multiple-view-type-68f85fe1222d
    @Override
    public int getItemViewType(int position) {
        return position == getItemCount() ? MOVIE_ITEM : LOADING_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull PMovieViewHolder holder, int position) {
        PMovie currentMovie = getItem(position);
        if(currentMovie != null) {
            glide.load(IMG_BASE_URL + currentMovie.getPosterPath())
                    .into(holder.binding.pagingMovieImg);
            holder.binding.pagingMovieRating.setText(String.valueOf(currentMovie.getVoteAverage()));
        }
    }

    public static class PMovieViewHolder extends RecyclerView.ViewHolder {
        /**
         * https://developer.android.com/topic/libraries/view-binding
         * view binding: pageing_movie_item.xml
         *
         * ViewBinding: Only binding views to code.
         *
         * DataBinding: Binding data (from code) to views + ViewBinding (Binding views to code)
         *
         * ViewBinding is a sort of subset of DataBinding libraries
         * There are three important differences:
         *
         *     1. With view binding, the layouts do not need a layout tag
         *
         *     2. You can't use viewbinding to bind layouts with data in xml
         *     (No binding expressions, no BindingAdapters nor two-way binding with viewbinding)
         *
         *     3. The main advantages of viewbinding are speed and efficiency.
         *     It has a shorter build time because it avoids the overhead and
         *     performance issues associated with databinding
         *     due to annotation processors affecting databinding's build time.
         */
        PagingMovieItemBinding binding;

        public PMovieViewHolder(@NonNull PagingMovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
