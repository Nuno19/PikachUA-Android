package com.cm.pikachua;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.cm.adapters.RestockAdapter;
import com.cm.entities.ItemFirebase;
import com.cm.entities.User;
import com.cm.instances.ItemInst;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestockActivity extends AppCompatActivity {

    GridView gridView;

    ArrayList<ItemFirebase> items = new ArrayList<ItemFirebase>();
    ItemFirebase it = null;
    String person_id;
    private boolean gotItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(acct != null){
            person_id = acct.getId();
        }

        gotItems = false;

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        final FloatingActionButton pokestop = (FloatingActionButton) findViewById(R.id.pokestop);
        pokestop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gridView = (GridView) findViewById(R.id.gridViewItems);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("items_inst").orderByChild("user_id").startAt(person_id).endAt(person_id + "\uf8ff");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        ItemInst item_inst = null;
                        int num_items_bag = 0;

                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            item_inst = postSnapshot.getValue(ItemInst.class);
                            num_items_bag += item_inst.getAmount();
                        }

                        if (gotItems == false){
                            if(num_items_bag <= 200){
                                generateItems();
                                updateXP(500);
                                Toast.makeText(getApplicationContext(), "Got 500 XP", Toast.LENGTH_LONG).show();
                                gotItems = true;
                                pokestop.setImageResource(R.drawable.pokestop_used);
                            }

                            else{
                                Toast.makeText(getApplicationContext(), "Your bag is full!", Toast.LENGTH_LONG).show();
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
                query.addListenerForSingleValueEvent(postListener);
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

                items.clear();
                for(int i=0; i<3;i++) {
                    int random = (int) (Math.random() * 100 + 1);

                    int k = 0;
                    for (ItemFirebase item : list_items) {
                        int v = (int) (Double.parseDouble(item.getDrop_rate()) * 100) + k;
                        k = v;
                        if (random <= v) {
                            items.add(item);
                            break;
                        }
                    }
                }

                gridView.setAdapter(new RestockAdapter(getApplicationContext(), items));
                ArrayList<ItemFirebase> items_copy = new ArrayList<ItemFirebase>();

                for(int index=0;index<items.size();index++){
                    items_copy.add(items.get(index));
                }

                int[] num_un_item = {0,0,0,0,0};

                for(int i=0;i<items_copy.size();i++){
                    num_un_item[Integer.parseInt(items_copy.get(i).getId())]+=1;
                }


                for(int k=0;k<num_un_item.length;k++){
                    if(num_un_item[k] != 0){
                        updateBag(String.valueOf(k), num_un_item[k]);
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
        //reference.addValueEventListener(postListener);
        reference.addListenerForSingleValueEvent(postListener);

    }


    public void updateBag(final String k, final int num_items){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items_inst");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ItemInst item_inst = null;

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);

                    if(item_inst.getUser_id().equals(person_id) && item_inst.getItem_id().equals(k)){
                        postSnapshot.getRef().child("amount").setValue(item_inst.getAmount()+num_items);
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

    public void updateXP(final int xp){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user = null;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    user = postSnapshot.getValue(User.class);

                    if(user.getId().equals(person_id)){
                        postSnapshot.getRef().child("totalXP").setValue(String.valueOf(Integer.parseInt(user.getTotalXP())+xp));
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
