package com.cm.pikachua;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class RestockActivity extends AppCompatActivity {

    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        String s = getIntent().getStringExtra("ID");

        Toast.makeText(getApplicationContext(), "PokéStop: " + s, Toast.LENGTH_LONG).show();

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Voltar", Toast.LENGTH_LONG)
                        .show();

                onBackPressed();

            }
        });

        ImageView imageView = findViewById(R.id.image);
        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), "Top: Got " + (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "Right: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "Left: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), "Bottom: Got " +  (int) (Math.ceil(Math.random()*4)) + " items", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void determinePaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        // If there is a second pane for details
        if (fragmentItemDetail != null) {
            isTwoPane = true;
        }
    }
}

