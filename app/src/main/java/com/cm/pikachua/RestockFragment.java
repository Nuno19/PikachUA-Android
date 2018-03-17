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
 */
public class RestockFragment extends Fragment {

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;

    public RestockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_restock, container, false);

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
