package com.cm.pikachua;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Eduardo on 28/03/2018.
 */

@IgnoreExtraProperties
public class User {

    private String username;
    private String startDate;
    private String totalXP;
    private String monstersCaught;
    private String image;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String username, String startDate, String totalXP, String monstersCaught, String image) {
        this.username = username;
        this.startDate = startDate;
        this.totalXP = totalXP;
        this.monstersCaught = monstersCaught;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTotalXP() {
        return totalXP;
    }

    public String getMonstersCaught() {
        return monstersCaught;
    }

    public String getImage(){
        return image;
    }

}


