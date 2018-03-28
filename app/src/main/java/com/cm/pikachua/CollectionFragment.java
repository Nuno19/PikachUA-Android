package com.cm.pikachua;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    ArrayList<MonsterCollection> arrayOfMonsterCollections = new ArrayList<MonsterCollection>();

    String[] gridMonsterName = {
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "MewTwo", ""
    };

    boolean[] gridViewHasBeenCaught = {
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,false,
            false,false,false,false,false,true,
            false
    };

    int[] gridViewImageId = {
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.mewtwo,
            R.drawable.ic_launcher_background

    };

    int[] gridViewNotImageId = {
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background, R.drawable.not_ic_launcher_background,
            R.drawable.not_ic_launcher_background
    };


    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collection, container, false);

        FloatingActionButton button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Voltar", Toast.LENGTH_LONG).show();

                getActivity().onBackPressed();

            }
        });

        final MonsterCollectionAdapter adapter = new MonsterCollectionAdapter(getContext(), arrayOfMonsterCollections);

        Log.d("T","e");

        int number = 0;
        for (int i=0;i<gridViewHasBeenCaught.length;i++){
                MonsterCollection x = new MonsterCollection(i, gridMonsterName[i], gridViewImageId[i], gridViewNotImageId[i], gridViewHasBeenCaught[i], "");
                adapter.add(x);
            if (gridViewHasBeenCaught[i] == true) {
                number += 1;
            }
        }

        TextView t = rootView.findViewById(R.id.total1);
        t.setText("Max: " + number + "/" + gridViewHasBeenCaught.length);

        final GridView androidGridView = (GridView) rootView.findViewById(R.id.gridView);
        androidGridView.setAdapter(adapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {

                if (adapter.getItem(i).hasBennCaught == true){
                    //Toast.makeText(getContext(), "GridView Item: " + adapter.getItem(i).monsterID, Toast.LENGTH_LONG).show();

                    MonsterBioFragment newFragment = MonsterBioFragment.newInstance(adapter.getItem(i).monsterName);

                    FragmentTransaction transaction =  getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, newFragment);
                    transaction.addToBackStack(null);

                    arrayOfMonsterCollections.clear();

                    // Commit the transaction
                    transaction.commit();
                }
            }
        });

        return rootView;
    }

}
