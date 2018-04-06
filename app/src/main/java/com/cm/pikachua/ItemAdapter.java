package com.cm.pikachua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final Item item = getItem(i);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_grid_2texts, parent, false);
        }

        TextView textViewAndroid1 = (TextView) convertView.findViewById(R.id.item_text1);
        textViewAndroid1.setText(item.itemQuantity + "x " + item.itemName);

        TextView textViewAndroid2 = (TextView) convertView.findViewById(R.id.item_text2);
        textViewAndroid2.setText(item.itemDescription);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(item.itemImage);
        ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.item_image);
        // Load the image using Glide

        Glide.with(getContext()).using(new FirebaseImageLoader()).load(storageReference).into(imageViewAndroid);

        return convertView;
    }
}
