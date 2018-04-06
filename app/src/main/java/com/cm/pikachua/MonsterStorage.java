package com.cm.pikachua;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class MonsterStorage {

    public int monsterId;
    public String monsterName;
    public String monsterImage;
    public int stat;

    public MonsterStorage(int monsterId, String monsterName, String monsterImage, int stat) {
        this.monsterId = monsterId;
        this.monsterName = monsterName;
        this.monsterImage = monsterImage;
        this.stat = stat;
    }
}
