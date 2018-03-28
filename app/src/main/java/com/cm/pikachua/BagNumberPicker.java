package com.cm.pikachua;

import android.app.Activity;
import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class BagNumberPicker extends Activity implements NumberPicker.OnValueChangeListener
{
    private TextView tv;
    static Dialog d ;

    ArrayList<Item> arrayOfItems = new ArrayList<Item>();

    int[] listViewTotalId = {
            1,2,3,4,5,6,
            7,8,
    };

    String[] listViewStringTitle = {
            "Razz Berry", "Unkown Berry 1", "Unkown Berry 2", "Golden Razz Berry", "PokéBall", "GreatBall",
            "UltraBall", "MasterBall",

    } ;

    String[] listViewStringSubtitle = {
            "1","2","3","4","5","6",
            "7","8",

    } ;

    int[] listViewImageId = {
            R.drawable.item_0701, R.drawable.item_0702, R.drawable.item_0704, R.drawable.item_0706, R.drawable.pokeball_sprite, R.drawable.greatball_sprite,
            R.drawable.ultraball_sprite, R.drawable.masterball_sprite,
    };


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Voltar", Toast.LENGTH_LONG).show();

                onBackPressed();
            }
        });


        final ItemAdapter adapter = new ItemAdapter(this, arrayOfItems);

        Log.d("T","e");

        int number = 0;
        for (int i=0;i<listViewTotalId.length;i++){
            if (listViewTotalId[i] > 0) {
                Item x = new Item(i, listViewStringTitle[i], listViewStringSubtitle[i], listViewImageId[i], listViewTotalId[i]);
                adapter.add(x);
                number += listViewTotalId[i];
            }
        }

        int total = 200;

        TextView t = findViewById(R.id.total1);
        t.setText("Max: " + number + "/" + total);

        final ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(adapter);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                //Toast.makeText(getContext(), "ListView Item: " + adapter.getItem(i).itemID, Toast.LENGTH_LONG).show();

                show(Integer.toString(adapter.getItem(i).itemID));
            }
        });
    }
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is",""+newVal);

    }

    public void show(String i)
    {

        final Dialog d = new Dialog(this);
        d.setTitle("How many " + i + " to delete?");
        d.setContentView(R.layout.numberpicker);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(200); // max value 100
        np.setMinValue(1);   // min value 1
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();


    }
}
