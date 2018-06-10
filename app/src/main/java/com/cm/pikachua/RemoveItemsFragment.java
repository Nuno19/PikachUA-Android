package com.cm.pikachua;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.instances.ItemInst;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RemoveItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemoveItemsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ItemInst item_inst = null;
    private DatabaseReference iFirebaseDatabase;
    private FirebaseDatabase iFirebaseInstance;
    private int totalAmount;


    public RemoveItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RemoveItemsFragment newInstance(String param1) {
        RemoveItemsFragment fragment = new RemoveItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View rootView = inflater.inflate(R.layout.fragment_remove_items, container, false);

        final DatabaseReference itemsInst = FirebaseDatabase.getInstance().getReference("items_inst");

        ValueEventListener listenerItemInst = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);
                    if(item_inst.getId().equals(mParam1)){
                        TextView t1 = rootView.findViewById(R.id.title1);
                        t1.setText(item_inst.getName());

                        TextView t2 = rootView.findViewById(R.id.title2);
                        t2.setText(getString(R.string.delete) + " " + item_inst.getAmount() + ")");

                        totalAmount = item_inst.getAmount();

                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(item_inst.getImage());
                        // Load the image using Glide

                        final ImageView imageViewAndroid = (ImageView) rootView.findViewById(R.id.image);
                        // Load the image using Glide

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                // Pass it to Picasso to download, show in ImageView and caching
                                Picasso.with(getContext()).load(uri.toString()).into(imageViewAndroid);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
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
        itemsInst.addListenerForSingleValueEvent(listenerItemInst);

        FloatingActionButton button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });

        final EditText quantity  = (EditText) rootView.findViewById(R.id.editText);

        Button button_all = rootView.findViewById(R.id.button_all);
        button_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity.setText(String.valueOf(totalAmount));
            }
        });

        Button button_confirm = rootView.findViewById(R.id.button_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.empty_field_text), Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                if (Integer.parseInt(quantity.getText().toString()) <= 0){
                    Toast.makeText(getContext(), getString(R.string.no_items), Toast.LENGTH_LONG).show();
                    return;
                }
                else if (Integer.parseInt(quantity.getText().toString()) > totalAmount){
                    Toast.makeText(getContext(), getString(R.string.too_many_items), Toast.LENGTH_LONG).show();
                    return;
                }
                else if (Integer.parseInt(quantity.getText().toString()) == 1){
                    builder1.setMessage(getString(R.string.delete_items) + " 1 " + getString(R.string.items_singular) + "?");
                }
                else {
                    builder1.setMessage(getString(R.string.delete_items) + " " + Integer.parseInt(quantity.getText().toString()) + " " + getString(R.string.items_plural) + "?");
                }

                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                deleteItems(Integer.parseInt(quantity.getText().toString()));
                            }
                        });

                builder1.setNegativeButton(
                        getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        Log.d("O", "hello");

        return rootView;
    }

    private void deleteItems(final int quantity){

        iFirebaseInstance = FirebaseDatabase.getInstance();
        iFirebaseDatabase = iFirebaseInstance.getReference("items_inst");

        final boolean[] delete = {true};

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    item_inst = postSnapshot.getValue(ItemInst.class);

                    if(item_inst.getId().equals(mParam1) && (delete[0] == true)) {
                        postSnapshot.getRef().child("amount").setValue(item_inst.getAmount()-quantity);
                        delete[0] = false;
                        hideSoftKeyboard(getActivity());
                        getActivity().onBackPressed();
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
        iFirebaseDatabase.addListenerForSingleValueEvent(postListener);
    }

    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

}