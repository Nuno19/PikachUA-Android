package com.cm.pikachua;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestockActivity extends AppCompatActivity {

    GridView gridView;

    ArrayList<ItemFirebase> items = new ArrayList<ItemFirebase>();
    ItemFirebase it = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);


        String s = getIntent().getStringExtra("ID");

        Toast.makeText(getApplicationContext(), "PokéStop: " + s, Toast.LENGTH_LONG).show();

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Voltar", Toast.LENGTH_LONG).show();

                onBackPressed();

            }
        });


        /*ImageView imageView = findViewById(R.id.);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), "Top: Got " + (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "Right: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "Left: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), "Bottom: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }

        });*/


        FloatingActionButton pokestop = (FloatingActionButton) findViewById(R.id.pokestop);

        pokestop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gridView = (GridView) findViewById(R.id.gridViewItems);



                //generateItems();
                ItemFirebase i = new ItemFirebase("1", "Pokeball", "lalala", "0.5", "0.5", "pokeball_sprite.png");
                items.add(i);
                i = new ItemFirebase("1", "Great Ball", "lalala", "0.5", "0.5", "greatball_sprite.png");
                items.add(i);
                i = new ItemFirebase("1", "Great Ball", "lalala", "0.5", "0.5", "greatball_sprite.png");
                items.add(i);
                Toast.makeText(getApplicationContext(), items.get(0).getName(), Toast.LENGTH_SHORT).show();
                gridView.setAdapter(new RestockAdapter(getApplicationContext(), items));

                /*for(int k=0;k<items.size();k++){
                    updateBag(items.get(k));
                }*/


            }
        });


        }


        public void generateItems(){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items");
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    ArrayList<ItemFirebase> list_items = new ArrayList<ItemFirebase>();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        list_items.add(postSnapshot.getValue(ItemFirebase.class));
                    }

                    for(int i=0; i<5;i++) {
                        int random = (int) (Math.random() * 100 + 1);

                        int k = 0;
                        for (ItemFirebase item : list_items) {
                            int v = (int) (Double.parseDouble(item.getDrop_rate()) * 100) + k;
                            k = v;
                            if (random < v) {
                                items.add(item);
                                it = item;
                                break;
                            }
                        }
                    }

                    //Toast.makeText(getApplicationContext(), it.getName(), Toast.LENGTH_SHORT).show();
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

        public void updateBag(final ItemFirebase item){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items_inst");
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    ItemInst item_inst = null;
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        item_inst = postSnapshot.getValue(ItemInst.class);

                        if(item_inst.getUser_id().equals("1") && item_inst.getItem_id().equals(item.getId())){
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference("items_inst");
                            item_inst.setAmount(Integer.parseInt(item_inst.getAmount())+1);
                            database.child(String.valueOf(item_inst.getId())).setValue(item_inst);
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
    }
