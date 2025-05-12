package com.androidlearning.mvvm;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.MvvmContactListItemBinding;
import com.androidlearning.mvvm.model.entity.Contact;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private List<Contact> contacts;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create a new ViewHolder for items in RecyclerView
        //use for data binding, to inflating a layout and create a binding object
        MvvmContactListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.mvvm_contact_list_item,
                parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**
         * Called by RecyclerView when it needs to display or update an item
         * at a specific position in the list or grid
         */

        Contact contact = this.contacts.get(position);

        holder.binding.setContactItem(contact);
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;

        /**
         * Inform the associate RecyclerView that the underlying dataset has changed,
         * and the RecyclerView should refresh its view to reflect the changes
         */
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * To use DataBinding with RecyclerView
         *
         * create a reference to binding:
         * format: <item xml file name> + Binding(postfix)
         */
        private final MvvmContactListItemBinding binding;

        public ViewHolder(@NonNull MvvmContactListItemBinding binding) {
            //obtain the root view of the layout associate with a data binding instance
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
