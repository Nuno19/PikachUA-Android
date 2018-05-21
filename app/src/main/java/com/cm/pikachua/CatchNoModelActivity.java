package com.cm.pikachua;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cm.entities.Pokemon;
import com.cm.entities.User;
import com.cm.instances.ItemInst;
import com.cm.instances.PokedexInst;
import com.cm.instances.PokemonInst;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CatchNoModelActivity extends AppCompatActivity {

    AlertDialog alertDialog1, alertDialog2;
    CharSequence[] values_ball = {" PokeBall: 0 ", " Great Ball: 0 ", " Ultra Ball: 0 "};
    CharSequence[] values_berry = {" None", " Razz Berry: 0 ", " Golden Razz Berry: 0 "};
    int choice_ball = 0;
    int choice_berry = 0;
    Pokemon pokemonToCatch = null;
    String cp = "0";
    int next_id = 0;
    private String personID;
    private String pokemon_id;
    private int[] num_items_bag = {0,0,0,0,0};
    private boolean with_berry, valid_berry, valid_ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_catch_no_model );

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount( getApplicationContext() );
        if (acct != null) {
            personID = acct.getId();
        }

        pokemon_id = getIntent().getStringExtra( "ID" );

        number_of_items();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "pokemons" );

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Pokemon mon = null;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    mon = postSnapshot.getValue(Pokemon.class);
                    if(mon.getId().equals(pokemon_id)) {
                        setPokemon( getWindow().getDecorView().getRootView(), mon );
                        pokemonToCatch = mon;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException() );
                // ...
            }
        };
        reference.addValueEventListener( postListener );

        getnextID();

        //String s = pokemonToCatch.getName();

        final FloatingActionButton button_back = findViewById( R.id.button_back );
        button_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Voltar", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        } );

        final ImageButton button_balls = findViewById( R.id.button_balls );
        button_balls.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Ball", Toast.LENGTH_LONG).show();

                number_of_items();

                AlertDialog.Builder builder1 = new AlertDialog.Builder( CatchNoModelActivity.this );
                builder1.setTitle( "Select Your Ball" );
                builder1.setSingleChoiceItems( values_ball, choice_ball, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "PokeBall", Toast.LENGTH_LONG).show();
                                choice_ball = 0;

                                button_balls.setImageResource(R.drawable.pokeball_sprite);

                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Great Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 1;

                                button_balls.setImageResource(R.drawable.greatball_sprite);

                                break;
                            case 2:
                                //Toast.makeText(CatchActivity.this, "Ultra Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 2;

                                button_balls.setImageResource(R.drawable.ultraball_sprite);

                                break;
                        }
                        alertDialog1.dismiss();
                    }
                } );
                alertDialog1 = builder1.create();
                alertDialog1.show();
            }
        } );

        final ImageButton button_berries = findViewById( R.id.button_berries );
        button_berries.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Berries", Toast.LENGTH_LONG).show();

                number_of_items();

                AlertDialog.Builder builder2 = new AlertDialog.Builder( CatchNoModelActivity.this );
                builder2.setTitle( "Select Your Berry" );
                builder2.setSingleChoiceItems( values_berry, choice_berry, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "None", Toast.LENGTH_LONG).show();
                                choice_berry = 0;

                                button_berries.setImageResource(R.drawable.ic_launcher_background);

                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Razz Berry", Toast.LENGTH_LONG).show();
                                choice_berry = 1;

                                button_berries.setImageResource(R.drawable.item_0701);

                                break;
                            case 2:

                                button_berries.setImageResource(R.drawable.item_0706);

                                choice_berry = 2;
                                break;
                        }
                        alertDialog2.dismiss();
                    }
                } );
                alertDialog2 = builder2.create();
                alertDialog2.show();
            }
        } );

        FloatingActionButton fab = findViewById( R.id.floatingActionButton );
        fab.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {

                int catchSimValue = catchSimulation( pokemonToCatch, choice_berry, choice_ball );

                if (catchSimValue == 1) {
                    Toast.makeText( CatchNoModelActivity.this,
                            "Got it!", Toast.LENGTH_LONG )
                            .show();

                    updatePlayer(1000,true);

                    DatabaseReference database2 = FirebaseDatabase.getInstance().getReference( "pokedex" );
                    PokedexInst pokedex_inst = new PokedexInst(personID + "_" + String.format( "%03d", Integer.parseInt(pokemonToCatch.getId())), pokemonToCatch.getId(), personID, pokemonToCatch.getName(), pokemonToCatch.getImage());
                    database2.child(personID + "_" + String.format( "%03d", Integer.parseInt(pokemonToCatch.getId()) )).setValue(pokedex_inst);

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference( "pokemonsInst" );
                    PokemonInst pokemon_inst = new PokemonInst( personID + "_" + String.format( "%012d", next_id ), pokemonToCatch.getId(), personID, pokemonToCatch.getName(), cp, pokemonToCatch.getImage() );
                    database.child( personID + "_" + String.format( "%012d", next_id ) ).setValue( pokemon_inst );

                    onBackPressed();
                } else if (catchSimValue == 2) {
                    Toast.makeText( CatchNoModelActivity.this,
                            "Oh No! It escaped!", Toast.LENGTH_LONG )
                            .show();
                    number_of_items();

                } else if (catchSimValue == 3) {
                    Toast.makeText( CatchNoModelActivity.this,
                            "Oh No! It ran away!", Toast.LENGTH_LONG )
                            .show();
                    updatePlayer(250,false);
                    onBackPressed();
                }
            }
        } );
    }

    public void setPokemon(View view, Pokemon pokemon) {
        TextView text = (TextView) view.findViewById( R.id.title1 );
        int random_cp = (int) (Math.random() * 2000 + 1);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(pokemon.getImage());
        ImageView imageViewAndroid = (ImageView) view.findViewById(R.id.pokemonImage);
        // Load the image using Glide

        Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(storageReference).into(imageViewAndroid);

        text.setText( pokemon.getName() + ": " + random_cp );
        cp = String.valueOf( random_cp );
    }


    public void getnextID() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference( "pokemonsInst" );
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                PokemonInst pokemon = null;
                next_id = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    pokemon = postSnapshot.getValue( PokemonInst.class );
                    if (pokemon.getUser_id().equals( personID )) {
                        next_id = Integer.parseInt( pokemon.getId().split( "_" )[1] ) + 1;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w( "loadPost:onCancelled", databaseError.toException() );
                // ...
            }
        };
        reference.addValueEventListener( postListener );

    }


    public int catchSimulation(Pokemon pokemon, int berry, int ball) {

        number_of_items();

        if (berry != 0){
            with_berry = true;
            if (num_items_bag[berry+2] != 0){
                valid_berry = true;
            }
            else{
                Toast.makeText( this, "Out of these berries", Toast.LENGTH_LONG ).show();
                valid_berry = false;
            }
        }
        else{
            with_berry = false;
            valid_berry = true;
        }

        if (num_items_bag[ball] != 0){
            valid_ball = true;
        }
        else{
            Toast.makeText( this, "Out of these pokéballs", Toast.LENGTH_LONG ).show();
            valid_ball = false;
        }

        if (valid_berry == true && valid_ball == true){
            if (with_berry == true){
                updateBag(Integer.toString(berry+2));
            }

            updateBag(Integer.toString(ball));
        }
        else {
            return 4;
        }


        int catchRate = (int) (Double.parseDouble( pokemon.getCatchRate() ) * 100);
        int fleeRate = (int) (Double.parseDouble( pokemon.getFleeRate() ) * 100);
        switch (berry) {
            case 1:
                catchRate *= 1.5;
                break;
            case 2:
                catchRate *= 2.5;
                break;
            default:
                break;
        }

        switch (ball) {
            case 0:
                break;
            case 1:
                catchRate *= 1.5;
                break;
            case 2:
                catchRate *= 2.0;
                break;
        }

        int random_value_catch = (int) (Math.random() * 100 + 1);

        if (random_value_catch < catchRate)
            return 1;

        int random_value_flee = (int) (Math.random() * 100 + 1);

        if (random_value_flee < fleeRate)
            return 3;

        return 2;

    }

    public void number_of_items(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("items_inst").orderByChild("user_id").startAt(personID).endAt(personID + "\uf8ff");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ItemInst item_inst = null;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);
                    num_items_bag[Integer.parseInt( item_inst.getItem_id() )] = item_inst.getAmount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        query.addValueEventListener(postListener);

        values_ball = new CharSequence[]{" PokeBall: " + num_items_bag[0], " Great Ball: " + num_items_bag[1], " Ultra Ball: " + num_items_bag[2]};
        values_berry = new CharSequence[]{" None", " Razz Berry: " + num_items_bag[3], " Golden Razz Berry: " + +num_items_bag[4]};
    }

    public void updateBag(final String k){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items_inst");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ItemInst item_inst = null;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);

                    if(item_inst.getUser_id().equals(personID) && item_inst.getItem_id().equals(k)){
                        postSnapshot.getRef().child("amount").setValue(item_inst.getAmount()-1);
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
        reference.addListenerForSingleValueEvent(postListener);
    }

    public void updatePlayer(final int xp, final boolean caught_monster){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user = null;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    user = postSnapshot.getValue(User.class);

                    if(user.getId().equals(personID)){
                        postSnapshot.getRef().child("totalXP").setValue(String.valueOf(Integer.parseInt(user.getTotalXP())+xp));
                        if (caught_monster == true){
                            postSnapshot.getRef().child("monstersCaught").setValue(String.valueOf(Integer.parseInt(user.getMonstersCaught())+1));
                        }
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
        reference.addListenerForSingleValueEvent(postListener);
    }
}