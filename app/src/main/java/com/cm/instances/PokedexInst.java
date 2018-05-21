package com.cm.instances;

/**
 * Created by Eduardo on 03/04/2018.
 */

public class PokedexInst {

    private String id, pokemon_id, user_id, name;
    private String image;

    public PokedexInst(){

    }

    public  PokedexInst(String id, String pokemon_id, String user_id, String name, String image){
        this.id = id;
        this.pokemon_id = pokemon_id;
        this.user_id = user_id;
        this.name = name;
        this.image = image;

    }

    public String getId() {
        return id;
    }

    public String getPokemon_id() {
        return pokemon_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() { return name; }

    public String getImage() {
        return image;
    }
}
