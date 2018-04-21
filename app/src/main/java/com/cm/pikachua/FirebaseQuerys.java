package com.cm.pikachua;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class FirebaseQuerys extends UnityPlayerActivity {
    private int userID;

    public static String[] getItems(final String userId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items_inst");
        final String[] num = new String[1];
        num[0] = new String("Hello");
        // Attach a listener to read the data at our posts reference
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ItemInst item_inst = null;
                int i = 0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);
                    if (item_inst.getUser_id().equals(userId )){

                        num[i] = item_inst.getItem_id();
                        num[i + 1] = String.valueOf(item_inst.getAmount());

                        i+=2;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };

        reference.addListenerForSingleValueEvent(valueEventListener);
    return num;
    }


    public static int getPokemonCatchRate(final String pokemonID){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("pokemons");
        final String[] num = new String[1];
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pokemon pokemon = null;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Pokemon poke= postSnapshot.getValue(Pokemon.class);
                    if (poke.getId().equals(pokemonID) ){
                        num[0] = poke.getCatchRate();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return 0;
    }
    public static int getPokemonFleeRate(final String pokemonID){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("pokemons");
        final String[] num = new String[1];
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pokemon pokemon = null;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Pokemon poke= postSnapshot.getValue(Pokemon.class);
                    if (poke.getId().equals(pokemonID) ){
                        num[0] = poke.getFleeRate();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return 0;
    }



    public static String getPokemonName(final String pokemonID){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("pokemons");
        final String[] name = new String[1];
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pokemon pokemon = null;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Pokemon poke= postSnapshot.getValue(Pokemon.class);
                    if (poke.getId().equals(pokemonID) ){
                        name[0] = poke.getName();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return name[0];

    }
    public static String getPokemonImage(final String pokemonID){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("pokemons");
        final String[] image = new String[1];
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pokemon pokemon = null;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Pokemon poke= postSnapshot.getValue(Pokemon.class);
                    if (poke.getId().equals(pokemonID) ){
                        image[0] = poke.getImage();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return image[0];

    }

    public static boolean addPokemonPokedex(final String pokemonID, final String userID){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pokedex");
        Map<String,PokedexInst> pokemon = new HashMap<>();

        String ID = userID + "_" + String.format( Locale.getDefault(),"%03d",Integer.valueOf(pokemonID));
        PokedexInst poke = new PokedexInst(ID,pokemonID,userID,getPokemonName(pokemonID),getPokemonImage(pokemonID));

        pokemon.put(ID,poke);
        final boolean[] complete = new boolean[1];
        ref.setValue(poke, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                complete[0] =true;
            }
        });

        return complete[0];
    }
    public static boolean addPokemonStorage(String pokemonID, String userID,String pokemonCP){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pokemons");
        Map<String,PokemonInst> pokemon = new HashMap<>();
        String ID = userID + "_" + pokemonID + "_" + pokemonCP + "_" + new Random().nextInt(100000000);
        PokemonInst poke = new PokemonInst(ID,pokemonID,userID,getPokemonName(pokemonID),pokemonCP,getPokemonImage(pokemonID));

        pokemon.put(ID,poke);
        final boolean[] complete = new boolean[1];
        ref.setValue(poke, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                complete[0] =true;
            }
        });

        return complete[0];
    }
    public static boolean updateItemAmounts(final int[] amounts, final String userId) {
        final HashMap<String, ItemInst> toAdd = new HashMap<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items_inst");

        // Attach a listener to read the data at our posts reference
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ItemInst item_inst = null;
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);
                    if (item_inst.getUser_id().equals(userId)) {
                        item_inst.setAmount(amounts[i++]);
                        toAdd.put(item_inst.getId(), item_inst);
                    }
                }
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("items_inst");
                ref.setValue(toAdd, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Log.d("UPDATEITEMS", "Complete");
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(valueEventListener);
        return true;
    }


    public static int getGreatballs(final String userId){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("items_inst");
        final int[] num = new int[1];
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ItemInst greatballs = null;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    greatballs = postSnapshot.getValue(ItemInst.class);
                    if (greatballs.getUser_id() == userId && greatballs.getName() == "Great Ball"){
                        num[0] = greatballs.getAmount();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return num[0];
    }
}

