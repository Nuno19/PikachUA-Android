package com.cm.pikachua;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
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
public class StorageActivity extends AppCompatActivity {

    ArrayList<MonsterStorage> arrayOfMonsterStorage = new ArrayList<MonsterStorage>();
    private ArrayList<String> selectedMonsters;
    CharSequence[] values = {" Sort By Date "," Sort By Alphabetic Order "};
    AlertDialog alertDialog1;
    Editable YouEditTextValue;
    boolean searching = false;

    int choice = 0;
    String string = "id";
    private String personID;
    private String substring;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(StorageActivity.this);
        if (acct != null) {
            personID = acct.getId();
        }

        final MonsterStorageAdapter adapter = new MonsterStorageAdapter(StorageActivity.this, arrayOfMonsterStorage);
        selectedMonsters = new ArrayList<>();
        final TextView text_selected = findViewById(R.id.selected);
        text_selected.setText(selectedMonsters.size() + " selected");

        final FloatingActionButton button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (searching == false){
                    AlertDialog.Builder alert = new AlertDialog.Builder(StorageActivity.this);
                    final EditText edittext = new EditText(StorageActivity.this);
                    edittext.setText(YouEditTextValue);
                    alert.setTitle("Search");

                    alert.setView(edittext);

                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            YouEditTextValue = edittext.getText();
                            substring = YouEditTextValue.toString();
                            searching = true;
                            button_search.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
                            loadStorage(adapter);
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
                    loadStorage(adapter);
                }
            }
        });

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Log.d("T","e");

        loadStorage(adapter);

        final GridView androidGridView = (GridView) findViewById(R.id.gridView);
        androidGridView.setAdapter(adapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                //Toast.makeText(getContext(), "ListView Item: " + adapter.getItem(i).monsterId, Toast.LENGTH_LONG).show();

                int selectedIndex = adapter.selectedPositions.indexOf(i);

                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    selectedMonsters.remove(adapter.getItem(i).monsterId);
                } else {
                    adapter.selectedPositions.add(i);
                    selectedMonsters.add(adapter.getItem(i).monsterId);
                }
                adapter.getView(i,view,parent);
                TextView text_selected = findViewById(R.id.selected);
                text_selected.setText(selectedMonsters.size() + " selected");
            }
        });

        Button button_transfer = findViewById(R.id.button_transfer);
        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(StorageActivity.this);
                if (selectedMonsters.isEmpty()){
                    Toast.makeText(StorageActivity.this, "No monsters selected!", Toast.LENGTH_LONG).show();
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
                                selectedMonsters.clear();
                                adapter.selectedPositions.clear();
                                text_selected.setText(selectedMonsters.size() + " selected");
                                loadStorage(adapter);
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

        Button button_sort = findViewById(R.id.button_sort);
        button_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialogWithRadioButtonGroup(adapter);
            }
        });
    }

    public void CreateAlertDialogWithRadioButtonGroup(final MonsterStorageAdapter adapter){

        AlertDialog.Builder builder = new AlertDialog.Builder(StorageActivity.this);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(values, choice, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        choice = 0;
                        string = "id";
                        loadStorage(adapter);
                        break;
                    case 1:
                        choice = 1;
                        string = "nickname";
                        loadStorage(adapter);
                        break;
                    default:
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    public void loadStorage(final MonsterStorageAdapter adapter){

        Query pokemonsInst = FirebaseDatabase.getInstance().getReference("pokemonsInst").orderByChild(string);

        if (searching == true){
            pokemonsInst = FirebaseDatabase.getInstance().getReference("pokemonsInst").orderByChild("nickname").startAt(substring.substring(0, 1).toUpperCase() + substring.substring(1,substring.length()-1).toLowerCase()).endAt(substring.substring(0, 1).toUpperCase() + substring.substring(1,substring.length()-1).toLowerCase()+"\uf8ff");
        }

        ValueEventListener listenerPokemonInst = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                PokemonInst pokemon_inst = null;
                adapter.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    pokemon_inst = postSnapshot.getValue(PokemonInst.class);
                    if(pokemon_inst.getUser_id().equals(personID)){
                        MonsterStorage x = new MonsterStorage(pokemon_inst.getId(), pokemon_inst.getNickname(), pokemon_inst.getImage(), Integer.parseInt(pokemon_inst.getValue()));
                        adapter.add(x);
                    }

                }
                int total = 200;

                TextView t = findViewById(R.id.total1);
                t.setText("Max: " + adapter.getCount() + "/" + total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        pokemonsInst.addListenerForSingleValueEvent(listenerPokemonInst);
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
