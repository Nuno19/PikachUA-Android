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
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    int number = 0;

    ArrayList<MonsterCollection> arrayOfMonsterCollections = new ArrayList<MonsterCollection>();
    ArrayList<String> list_pokedex = new ArrayList<String>();
    private String personID;
    PokedexInst mon;

    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collection, container, false);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            personID = acct.getId();
        }

        FloatingActionButton button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        final MonsterCollectionAdapter adapter = new MonsterCollectionAdapter(getContext(), arrayOfMonsterCollections);

        loadPokedex(rootView, adapter);

        final GridView androidGridView = (GridView) rootView.findViewById(R.id.gridView);
        androidGridView.setAdapter(adapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {

                if (adapter.getItem(i).hasBennCaught == true){

                    MonsterBioFragment newFragment = MonsterBioFragment.newInstance(adapter.getItem(i).monsterID);

                    FragmentTransaction transaction =  getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.fragment_container, newFragment);
                    transaction.addToBackStack(null);

                    arrayOfMonsterCollections.clear();
                    number=0;

                    // Commit the transaction
                    transaction.commit();
                }
            }
        });

        return rootView;
    }

    public void loadPokedex(final View rootView, final MonsterCollectionAdapter adapter){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pokedex");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mon = null;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    mon = postSnapshot.getValue(PokedexInst.class);
                    if(mon.getUser_id().equals(personID)) {
                        list_pokedex.add(mon.getPokemon_id());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        reference.addValueEventListener(postListener);


        final DatabaseReference pokemons_p = FirebaseDatabase.getInstance().getReference("pokemons");
        ValueEventListener listenerpokemons_P = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Pokemon pokemon = null;
                number=0;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    pokemon = postSnapshot.getValue(Pokemon.class);
                        if( list_pokedex.contains(pokemon.getId())) {
                            MonsterCollection x = new MonsterCollection(pokemon.getId(), pokemon.getImage(), true);
                            adapter.add(x);
                            number++;
                        }
                        else{
                            MonsterCollection x = new MonsterCollection(pokemon.getId(), pokemon.getImage(), false);
                            adapter.add(x);
                        }
                }

                TextView t = rootView.findViewById(R.id.total1);
                t.setText("Max: " + number + "/" + adapter.getCount());

                list_pokedex.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        pokemons_p.addValueEventListener(listenerpokemons_P);
    }

}