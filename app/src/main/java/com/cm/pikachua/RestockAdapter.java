package com.cm.pikachua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Eduardo on 05/04/2018.
 */

public class RestockAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<ItemFirebase> items;

    public RestockAdapter(Context context, ArrayList<ItemFirebase> items) {
        this.context = context;
        this.items = items;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.restock_grid, parent, false);

            //Toast.makeText(context, items.get(0).getName(), Toast.LENGTH_SHORT).show();

            TextView textView = (TextView) convertView.findViewById(R.id.item_name);
            textView.setText(items.get(position).getName());

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(items.get(position).getImage());
            ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.item_logo);
            // Load the image using Glide

            Glide.with(context).using(new FirebaseImageLoader()).load(storageReference).into(imageViewAndroid);

        }
        return convertView;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
