package com.cm.pikachua;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;


    public CatchFragment() {
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
    public static CatchFragment newInstance(String param1) {
        CatchFragment fragment = new CatchFragment();
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

    AlertDialog alertDialog1, alertDialog2;
    CharSequence[] values_ball = {" Normal Ball "," Great Ball "," Ultra Ball "," Master Ball "};
    CharSequence[] values_berry = {" Razz Berry "," Nanab Berry (lol) "," Pinap Berry "," Golden Razz Berry "};
    int choice_ball = 0;
    int choice_berry = 0;
    int j = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_catch, container, false);

        Toast.makeText(getContext(), "Pokémon: " + mParam1, Toast.LENGTH_LONG).show();

        Button button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Voltar", Toast.LENGTH_LONG)
                        .show();

                getActivity().onBackPressed();

            }
        });

        Button button_balls = rootView.findViewById(R.id.button_balls);
        button_balls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Ball", Toast.LENGTH_LONG)
                        .show();

                CreateAlertDialogWithRadioButtonGroupBalls();
            }
        });

        Button button_berries = rootView.findViewById(R.id.button_berries);
        button_berries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Berries", Toast.LENGTH_LONG)
                        .show();

                CreateAlertDialogWithRadioButtonGroupBerries();
            }
        });

        FloatingActionButton fab = rootView.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Math.random() > Math.random()){
                    Toast.makeText(getContext(),
                            "Got it!", Toast.LENGTH_LONG)
                            .show();
                    getActivity().onBackPressed();
                }
                else if (j < 1){
                    j++;
                    Toast.makeText(getContext(),
                            "Oh No! It escaped!", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    Toast.makeText(getContext(),
                            "Oh No! It ran away!", Toast.LENGTH_LONG)
                            .show();
                    getActivity().onBackPressed();
                }
            }
        });

        return rootView;
    }

    public void CreateAlertDialogWithRadioButtonGroupBalls(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Your Ball");
        builder.setSingleChoiceItems(values_ball, choice_ball, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        Toast.makeText(getContext(), "Normal Ball", Toast.LENGTH_LONG).show();
                        choice_ball = 0;
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Great Ball", Toast.LENGTH_LONG).show();
                        choice_ball = 1;
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Ultra Ball", Toast.LENGTH_LONG).show();
                        choice_ball = 2;
                        break;
                    case 3:
                        Toast.makeText(getContext(), "Master Ball", Toast.LENGTH_LONG).show();
                        choice_ball = 3;
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    public void CreateAlertDialogWithRadioButtonGroupBerries(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Your Berry");
        builder.setSingleChoiceItems(values_berry, choice_berry, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        Toast.makeText(getContext(), "Razz Berry", Toast.LENGTH_LONG).show();
                        choice_berry = 0;
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Nanab Berry (lol)", Toast.LENGTH_LONG).show();
                        choice_berry = 1;
                        break;
                    case 2:
                        Toast.makeText(getContext(), "Pinap Berry", Toast.LENGTH_LONG).show();
                        choice_berry = 2;
                        break;
                    case 3:
                        Toast.makeText(getContext(), "Golden Razz Berry", Toast.LENGTH_LONG).show();
                        choice_berry = 3;
                        break;
                }
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = builder.create();
        alertDialog2.show();
    }
}
