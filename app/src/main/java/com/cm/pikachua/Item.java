package com.cm.pikachua;

/**
 * Created by PedroRaimundo on 2018-03-13.
 */

public class Item {

    public int itemID;
    public String itemName;
    public String itemDescription;
    public int itemImage;
    public int itemQuantity;

    public Item(int itemID, String itemName, String itemDescription, int itemImage, int itemQuantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
    }
}
