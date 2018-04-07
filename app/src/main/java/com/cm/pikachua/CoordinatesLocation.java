/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cm.pikachua;

/**
 *
 * @author Eduardo
 */

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;
import java.text.*;
import java.io.*;

public class CoordinatesLocation {

    public static void getCoordinates(double center_latitude, double center_longitude) {
        // TODO code application logic here

        int id = 152 + 0;
        
        double[] myCoords = {center_latitude, center_longitude};
        
        int radius = 2500;
        Coordinates[] coords = new Coordinates[200];


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("pokestops");

        Map<String, Coordinates> coord = new HashMap<>();
        for(int i=0;i<coords.length;i++){
            double[] array = getLocation(center_latitude, center_longitude, radius);
           // DecimalFormat dff=new DecimalFormat(".######");
           // DecimalFormat dff2=new DecimalFormat("#.##");

            coord.put(Integer.toString(id),new Coordinates(array[0],array[1],Integer.toString(id)));

            id++;

           // coords[i] = ;
           // System.out.println(" Coordenadas da Localização " + i + ": "  + dff.format(array[0]) + "," + dff.format(array[1]) + "Distância: " + dff2.format(diff) );
        }
        ref.setValue(coord, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("R", "Data could not be saved. " + databaseError.getMessage());
                } else {
                    Log.d("R", "Data saved successfully.");
                    // update the 'owns' list in user's data
                    //TODO: we can use this to count how many of the same books an user has
                }
            }
        });
       /*try{
        readFromFile("pokemon_stats.txt");
       }catch(Exception e){}
        */
    }
    
    
    public static double[] getLocation(double x0, double y0, int radius) {
    Random random = new Random();

    // Convert radius from meters to degrees
    double radiusInDegrees = radius / 111000f;

    double u = random.nextDouble();
    double v = random.nextDouble();
    double w = radiusInDegrees * Math.sqrt(u);
    double t = 2 * Math.PI * v;
    double x = w * Math.cos(t);
    double y = w * Math.sin(t);

    // Adjust the x-coordinate for the shrinking of the east-west distances
    double new_x = x / Math.cos(Math.toRadians(y0));

    double foundLongitude = new_x + y0;
    double foundLatitude = y + x0;
    
    double array[] = new double[2];
    array[0] = foundLatitude;
    array[1] = foundLongitude;
    
    return array;
}
    
    public static double getDistance(double[] myPosition, double[] pokemonPosition){
        int earthRadiusKm = 6371;
        
        double diff_lat = degreesToRadians(pokemonPosition[0]-myPosition[0]);
        double diff_long = degreesToRadians(pokemonPosition[1]-myPosition[1]);
        
        double l1 = myPosition[0];
        double l2 = pokemonPosition[0];
        
        double a = Math.sin(diff_lat/2) * Math.sin(diff_lat/2) + Math.sin(diff_long/2) * Math.sin(diff_long/2) * Math.cos(l1) * Math.cos(l2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return earthRadiusKm * c;
        
        
}
    
    public static double degreesToRadians(double degrees){
        return degrees * Math.PI / 180;
    }
    
    public static void readFromFile(String filename) throws Exception{
       
        File f = new File(filename);
        Scanner scf = new Scanner(f);
        int i=0, j=0;
        String array[] = new String[1000];
        String s = "";
        
        while(scf.hasNextLine()){
            if(i % 5 == 0){
                array[j] = s;
                j++;
                s = scf.nextLine();
            }
            else{
                s += scf.nextLine();
            }
            i++;
        }
        
        System.out.printf("A: %s", array[0]);
            
        
        
    }
    
    
}


