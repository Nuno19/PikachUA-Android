package com.cm.pikachua;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ListView listView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        Button button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Voltar", Toast.LENGTH_LONG)
                        .show();

                getActivity().onBackPressed();

            }
        });

        String[] user = new String[] {"Raimas96", "2017-03-22", getDecimal(34378989), getDecimal(2100000000)};
        int userInt = R.drawable.ic_launcher_background;

        TextView textViewAndroid1 = (TextView) rootView.findViewById(R.id.username);
        textViewAndroid1.setText("Username: " + user[0]);

        TextView textViewAndroid2 = (TextView) rootView.findViewById(R.id.startDate);
        textViewAndroid2.setText("Member since: " + user[1]);

        TextView textViewAndroid3 = (TextView) rootView.findViewById(R.id.totalMonstersCaught);
        textViewAndroid3.setText("Total monsters caught: " + user[2]);

        TextView textViewAndroid4 = (TextView) rootView.findViewById(R.id.totalEXP);
        textViewAndroid4.setText("Total EXP earned: " + user[3]);

        ImageView imageViewAndroid = (ImageView) rootView.findViewById(R.id.image);
        imageViewAndroid.setImageResource(userInt);

        imageViewAndroid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "És feio, esperavas o quê?", Toast.LENGTH_LONG)
                        .show();
            }
        });

        return rootView;
    }

    public String getDecimal (int number) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbol = format.getDecimalFormatSymbols();
        symbol.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(symbol);

        return format.format(number);
    }

}
