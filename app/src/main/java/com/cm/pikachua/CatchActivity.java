package com.cm.pikachua;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CatchActivity extends AppCompatActivity {

    AlertDialog alertDialog1, alertDialog2;
    CharSequence[] values_ball = {" Normal Ball (5) "," Great Ball (6) "," Ultra Ball (7) "," Master Ball (8) "};
    CharSequence[] values_berry = {" None "," Razz Berry (1) "," Unkown Berry 1 (2) "," Unkown Berry 2 (3) "," Golden Razz Berry (4) "};
    int choice_ball = 0;
    int choice_berry = 0;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        String s = getIntent().getStringExtra("ID");

        Toast.makeText(CatchActivity.this, "Pokémon: " + s, Toast.LENGTH_LONG).show();

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Voltar", Toast.LENGTH_LONG).show();

                onBackPressed();

            }
        });

        Button button_balls = findViewById(R.id.button_balls);
        button_balls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Ball", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(CatchActivity.this);
                builder1.setTitle("Select Your Ball");
                builder1.setSingleChoiceItems(values_ball, choice_ball, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "Normal Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 0;
                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Great Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 1;
                                break;
                            case 2:
                                //Toast.makeText(CatchActivity.this, "Ultra Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 2;
                                break;
                            case 3:
                                //Toast.makeText(CatchActivity.this, "Master Ball", Toast.LENGTH_LONG).show();
                                choice_ball = 3;
                                break;
                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder1.create();
                alertDialog1.show();
            }
        });

        Button button_berries = findViewById(R.id.button_berries);
        button_berries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CatchActivity.this, "Berries", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder2 = new AlertDialog.Builder(CatchActivity.this);
                builder2.setTitle("Select Your Berry");
                builder2.setSingleChoiceItems(values_berry, choice_berry, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                //Toast.makeText(CatchActivity.this, "None", Toast.LENGTH_LONG).show();
                                choice_berry = 0;
                                break;
                            case 1:
                                //Toast.makeText(CatchActivity.this, "Razz Berry", Toast.LENGTH_LONG).show();
                                choice_berry = 1;
                                break;
                            case 2:
                                //Toast.makeText(CatchActivity.this, "Unkown Berry 1", Toast.LENGTH_LONG).show();
                                choice_berry = 2;
                                break;
                            case 3:
                                //Toast.makeText(CatchActivity.this, "Unkown Berry 2", Toast.LENGTH_LONG).show();
                                choice_berry = 3;
                                break;
                            case 4:
                                //Toast.makeText(CatchActivity.this, "Golden Razz Berry", Toast.LENGTH_LONG).show();
                                choice_berry = 4;
                                break;
                        }
                        alertDialog2.dismiss();
                    }
                });
                alertDialog2 = builder2.create();
                alertDialog2.show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Math.random() > Math.random()){
                    Toast.makeText(CatchActivity.this,
                            "Got it!", Toast.LENGTH_LONG)
                            .show();
                    onBackPressed();
                }
                else if (j < 1){
                    j++;
                    Toast.makeText(CatchActivity.this,
                            "Oh No! It escaped!", Toast.LENGTH_LONG)
                            .show();
                }
                else {
                    Toast.makeText(CatchActivity.this,
                            "Oh No! It ran away!", Toast.LENGTH_LONG)
                            .show();
                    onBackPressed();
                }
            }
        });
    }
}
