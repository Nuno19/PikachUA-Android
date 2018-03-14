package com.cm.pikachua;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cm.nuno.weatherapp.R;


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

        ImageButton but1 = rootView.findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Menos", Toast.LENGTH_LONG)
                        .show();

                getFragmentManager().popBackStack();
            }
        });

        ImageButton but2 = rootView.findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Storage", Toast.LENGTH_LONG)
                        .show();

                Intent myIntent = new Intent(getActivity(), StorageActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
                //((StorageActivity) getActivity() ).overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
        });

        ImageButton but3 = rootView.findViewById(R.id.button3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Bag", Toast.LENGTH_LONG)
                        .show();

                Intent myIntent = new Intent(getActivity(), BagActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
                //((BagActivity) getActivity() ).overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
        });

        ImageButton but4 = rootView.findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Collection", Toast.LENGTH_LONG)
                        .show();

                Intent myIntent = new Intent(getActivity(), CollectionActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
            }
        });

        Button but5 = rootView.findViewById(R.id.button5);
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Settings", Toast.LENGTH_LONG)
                        .show();

                Intent myIntent = new Intent(getActivity(),SettingsActivity.class);
                getActivity().startActivity(myIntent);

                getFragmentManager().popBackStack();
            }
        });

        return rootView;
    }

}
