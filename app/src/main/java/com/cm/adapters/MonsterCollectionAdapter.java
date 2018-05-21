package com.cm.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.cm.pikachua.MonsterCollection;
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
            convertView = LayoutInflater.from(getContext()).inflate( R.layout.row_grid_images, parent, false);
        }


        final ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.item_image);

        if (monsterCollection.hasBennCaught == true){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(monsterCollection.monsterImage);
            // Load the image using Picasso

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
        }
        else {
            imageViewAndroid.setImageResource(R.drawable.ic_launcher_background);
        }

        return convertView;
    }
}
