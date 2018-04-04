package com.cm.pikachua;

/**
 * Created by Eduardo on 03/04/2018.
 */

public class PokemonInst {

    private String id, pokemon_id, user_id, value, nickname;
    private String defaultNickname;
    private String image;

    public PokemonInst(){

    }

    public  PokemonInst(String id, String pokemon_id, String user_id,String nickname, String value, String image){
        this.id = id;
        this.pokemon_id = pokemon_id;
        this.user_id = user_id;
        this.nickname = nickname;
        this.value = value;
        this.image = image;
        defaultNickname = nickname;

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

    public String getNickname() {
        return nickname;
    }

    public String getValue() {
        return value;
    }

    public String getImage() {
        return image;
    }
}
