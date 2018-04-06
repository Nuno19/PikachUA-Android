package com.cm.pikachua;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StorageFragment extends Fragment {

    ArrayList<MonsterStorage> arrayOfMonsterStorage = new ArrayList<MonsterStorage>();
    private ArrayList<String> selectedMonsters;
    CharSequence[] values = {" Sort By Alphabetic Order "," Sort By Number "," Sort By Better Stat "};
    AlertDialog alertDialog1;
    Editable YouEditTextValue;
    boolean searching = false;


    int choice = 0;

    String[] gridViewStat = {};
    String[] gridViewString = {};
    int[] gridViewImageId = {R.drawable.mewtwo, R.drawable.mewtwo};

    int index = 0;
    private String personID;

    public StorageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_storage, container, false);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            personID = acct.getId();
        }

        final FloatingActionButton button_search = rootView.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (searching == false){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    final EditText edittext = new EditText(getContext());
                    edittext.setText(YouEditTextValue);
                    alert.setTitle("Search");

                    alert.setView(edittext);

                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            YouEditTextValue = edittext.getText();
                            searching = true;
                            button_search.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
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

        FloatingActionButton button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Voltar", Toast.LENGTH_LONG).show();

                getActivity().onBackPressed();
            }
        });


        final MonsterStorageAdapter adapter = new MonsterStorageAdapter(getContext(), arrayOfMonsterStorage);
        selectedMonsters = new ArrayList<>();

        Log.d("T","e");

        loadStorage(rootView, adapter);

        final GridView androidGridView = (GridView) rootView.findViewById(R.id.gridView);
        androidGridView.setAdapter(adapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                //Toast.makeText(getContext(), "ListView Item: " + adapter.getItem(i).monsterId, Toast.LENGTH_LONG).show();

                int selectedIndex = adapter.selectedPositions.indexOf(i);

                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    selectedMonsters.remove(adapter.getItem(i));
                } else {
                    adapter.selectedPositions.add(i);
                    selectedMonsters.add(adapter.getItem(i).monsterId);
                }
                adapter.getView(i,view,parent);
            }
        });

        Button button_transfer = rootView.findViewById(R.id.button_transfer);
        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                if (selectedMonsters.isEmpty()){
                    Toast.makeText(getContext(), "No monsters selected!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (selectedMonsters.size() == 1){
                    builder1.setMessage("Do you want to transfer 1 monster?");
                }
                else {
                    builder1.setMessage("Do you want to transfer " + selectedMonsters.size() + " monsters?");
                }

                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i = 0; i < selectedMonsters.size(); i++){
                                    transferMonster(String.valueOf(selectedMonsters.get(i)));
                                }
                                arrayOfMonsterStorage.clear();
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
                if (selectedMonsters.isEmpty()){
                    Toast.makeText(getContext(), "No monsters selected!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (selectedMonsters.size() == 1){
                    builder1.setMessage("Do you want to trade 1 monster?");
                }
                else {
                    builder1.setMessage("Do you want to trade " + selectedMonsters.size() + " monsters?");
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
                        //Toast.makeText(getContext(), "Sort By Alphabetic Order", Toast.LENGTH_LONG).show();
                        choice = 0;
                        break;
                    case 1:
                        //Toast.makeText(getContext(), "Sort By Number", Toast.LENGTH_LONG).show();
                        choice = 1;
                        break;
                    case 2:
                        //Toast.makeText(getContext(), "Sort By Better Stat", Toast.LENGTH_LONG).show();
                        choice = 2;
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    public void loadStorage(final View rootView, final MonsterStorageAdapter adapter){

        DatabaseReference pokemonsInst = FirebaseDatabase.getInstance().getReference("pokemonsInst");

        ValueEventListener listenerPokemonInst = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                PokemonInst pokemon_inst = null;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    pokemon_inst = postSnapshot.getValue(PokemonInst.class);
                    if(pokemon_inst.getUser_id().equals(personID)){
                        MonsterStorage x = new MonsterStorage(pokemon_inst.getId(), pokemon_inst.getNickname(), pokemon_inst.getImage(), Integer.parseInt(pokemon_inst.getValue()));
                        adapter.add(x);
                    }

                }
                int total = 200;

                TextView t = rootView.findViewById(R.id.total1);
                t.setText("Max: " + adapter.getCount() + "/" + total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        pokemonsInst.addValueEventListener(listenerPokemonInst);
    }


    public void transferMonster(final String id){

        DatabaseReference pokemonsInst = FirebaseDatabase.getInstance().getReference("pokemonsInst");
        Query monsterToTransfer = pokemonsInst.child(id);

        monsterToTransfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        });

    }


}
