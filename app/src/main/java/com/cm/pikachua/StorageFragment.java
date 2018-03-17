package com.cm.pikachua;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageFragment extends Fragment {

    ArrayList<MonsterStorage> arrayOfMonsterStorage = new ArrayList<MonsterStorage>();
    private ArrayList<String> selectedStrings;
    CharSequence[] values = {" Sort By Alphabetic Order "," Sort By Number "," Sort By Better Stat "};
    AlertDialog alertDialog1;
    Editable YouEditTextValue;
    boolean searching = false;

    int choice = 0;

    int[] gridViewStat = {
            100,100,100,100,100,100,
            100,100,100,100,100,100,
            100,100,100,100,100,100,
    };

    String[] gridViewString = {
            "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo",
            "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo",
            "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo", "Mewtwo"

    } ;
    int[] gridViewImageId = {
            R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo,
            R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo,
            R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo, R.drawable.mewtwo

    };

    public StorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_storage, container, false);

        final Button button_search = rootView.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Search", Toast.LENGTH_LONG)
                        .show();

                if (searching == false){
                    searching = true;
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    final EditText edittext = new EditText(getContext());
                    edittext.setText(YouEditTextValue);
                    alert.setTitle("Search");

                    alert.setView(edittext);

                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            YouEditTextValue = edittext.getText();
                            button_search.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    alert.show();
                }
                else {
                    searching = false;
                    button_search.getBackground().clearColorFilter();
                    YouEditTextValue = null;
                }



            }
        });

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
        selectedStrings = new ArrayList<>();

        Log.d("T","e");

        for (int i=0;i<gridViewString.length;i++){
            MonsterStorage x = new MonsterStorage(i, gridViewString[i], gridViewImageId[i], gridViewStat[i]);
            adapter.add(x);
        }

        int total = 200;

        TextView t = rootView.findViewById(R.id.total1);
        t.setText("Max: " + adapter.getCount() + "/" + total);


        final GridView androidGridView = (GridView) rootView.findViewById(R.id.gridView);
        androidGridView.setAdapter(adapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "ListView Item: " + adapter.getItem(i).monsterId, Toast.LENGTH_LONG).show();

                int selectedIndex = adapter.selectedPositions.indexOf(i);

                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    selectedStrings.remove(Integer.toString(adapter.getItem(i).monsterId));
                } else {
                    adapter.selectedPositions.add(i);
                    selectedStrings.add(Integer.toString(adapter.getItem(i).monsterId));
                }
                adapter.getView(i,view,parent);
            }
        });

        Button button_transfer = rootView.findViewById(R.id.button_transfer);
        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                if (selectedStrings.isEmpty()){
                    Toast.makeText(getContext(),
                            "No monsters selected!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                else if (selectedStrings.size() == 1){
                    builder1.setMessage("Do you want to transfer 1 monster?");
                }
                else {
                    builder1.setMessage("Do you want to transfer " + selectedStrings.size() + " monsters?");
                }

                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        Button button_exchange = rootView.findViewById(R.id.button_exchange);
        button_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                if (selectedStrings.isEmpty()){
                    Toast.makeText(getContext(),
                            "No monsters selected!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                else if (selectedStrings.size() == 1){
                    builder1.setMessage("Do you want to trade 1 monster?");
                }
                else {
                    builder1.setMessage("Do you want to trade " + selectedStrings.size() + " monsters?");
                }

                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        Button button_sort = rootView.findViewById(R.id.button_sort);
        button_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Sort", Toast.LENGTH_LONG)
                        .show();

                CreateAlertDialogWithRadioButtonGroup();
            }
        });

        return rootView;
    }

    public void CreateAlertDialogWithRadioButtonGroup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(values, choice, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        Toast.makeText(getContext(), "Sort By Alphabetic Order", Toast.LENGTH_LONG).show();
                        choice = 0;
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Sort By Number", Toast.LENGTH_LONG).show();
                        choice = 1;
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Sort By Better Stat", Toast.LENGTH_LONG).show();
                        choice = 2;
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

}
