package com.cm.pikachua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_grid_images, parent, false);
        }


        ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.item_image);

        if (monsterCollection.hasBennCaught == true){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(monsterCollection.monsterImage);
            // Load the image using Glide
            Glide.with(getContext()).using(new FirebaseImageLoader()).load(storageReference).into(imageViewAndroid);
        }
        else {
            imageViewAndroid.setImageResource(R.drawable.ic_launcher_background);
        }

        return convertView;
    }
}
