package com.androidlearning.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.model.Grocery;

import java.util.List;


//recycler view item click event: https://antonioleiva.com/recyclerview-listener/
public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {

    private List<Grocery> groceries;

    private static RecyclerViewItemClickListener clickListener;

    public MarketAdapter(List<Grocery> groceries) {
        this.groceries = groceries;
    }

    public void setItemClickListener(RecyclerViewItemClickListener clickListener) {
        MarketAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    //responsible for creating new view holders for the item
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /**
         * viewType: used when recycler view has multiple types of item
         *
         * useful when need different layouts or behaviors for different
         * view item
         */

        View item = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.market_app_item, parent, false);

        return new ViewHolder(item);
    }

    @Override
    //bind data from dataset to the views within the view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery grocery = this.groceries.get(position);

        holder.img.setImageResource(grocery.getImgId());
        holder.title.setText(grocery.getTitle());
        holder.description.setText(grocery.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.groceries.size();
    }

    /**
     * View.OnClickListener: provide by Android
     *  invoke when a View(UI) get clicked
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img;
        TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.market_item_img);
            title = itemView.findViewById(R.id.market_item_title);
            description = itemView.findViewById(R.id.market_item_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //handles when a specific View item is clicked
            if(clickListener != null) {
                /**
                 * link customer click listener to this View item click event
                 *
                 * getAdapterPosition(): used to retrieve the adapter position of the item associated with the view holder
                 * useful when need to know the position of the clicked item within the data set
                 */
                clickListener.onItemClicked(view, getAdapterPosition());
            }
        }
    }
}
