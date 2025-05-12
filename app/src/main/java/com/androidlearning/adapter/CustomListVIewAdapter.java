package com.androidlearning.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidlearning.R;

import java.util.Collections;
import java.util.List;

public class CustomListVIewAdapter extends BaseAdapter {

    private Context context;
    private List<Pair<String, String>> data;

    public CustomListVIewAdapter(Context context, List<Pair<String, String>> data) {
        /**
         * Edge Case Handling for data
         *
         *     Issue: There is no check for null or empty data in your adapter.
         *     If data is null or empty, the app may crash or display nothing without clear feedback.
         *
         *     Solution: Add a null check or handle empty data gracefully in your adapter.
         */
        this.data = data != null ? data : Collections.emptyList();
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        //convertView: is a recycled view that can be reused to improve the performance of list
        if(convertView != null) {
            //reusing the view
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_custom_list_view_item, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.custom_list_item_language);
            holder.like = convertView.findViewById(R.id.custom_list_view_item_like);

            /**
             * 1. convertView Not Setting Tags
             *
             *     Issue: In the CustomListVIewAdapter, when you inflate a new convertView,
             *     you don’t call convertView.setTag(holder) to attach the ViewHolder to the view.
             *     As a result, the convertView cannot reuse the ViewHolder when it is recycled,
             *     which defeats the purpose of using the ViewHolder pattern.
             *
             *     Solution: Add convertView.setTag(holder) after inflating a new view.
             */
            convertView.setTag(holder);
        }

        holder.name.setText(this.data.get(position).first);
        holder.like.setText(this.data.get(position).second);


        return convertView;
    }


    static class ViewHolder {
        //hold reference to the views in the item layout
        TextView name, like;
    }
}
