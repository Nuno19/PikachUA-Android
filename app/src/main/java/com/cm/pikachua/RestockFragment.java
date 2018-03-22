package com.cm.pikachua;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestockFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;


    public RestockFragment() {
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
    public static RestockFragment newInstance(String param1) {
        RestockFragment fragment = new RestockFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_restock, container, false);

        Toast.makeText(getContext(), "PokéStop: " + mParam1, Toast.LENGTH_LONG).show();

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

        ImageView imageView = rootView.findViewById(R.id.image);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
                Toast.makeText(getContext(), "Top: Got " + (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getContext(), "Right: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getContext(), "Left: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(getContext(), "Bottom: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }

        });

        return rootView;
    }

}
