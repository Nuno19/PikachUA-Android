package com.cm.pikachua;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CatchActivity extends AppCompatActivity {

    AlertDialog alertDialog1, alertDialog2;
    CharSequence[] values_ball = {" PokeBall (0) "," Great Ball (1) "," Ultra Ball (2)"};
    CharSequence[] values_berry = {"None (0) ", "Razz Berry (1) "," Golden Razz Berry (2) "};
    int choice_ball = 0;
    int choice_berry = 0;
    int j = 0;
    Pokemon pokemonToCatch = null;
    String cp = "0";
    int next_id = 0;
    private String personID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personID = acct.getId();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pokemons");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ArrayList<Pokemon> list_pokemons = new ArrayList<Pokemon>();
                Pokemon mon = null;
                int i=0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    list_pokemons.add(postSnapshot.getValue(Pokemon.class));
                }

                int random = (int )(Math.random() * 100 + 1);

                int k=1;
                for(Pokemon pokemon : list_pokemons){
                    int v = (int)(Double.parseDouble(pokemon.getSpawnRate())*100) * k;
                    k++;
                    if(random < v) {
                        setPokemon(getWindow().getDecorView().getRootView(), pokemon);
                        pokemonToCatch = pokemon;
                        break;
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

        getnextID();

        String s = getIntent().getStringExtra("ID");
        //String s = pokemonToCatch.getName();


        Toast.makeText(CatchActivity.this, "Pokémon: " + s, Toast.LENGTH_LONG).show();

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Voltar", Toast.LENGTH_LONG).show();

                onBackPressed();

            }
        });

        Button button_balls = findViewById(R.id.button_balls);
        button_balls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Ball", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(CatchActivity.this);
                builder1.setTitle("Select Your Ball");
                builder1.setSingleChoiceItems(values_ball, choice_ball, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "PokeBall", Toast.LENGTH_LONG).show();
                                choice_ball = 0;
                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Great Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 1;
                                break;
                            case 2:
                                //Toast.makeText(CatchActivity.this, "Ultra Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 2;
                                break;
                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder1.create();
                alertDialog1.show();
            }
        });

        Button button_berries = findViewById(R.id.button_berries);
        button_berries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Berries", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder2 = new AlertDialog.Builder(CatchActivity.this);
                builder2.setTitle("Select Your Berry");
                builder2.setSingleChoiceItems(values_berry, choice_berry, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "None", Toast.LENGTH_LONG).show();
                                choice_berry = 0;
                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Razz Berry", Toast.LENGTH_LONG).show();
                                choice_berry = 1;
                                break;
                            case 4:
                                //Toast.makeText(CatchActivity.this, "Golden Razz Berry", Toast.LENGTH_LONG).show();
                                choice_berry = 2;
                                break;
                        }
                        alertDialog2.dismiss();
                    }
                });
                alertDialog2 = builder2.create();
                alertDialog2.show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int catchSimValue = catchSimulation(pokemonToCatch, choice_berry, choice_ball);

                if (catchSimValue == 1){
                    Toast.makeText(CatchActivity.this,
                            "Got it!", Toast.LENGTH_LONG)
                            .show();



                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("pokemonsInst");
                    PokemonInst pokemon_inst = new PokemonInst(personID + "_" + String.format("%012d", next_id), pokemonToCatch.getId(), personID, pokemonToCatch.getName(), cp, pokemonToCatch.getImage());
                    database.child(personID + "_" + String.format("%012d", next_id)).setValue(pokemon_inst);

                    onBackPressed();
                }
                else if (catchSimValue == 2){
                    Toast.makeText(CatchActivity.this,
                            "Oh No! It escaped!", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    Toast.makeText(CatchActivity.this,
                            "Oh No! It ran away!", Toast.LENGTH_LONG)
                            .show();
                    onBackPressed();
                }
            }
        });
    }


    public void setPokemon(View view, Pokemon pokemon){
        TextView text = (TextView) view.findViewById(R.id.title1);
        int random_cp = (int )(Math.random() * 2000 + 1);
        text.setText(pokemon.getName() + " " + random_cp);
        cp = String.valueOf(random_cp);


        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(pokemon.getImage());
        ImageView image = (ImageView)findViewById(R.id.pokemonImage);

        // Load the image using Glide
        Context activitiyContext = this.getApplicationContext();
        Glide.with(activitiyContext).using(new FirebaseImageLoader()).load(storageReference).into(image);

    }


    public void getnextID(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pokemonsInst");
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                PokemonInst pokemon = null;
                next_id = 0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    pokemon = postSnapshot.getValue(PokemonInst.class);
                    if (pokemon.getUser_id().equals(personID)){
                        next_id = Integer.parseInt(pokemon.getId().split("_")[1]) + 1;
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

    }


    public int catchSimulation(Pokemon pokemon, int berry, int ball){
        int catchRate = (int)(Double.parseDouble(pokemon.getCatchRate())*100);
        int fleeRate = (int)(Double.parseDouble(pokemon.getFleeRate())*100);
        switch (berry){
            case 1:
                catchRate *= 1.5;
                break;
            case 2:
                catchRate *= 2.5;
                break;
            default:
                break;
        }

        switch (ball){
            case 0:
                break;
            case 1:
                catchRate *= 1.5;
                break;
            case 2:
                catchRate *= 2.0;
                break;
        }

        int random_value_catch = (int )(Math.random() * 100 + 1);

        if(random_value_catch < catchRate)
            return 1;

        int random_value_flee = (int )(Math.random() * 100 + 1);

        if(random_value_flee < fleeRate)
            return  3;

        return  2;

    }
}
