package com.androidlearning.cardview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.model.Sport;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {

    private List<Sport> sports;

    public SportsAdapter(List<Sport> sports) {
        this.sports = sports;
    }

    @NonNull
    @Override
    public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sports_card_item, parent, false);

        return new SportsViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {

        Sport sport = this.sports.get(position);

        holder.img.setImageResource(sport.getSportImg());
        holder.title.setText(sport.getSportTitle());

    }

    @Override
    public int getItemCount() {
        return sports.size();
    }


    public static class SportsViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;

        public SportsViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.sport_card_item_img);
            title = itemView.findViewById(R.id.sport_card_item_title);
        }
    }
}
