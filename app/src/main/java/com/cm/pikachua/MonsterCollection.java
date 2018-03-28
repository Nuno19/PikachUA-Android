package com.cm.pikachua;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class MonsterCollection {

    public int monsterID;
    public String monsterName;
    public int monsterImage;
    public int monsterNotImage;
    public boolean hasBennCaught;
    public String monsterText;

    public MonsterCollection(int monsterID, String monsterName, int monsterImage, int monsterNotImage, boolean hasBennCaught, String monsterText) {
        this.monsterID = monsterID;
        this.monsterName = monsterName;
        this.monsterImage = monsterImage;
        this.monsterNotImage = monsterNotImage;
        this.hasBennCaught = hasBennCaught;
        this.monsterText = monsterText;
    }
}
