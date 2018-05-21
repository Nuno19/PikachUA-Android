package com.cm.entities;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Eduardo on 03/04/2018.
 */

@IgnoreExtraProperties
public class ItemFirebase {

    private String id, description, drop_rate, catch_bonus, name;
    private String image;

    public ItemFirebase(){

    }

    public  ItemFirebase(String id, String name, String description,String drop_rate, String catch_bonus, String image){
        this.id = id;
        this.name = name;
        this.description = description;
        this.drop_rate = drop_rate;
        this.catch_bonus = catch_bonus;
        this.image = image;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCatch_bonus() { return catch_bonus; }

    public String getDrop_rate() { return drop_rate; }

    public String getImage() {
        return image;
    }
}
