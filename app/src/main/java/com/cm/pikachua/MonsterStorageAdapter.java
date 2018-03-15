package com.cm.pikachua;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class MonsterStorageAdapter extends ArrayAdapter<MonsterStorage> {

    public ArrayList selectedPositions;

    public MonsterStorageAdapter(Context context, ArrayList<MonsterStorage> items) {
        super(context, 0, items);
        selectedPositions = new ArrayList<>();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        // Get the data item for this position
        MonsterStorage monsterStorage = getItem(i);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_grid, parent, false);
        }

        TextView textViewAndroid1 = (TextView) convertView.findViewById(R.id.item_text1);
        textViewAndroid1.setText(monsterStorage.monsterName);

        TextView textViewAndroid2 = (TextView) convertView.findViewById(R.id.item_text2);
        textViewAndroid2.setText(String.valueOf(monsterStorage.stat) + "%");

        if (selectedPositions.contains(i)){
            convertView.setBackgroundColor(Color.rgb(255,255,153));
        }
        else {
            convertView.setBackgroundColor(Color.rgb(242,242,242));
        }

        ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.item_image);
        imageViewAndroid.setImageResource(monsterStorage.monsterImage);

        return convertView;
    }
}
