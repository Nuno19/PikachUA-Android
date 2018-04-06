package com.cm.pikachua;

/**
 * Created by Eduardo on 03/04/2018.
 */

public class ItemInst {

    private String id, item_id, user_id, name, description, amount;
    private String image;

    public ItemInst(){

    }

    public  ItemInst(String id, String item_id, String user_id, String name, String description, String amount, String image){
        this.id = id;
        this.item_id = item_id;
        this.user_id = user_id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.image = image;

    }

    public String getId() {
        return id;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() { return amount; }

    public String getImage() {
        return image;
    }

    public void setAmount(int num){
        amount = String.valueOf(num);
    }
}
