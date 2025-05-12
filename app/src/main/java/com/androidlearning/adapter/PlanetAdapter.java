package com.androidlearning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidlearning.R;
import com.androidlearning.model.Planet;

import java.util.ArrayList;

/**
 * ArrayAdapter
 *      Its a more complete implementation that works well for data in arrays or ArrayLists.
 * ListAdapter
 *      is a an interface implemented by concrete adapter classes.
 */
public class PlanetAdapter extends ArrayAdapter<Planet> {

    private ArrayList<Planet> planetsList;
    private Context context;

    public PlanetAdapter(ArrayList<Planet> planetsList, Context context) {
        super(context, R.layout.item_lists_planets, planetsList);
        this.planetsList = planetsList;
        this.context = context;
    }

    /**
     * ViewHolder class,
     *      used to cache reference to the views within an item layout,
     *      so they dont have to repeatedly looked up during scrolling
     */
    private static class ViewHolder {
        ImageView img;
        TextView name, moonCount;
    }

    /**
     * getView()
     *      used to create and return a view for a specific item in the list
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1. get the planet object for the current position
        Planet planet = getItem(position);

        //2. inflate the layout
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //parent: is the parent view the convertView attached to
            //false: the view will not attached to parent right away
            convertView = inflater.inflate(R.layout.item_lists_planets, parent, false);

            //3. find the item view
            viewHolder.img = convertView.findViewById(R.id.item_planets_img);
            viewHolder.name = convertView.findViewById(R.id.item_planets_name);
            viewHolder.moonCount = convertView.findViewById(R.id.item_planets_moon_count);

            /**
             * setTag(): used to attach an arbitrary object to the view object
             * it can be any object that is useful for associating additional data or information with the view
             *
             * attaching viewHolder, ensure it can be retrieved from the view when need to update
             * the view content
             *
             * can also attach some other custom data
             */
            convertView.setTag(R.id.planet_item_view_tag, viewHolder);
        } else {
            //view is recycled
            //retrieve the viewHolder using the tag key
            viewHolder = (ViewHolder) convertView.getTag(R.id.planet_item_view_tag);
        }


        viewHolder.img.setImageResource(planet.getPlanetImg());
        viewHolder.name.setText(planet.getPlanetName());
        viewHolder.moonCount.setText(planet.getMoonCount());



        return convertView;
    }
}
