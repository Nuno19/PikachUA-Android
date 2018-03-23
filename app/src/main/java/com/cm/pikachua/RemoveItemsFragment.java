package com.cm.pikachua;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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


        View rootView = inflater.inflate(R.layout.fragment_remove_items, container, false);

        TextView t1 = rootView.findViewById(R.id.title1);
        t1.setText(mParam1);

        TextView t2 = rootView.findViewById(R.id.title2);
        t2.setText("How many items to delete?");

        FloatingActionButton button_back = rootView.findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),
                        "Voltar", Toast.LENGTH_LONG)
                        .show();

                getActivity().onBackPressed();

            }
        });

        final EditText quantity  = (EditText) rootView.findViewById(R.id.editText);

        Button button_confirm = rootView.findViewById(R.id.button_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Empty field text", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), mParam1 + ": " + Integer.parseInt(quantity.getText().toString())
                        , Toast.LENGTH_LONG)
                        .show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                if (Integer.parseInt(quantity.getText().toString()) <= 0){
                    Toast.makeText(getContext(),
                            "No items to remove!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                else if (Integer.parseInt(quantity.getText().toString()) == 1){
                    builder1.setMessage("Do you want to remove 1 item?");
                }
                else {
                    builder1.setMessage("Do you want to remove " + Integer.parseInt(quantity.getText().toString()) + " items?");
                }

                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                getActivity().onBackPressed();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
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

}
