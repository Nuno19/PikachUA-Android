package com.cm.pikachua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cm.pikachua.AR.UnityPlayerActivity;

public class LaunchUnity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle extras =  i.getExtras();
        int id = extras.getInt("ID");
        int markerID = extras.getInt("markerID");
        Intent intent = new Intent(getBaseContext(), UnityPlayerActivity.class);

        intent.putExtra("ID", id);
        
        intent.putExtra("markerID",markerID);
        startActivity(intent, extras);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
