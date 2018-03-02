package com.cm.nuno.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Weather w = new Weather();
        String wString = w.callOpenWeatherForecast();
        try {
            Log.d("R", "HELLO");
            String[] entries = (String[]) w.parseJSON(wString);
            Log.d("F", entries[0]);


            ArrayAdapter<String> forecastListAdapter = new ArrayAdapter<String>(
                    this, // The current context (this activity)
                    R.layout.list_item_meteo, // The name of the layout ID.
                    R.id.textDayForecast, // The ID of the textview to populate.
                    entries);


            final ListView list = (ListView) findViewById(R.id.list_of_days);
            list.setAdapter(forecastListAdapter);


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Context context = getApplicationContext();
                    String text = (String) list.getItemAtPosition(i);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    sendMessage(text);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("E", e.getMessage());
        }
    }

    public void sendMessage(String msg) {

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, msg);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.map:
                showMap();
                return true;
            case R.id.I2:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showMap() {
        Uri location = Uri.parse("geo:0,0?q=Aveiro,+Portugal");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(mapIntent);
        }


    }
}
