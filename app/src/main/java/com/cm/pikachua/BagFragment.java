package com.cm.pikachua;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.nuno.weatherapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BagFragment extends Fragment {

    ArrayList<Item> arrayOfItems = new ArrayList<Item>();

    int[] listViewTotalId = {
            1,2,3,4,5,6,
            7,8,9,10,11,12,
            13,14,15,16,0,18
    };

    String[] listViewStringTitle = {
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress"

    } ;

    String[] listViewStringSubtitle = {
            "1","2","3","4","5","6",
            "7","8","9","10","11","12",
            "13","14","15","16","17","18"

    } ;

    int[] listViewImageId = {
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background

    };


    public BagFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bag, container, false);

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


        final ItemAdapter adapter = new ItemAdapter(getContext(), arrayOfItems);

        Log.d("T","e");

        int number = 0;
        for (int i=0;i<listViewTotalId.length;i++){
            if (listViewTotalId[i] > 0) {
                Item x = new Item(i, listViewStringTitle[i], listViewStringSubtitle[i], listViewImageId[i], listViewTotalId[i]);
                adapter.add(x);
                number += listViewTotalId[i];
            }
        }

        int total = 200;

        TextView t = rootView.findViewById(R.id.total1);
        t.setText("Max: " + number + "/" + total);

        final ListView androidListView = (ListView) rootView.findViewById(R.id.list_view);
        androidListView.setAdapter(adapter);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "ListView Item: " + adapter.getItem(i).itemID, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

}
