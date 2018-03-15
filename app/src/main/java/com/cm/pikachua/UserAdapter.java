package com.cm.pikachua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PedroRaimundo on 2018-03-14.
 */

public class UserAdapter extends BaseAdapter {

    private Context context;
    private int user_image;
    private String[] user;
    LayoutInflater inflater;

    public UserAdapter(Context context, String[] user, int user_image) {
        this.context = context;
        this.user = user;
        this.user_image = user_image;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.profile_list_view, null);
        }

        TextView textViewAndroid1 = (TextView) convertView.findViewById(R.id.username);
        textViewAndroid1.setText("Username: " + user[1]);

        TextView textViewAndroid2 = (TextView) convertView.findViewById(R.id.startDate);
        textViewAndroid2.setText("Member since: " + user[2]);

        TextView textViewAndroid3 = (TextView) convertView.findViewById(R.id.totalMonstersCaught);
        textViewAndroid3.setText("Total mosnters caught: " + user[3]);

        TextView textViewAndroid4 = (TextView) convertView.findViewById(R.id.totalEXP);
        textViewAndroid4.setText("Total EXP earned: " + user[4]);

        ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.image);
        imageViewAndroid.setImageResource(user_image);

        /*imageViewAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(this,
                        "És feio, esperavas o quê?", Toast.LENGTH_LONG)
                        .show();

            }
        });*/

        return convertView;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return user[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
