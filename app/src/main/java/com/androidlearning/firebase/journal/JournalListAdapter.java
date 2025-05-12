package com.androidlearning.firebase.journal;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.firebase.journal.model.pojo.Journal;
import com.bumptech.glide.Glide;

import java.util.List;

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.ViewHolder>{

    private Context context;
    private List<Journal> journals;

    public JournalListAdapter(Context context, List<Journal> journals) {
        this.context = context;
        this.journals = journals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.journal_item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Journal journal = journals.get(position);

        holder.title.setText(journal.getTitle());
        holder.name.setText(journal.getUsername());
        holder.thoughts.setText(journal.getThoughts());
        holder.timeAdded.setText(DateUtils.getRelativeTimeSpanString(journal.getTimeAdded().getSeconds() * 1000));

        //Glide to display image
        Glide.with(context).load(journal.getImgUrl())
                .fitCenter()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, thoughts, timeAdded, name;
        private ImageView image, share;
        private String userId, username;

        //User ViewBinding/DataBinding to avoid the findViewById
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.journal_item_row_title);
            thoughts = itemView.findViewById(R.id.journal_item_row_thought);
            timeAdded = itemView.findViewById(R.id.journal_item_row_timestamp);
            name = itemView.findViewById(R.id.journal_item_row_username);
            image = itemView.findViewById(R.id.journal_item_row_image);
            share = itemView.findViewById(R.id.journal_item_row_share_button);
        }
    }
}
