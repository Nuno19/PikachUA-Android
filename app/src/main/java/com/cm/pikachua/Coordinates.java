package com.cm.pikachua;

public class Coordinates{
    private double latitude;
    private double longitude;
    private String id;
    public  Coordinates(){}
    public Coordinates(double latitude, double longitude, String id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

}
