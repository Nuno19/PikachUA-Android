package com.cm.pikachua;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cm.entities.Pokemon;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonsterBioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterBioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    ArrayList<Pokemon> list_pokemons;


    public MonsterBioFragment() {
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
    public static MonsterBioFragment newInstance(String param1) {
        MonsterBioFragment fragment = new MonsterBioFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_monster_bio, container, false);

        FloatingActionButton button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Voltar", Toast.LENGTH_LONG).show();

                getActivity().onBackPressed();
            }
        });

        Query reference = FirebaseDatabase.getInstance().getReference("pokemons").orderByChild("id").startAt(mParam1).endAt(mParam1 + "\uf8ff");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                list_pokemons = new ArrayList<Pokemon>();
                Pokemon mon = null;
                int i=0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    mon = postSnapshot.getValue(Pokemon.class);
                    TextView t1 = rootView.findViewById(R.id.name);
                    t1.setText(mon.getName());

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(mon.getImage());
                    ImageView imageViewAndroid = (ImageView) rootView.findViewById(R.id.image);
                    // Load the image using Glide

                    Glide.with(getContext()).using(new FirebaseImageLoader()).load(storageReference).into(imageViewAndroid);

                    TextView t2 = rootView.findViewById(R.id.weight);
                    t2.setText(getString(R.string.weight) + " " + mon.getWeight());

                    TextView t3 = rootView.findViewById(R.id.height);
                    t3.setText(getString(R.string.height) + " " + mon.getHeight());

                    TextView t4 = rootView.findViewById(R.id.pokedex);
                    t4.setText(mon.getPokedex());

                    TextView t5 = rootView.findViewById(R.id.nickname);
                    t5.setText(mon.getNickname());
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

        return rootView;
    }

}
