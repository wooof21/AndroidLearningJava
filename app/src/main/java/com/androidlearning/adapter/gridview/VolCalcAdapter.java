package com.androidlearning.adapter.gridview;

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

import java.util.ArrayList;

public class VolCalcAdapter extends ArrayAdapter<Shape> {

    private ArrayList<Shape> shapeList;
    private Context context;

    public VolCalcAdapter(@NonNull Context context, ArrayList<Shape> shapeList) {
        super(context, R.layout.adapter_vol_calc_grid_item, shapeList);
        this.shapeList = shapeList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Shape shape = getItem(position);
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.adapter_vol_calc_grid_item, parent, false);

            holder.img = convertView.findViewById(R.id.adapter_vol_calc_grid_item_image);
            holder.name = convertView.findViewById(R.id.adapter_vol_calc_grid_item_text);

            convertView.setTag(R.id.vol_calc_item_view_tag, holder);

        } else {
            holder = (ViewHolder) convertView.getTag(R.id.vol_calc_item_view_tag);
        }

        holder.img.setImageResource(shape.getShapeImgId());
        holder.name.setText(shape.getShapeName());

        return convertView;
    }

    public static class ViewHolder {
        ImageView img;
        TextView name;
    }
}
