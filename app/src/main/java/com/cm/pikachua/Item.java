package com.cm.pikachua;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class Item {

    public int itemID;
    public String itemName;
    public String itemDescription;
    public String itemImage;
    public int itemQuantity;

    public Item(int itemID, String itemName, String itemDescription, String itemImage, int itemQuantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
    }
}
