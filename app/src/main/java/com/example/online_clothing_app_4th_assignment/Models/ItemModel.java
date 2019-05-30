package com.example.online_clothing_app_4th_assignment.Models;

public class ItemModel {
    String itemName;
    String itemPrice;
    String itemImageName;
    String itemDescription;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImageName() {
        return itemImageName;
    }

    public void setItemImageName(String itemImageName) {
        this.itemImageName = itemImageName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public ItemModel(String itemName, String itemPrice, String itemImageName, String itemDescription) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImageName = itemImageName;
        this.itemDescription = itemDescription;
    }
}
