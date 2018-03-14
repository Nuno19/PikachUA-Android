package com.cm.pikachua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.cm.nuno.weatherapp.R;

import java.util.ArrayList;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class MonsterCollectionAdapter extends ArrayAdapter<MonsterCollection> {
    public MonsterCollectionAdapter(Context context, ArrayList<MonsterCollection> monsterCollections) {
        super(context, 0, monsterCollections);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        // Get the data item for this position
        MonsterCollection monsterCollection = getItem(i);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_grid_2texts, parent, false);
        }

        ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.item_image);
        if (monsterCollection.hasBennCaught == true){
            imageViewAndroid.setImageResource(monsterCollection.monsterImage);
        }
        else {
            imageViewAndroid.setImageResource(monsterCollection.monsterNotImage);
        }

        return convertView;
    }
}
