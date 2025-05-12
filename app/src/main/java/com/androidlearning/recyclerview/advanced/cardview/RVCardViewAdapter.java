package com.androidlearning.recyclerview.advanced.cardview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.AdvRvItemCardviewBinding;

import java.util.List;

public class RVCardViewAdapter extends RecyclerView.Adapter<RVCardViewAdapter.ViewHolder> {

    private AdvRvItemCardviewBinding binding;
    private List<PlanetCard> planetCardList;

    public RVCardViewAdapter(List<PlanetCard> planetCardList) {
        this.planetCardList = planetCardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adv_rv_item_cardview,
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanetCard planetCard = planetCardList.get(position);
        holder.binding.setPlanetCard(planetCard);
    }

    @Override
    public int getItemCount() {
        return planetCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AdvRvItemCardviewBinding binding;

        public ViewHolder(AdvRvItemCardviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
