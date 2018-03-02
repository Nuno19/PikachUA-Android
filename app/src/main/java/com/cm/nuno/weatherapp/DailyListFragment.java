package com.cm.nuno.weatherapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyListFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "MESSAGE";

    public DailyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_daily_list, container,
                false);
        String weather = Weather.callOpenWeatherForecast();
        String[] daysLabels = Weather.parseJSON(weather);

        ArrayAdapter<String> forecastListAdapter = new ArrayAdapter<String>(
                getActivity(), // The current context (this activity)
                R.layout.list_item_meteo, // The name of the layout ID.
                R.id.textDayForecast, // The ID of the textview to populate.
                daysLabels);
// Get a reference to the ListView, and attach this adapter to it.
        final ListView listView = rootView.findViewById(R.id.dayList);
        listView.setAdapter(forecastListAdapter);

        Button but = rootView.findViewById(R.id.button2);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadAsyncTask task = new DownloadAsyncTask();
                task.execute(new String[]{"https://developer.android.com/images/fundamentals/diagram_backstack.png"});
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String text = (String) listView.getItemAtPosition(i);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getActivity(), text, duration);
                toast.show();

                sendMessage(text, i);

            }
        });
        return rootView;
    }

    public void sendMessage(String msg, int position) {


        DetailsFragment newFragment = DetailsFragment.newInstance(msg, "GoodBye");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }

    public class DownloadAsyncTask extends AsyncTask<String, ProgressBar, Bitmap> {

        @Override
        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getActivity(), "Downloading", duration);
            toast.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            // Some long-running task like downloading an image.
            try {
                URL url = new URL(strings[0]);

                InputStream stream = url.openStream();

                Bitmap d = BitmapFactory.decodeStream(stream);
                stream.close();
                return d;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(ProgressBar... values) {
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
            TextView t = getActivity().findViewById(R.id.textView2);
            t.setText(values[0].getProgress());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getActivity(), "Finished Download", duration);
            toast.show();
            ImageView v = getActivity().findViewById(R.id.imageView);
            v.setImageBitmap(result);
        }

    }
}
