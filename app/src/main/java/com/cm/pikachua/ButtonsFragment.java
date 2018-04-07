package com.cm.pikachua;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonsFragment extends Fragment {


    public ButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_buttons, container, false);

        ImageButton but2 = rootView.findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getActivity(), StorageActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
                MapsActivity.buttons = false;
            }
        });

        ImageButton but3 = rootView.findViewById(R.id.button3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getActivity(), BagActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
                MapsActivity.buttons = false;
            }
        });

        ImageButton but4 = rootView.findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getActivity(), CollectionActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
                MapsActivity.buttons = false;
            }
        });

        Button but5 = rootView.findViewById(R.id.button5);
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getActivity(),SettingsActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
                MapsActivity.buttons = false;
            }
        });

        return rootView;
    }
}
