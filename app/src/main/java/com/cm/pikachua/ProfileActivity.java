package com.cm.pikachua;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageViewAndroid;
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        determinePaneLayout();

        FloatingActionButton button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Voltar", Toast.LENGTH_LONG).show();

                onBackPressed();

            }
        });

        String[] user = new String[] {"Raimas96", "2017-03-22", getDecimal(34378989), getDecimal(2100000000)};
        int userInt = R.drawable.jv_7adtj;

        TextView textViewAndroid1 = (TextView) findViewById(R.id.username);
        textViewAndroid1.setText("Username: " + user[0]);

        TextView textViewAndroid2 = (TextView) findViewById(R.id.startDate);
        textViewAndroid2.setText("Member since: " + user[1]);

        TextView textViewAndroid3 = (TextView) findViewById(R.id.totalMonstersCaught);
        textViewAndroid3.setText("Total monsters caught: " + user[2]);

        TextView textViewAndroid4 = (TextView) findViewById(R.id.totalEXP);
        textViewAndroid4.setText("Total EXP earned: " + user[3]);

        imageViewAndroid = (ImageView) findViewById(R.id.image);
        imageViewAndroid.setImageResource(userInt);

        imageViewAndroid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "És feio, esperavas o quê?", Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton button_change = findViewById(R.id.button_change);
        button_change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        FloatingActionButton button_camera = findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

    public String getDecimal (int number) {
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbol = format.getDecimalFormatSymbols();
        symbol.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(symbol);

        return format.format(number);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imageViewAndroid.setImageURI(imageUri);
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            imageViewAndroid.setImageBitmap(photo);
        }
    }
}
