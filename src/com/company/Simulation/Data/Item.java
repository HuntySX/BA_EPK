package com.company.Simulation.Data;

import com.company.Enums.Quality;

public class Item {

    private int i_ID;
    private String item_Name;
    private int quantity;
    private Quality quality;

    public Item(int i_ID, String item_Name, int quantity, Quality quality) {
        this.i_ID = i_ID;
        this.item_Name = item_Name;
        this.quantity = quantity;
        this.quality = quality;
    }

    public int getI_ID() {
        return i_ID;
    }

    public void setI_ID(int i_ID) {
        this.i_ID = i_ID;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }
}
