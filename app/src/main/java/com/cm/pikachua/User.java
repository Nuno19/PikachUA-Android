package com.cm.pikachua;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Eduardo on 28/03/2018.
 */

@IgnoreExtraProperties
public class User {

    private String id;
    private String startDate;
    private String totalXP;
    private String monstersCaught;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String id, String startDate, String totalXP, String monstersCaught) {
        this.id = id;
        this.startDate = startDate;
        this.totalXP = totalXP;
        this.monstersCaught = monstersCaught;
    }

    public String getId() { return id; }

    public String getStartDate() {
        return startDate;
    }

    public String getTotalXP() {
        return totalXP;
    }

    public String getMonstersCaught() {
        return monstersCaught;
    }
}



