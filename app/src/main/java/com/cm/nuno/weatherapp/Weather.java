package com.cm.nuno.weatherapp;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nuno on 16-02-2018.
 */

public class Weather {

    public static String callOpenWeatherForecast() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BufferedReader reader = null;
        InputStream urlConnection = null;

        String forecastJsonStr = null;

        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?id=2742611&appid=57aab80deb9a31ce802745a24b5361a2&mode=json&units=metric&cnt=7");

            InputStream s = url.openStream();
            Log.d("R", s.toString());

            InputStream inputStream = new BufferedInputStream(s);

            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                forecastJsonStr = null;
            }

            forecastJsonStr = buffer.toString();

        } catch (Exception e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } finally {
            if (urlConnection != null) {

            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return forecastJsonStr;
    }

    public static String[] parseJSON(String json) {
        List<String> w = new ArrayList<>();

        try {
            JSONObject jObject = new JSONObject(json);
            JSONArray jArray = jObject.getJSONArray("list");
            for (int i = 0; i < 7; i++) {
                Log.d("F", "msg");
                String time = jArray.getJSONObject(i).getString("dt_txt");
                String temp = jArray.getJSONObject(i).getJSONObject("main").getString("temp");
                w.add("Time:" + time + " | Temp:" + temp);
            }


        } catch (Exception e) {
            Log.d("E", e.getMessage());
        }
        Log.d("D", w.toString());
        String[] s = new String[w.size()];
        int i = 0;
        for (String a : w) {
            s[i++] = a;
        }
        return s;
    }

}
