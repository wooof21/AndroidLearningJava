package com.androidlearning.firebase.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.PhoneBookItemCardBinding;

import java.util.List;

public class PhoneBookRVAdapter extends RecyclerView.Adapter<PhoneBookRVAdapter.ViewHolder> {

    private Context context;
    private List<PhoneBookUser> users;

    public PhoneBookRVAdapter(Context context, List<PhoneBookUser> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initializes the ViewHolder and Inflate the Item layout
        PhoneBookItemCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.phone_book_item_card,
                parent,
                false
        );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**
         * Bind data to an existing ViewHolder
         * Populates the Views in ViewHolder with data from dataset
         */
        PhoneBookUser user = users.get(position);
        //link data binding, let DataBinding set the value on UI
        holder.binding.setUser(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private PhoneBookItemCardBinding binding;

        public ViewHolder(PhoneBookItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            //Handling Click Event on RecyclerView Item
            this.binding.getRoot().setOnClickListener(v -> {
                //Get the clicked item position
                int position = getBindingAdapterPosition();
            });
        }
    }
}
