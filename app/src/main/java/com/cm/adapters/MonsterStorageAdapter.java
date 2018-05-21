package com.cm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cm.pikachua.MonsterStorage;
import com.cm.pikachua.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
            convertView = LayoutInflater.from(getContext()).inflate( R.layout.row_grid, parent, false);
        }

        TextView textViewAndroid1 = (TextView) convertView.findViewById(R.id.item_text1);
        textViewAndroid1.setText(monsterStorage.monsterName);

        TextView textViewAndroid2 = (TextView) convertView.findViewById(R.id.item_text2);
        textViewAndroid2.setText("Value: " + String.valueOf(monsterStorage.stat));

        if (selectedPositions.contains(i)){
            convertView.setBackgroundColor(Color.rgb(255,255,153));
        }
        else {
            convertView.setBackgroundColor(Color.rgb(242,242,242));
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(monsterStorage.monsterImage);
        final ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.item_image);
        // Load the image using Glide

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.with(getContext()).load(uri.toString()).into(imageViewAndroid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return convertView;
    }
}
