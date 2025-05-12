package com.androidlearning.retrofitandpaging.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.androidlearning.retrofitandpaging.model.pojo.PMovie;

import java.util.Objects;

/**
 * Compare Movie Object to avoid duplicate item
 */
public class MovieComparator extends DiffUtil.ItemCallback<PMovie> {

    @Override
    public boolean areItemsTheSame(@NonNull PMovie oldItem, @NonNull PMovie newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull PMovie oldItem, @NonNull PMovie newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }
}
