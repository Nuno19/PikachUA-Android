package com.cm.pikachua;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.nuno.weatherapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageFragment extends Fragment {

    ArrayList<MonsterStorage> arrayOfMonsterStorage = new ArrayList<MonsterStorage>();

    int[] gridViewStat = {
            1,4,2,3,4,5,
            10,8,9,7,2,6,
            11,12,3,13,0,1
    };

    String[] gridViewString = {
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress",
            "Alram", "Android", "Mobile", "Website", "Profile", "WordPress"

    } ;
    int[] gridViewImageId = {
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background

    };


    public StorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_storage, container, false);

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

        final MonsterStorageAdapter adapter = new MonsterStorageAdapter(getContext(), arrayOfMonsterStorage);

        Log.d("T","e");

        int number = 0;
        for (int i=0;i<gridViewString.length;i++){
            MonsterStorage x = new MonsterStorage(i, gridViewString[i], gridViewImageId[i], gridViewStat[i]);
            adapter.add(x);
            number += 1;
        }

        int total = 200;

        TextView t = rootView.findViewById(R.id.total1);
        t.setText("Max: " + number + "/" + total);

        final GridView androidGridView = (GridView) rootView.findViewById(R.id.gridView);
        androidGridView.setAdapter(adapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "ListView Item: " + adapter.getItem(i).monsterId, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

}
