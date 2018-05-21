package com.cm.entities;

import com.google.firebase.database.IgnoreExtraProperties;


/**
 * Created by Eduardo on 30/03/2018.
 */

@IgnoreExtraProperties
public class Pokemon {

    private String id;
    private String name, weight, height,nickname,pokedex;
    private String spawnRate, catchRate, fleeRate;
    String image;

    public Pokemon(){

    }

    public Pokemon(String id, String name, String weight, String height, String nickname, String pokedex, String spawnRate, String catchRate, String fleeRate, String image){
        //this.id = Integer.parseInt(id);
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.nickname = nickname;
        this.pokedex = pokedex;
        //this.spawnRate = Double.parseDouble(spawnRate);
        this.spawnRate = spawnRate;
        this.catchRate = catchRate;
        this.fleeRate = fleeRate;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPokedex() {
        return pokedex;
    }

    public String getSpawnRate() {
        return spawnRate;
    }

    public String getCatchRate() {
        return catchRate;
    }

    public String getFleeRate() {
        return fleeRate;
    }

    public String getImage() {
        return image;
    }
}
