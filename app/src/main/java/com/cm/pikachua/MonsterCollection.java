package com.cm.pikachua;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class MonsterCollection {

    public int monsterID;
    public String monsterImage;
    public boolean hasBennCaught;

    public MonsterCollection(int monsterID, String monsterImage, boolean hasBennCaught) {
        this.monsterID = monsterID;
        this.monsterImage = monsterImage;
        this.hasBennCaught = hasBennCaught;
    }
}
