package com.androidlearning.retrofitandpaging.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.PagingLoadStateItemBinding;

/**
 * https://developer.android.com/reference/kotlin/androidx/paging/LoadStateAdapter
 * Adapter for displaying a RecyclerView item based on LoadState, such as a loading spinner, or a retry error button.
 *
 */
public class PMovieLoadStateAdapter extends LoadStateAdapter<PMovieLoadStateAdapter.LoadStateViewHolder> {

    private View.OnClickListener retryCallback;

    public PMovieLoadStateAdapter(View.OnClickListener retryCallback) {
        this.retryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(parent, retryCallback);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private TextView errorMsg;
        private Button retry;

        public LoadStateViewHolder(@NonNull ViewGroup parent,
                                   @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.paging_load_state_item, parent, false
            ));

            //itemView - refers to the item view inflated above
            PagingLoadStateItemBinding binding = PagingLoadStateItemBinding.bind(itemView);

            this.progressBar = binding.pagingProgressbar;
            this.errorMsg = binding.pagingErrorMsg;
            this.retry = binding.pagingRetryButton;
            this.retry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState) {
            if(loadState instanceof LoadState.Error) {
                LoadState.Error errorState = (LoadState.Error) loadState;
                errorMsg.setText(errorState.getError().getLocalizedMessage());
            }

            progressBar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);

            retry.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);

            errorMsg.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
        }
    }
}
