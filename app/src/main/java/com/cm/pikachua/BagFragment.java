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
import android.widget.ListView;
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
public class BagFragment extends Fragment {

    ArrayList<Item> arrayOfItems = new ArrayList<Item>();
    int number = 0;
    private String personID;

    public BagFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bag, container, false);

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


        final ItemAdapter adapter = new ItemAdapter(getContext(), arrayOfItems);

        Log.d("T","e");

        loadItems(rootView, adapter);

        final ListView androidListView = (ListView) rootView.findViewById(R.id.list_view);
        androidListView.setAdapter(adapter);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                RemoveItemsFragment newFragment = RemoveItemsFragment.newInstance(adapter.getItem(i).itemID);

                FragmentTransaction transaction =  getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                arrayOfItems.clear();
                number=0;

                // Commit the transaction
                transaction.commit();
            }
        });

        return rootView;
    }

    public void loadItems(final View rootView, final ItemAdapter adapter){

        DatabaseReference itemsInst = FirebaseDatabase.getInstance().getReference("items_inst");

        ValueEventListener listenerItemInst = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ItemInst item_inst = null;
                adapter.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);

                    if(item_inst.getUser_id().equals(personID)){
                        if (item_inst.getAmount() > 0){
                            Item x = new Item(item_inst.getId(), item_inst.getName(), item_inst.getDescription(), item_inst.getImage(), item_inst.getAmount());
                            adapter.add(x);
                        }
                        number+=item_inst.getAmount();
                    }
                }
                int total = 200;

                TextView t = rootView.findViewById(R.id.total1);
                t.setText("Max: " + number + "/" + total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        itemsInst.addValueEventListener(listenerItemInst);
    }
}