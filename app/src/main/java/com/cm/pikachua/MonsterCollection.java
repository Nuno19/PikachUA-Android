package com.cm.pikachua;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class MonsterCollection {

    public int monsterID;
    public int monsterImage;
    public int monsterNotImage;
    public boolean hasBennCaught;

    public MonsterCollection(int monsterID, int monsterImage, int monsterNotImage, boolean hasBennCaught) {
        this.monsterID = monsterID;
        this.monsterImage = monsterImage;
        this.monsterNotImage = monsterNotImage;
        this.hasBennCaught = hasBennCaught;
    }
}
