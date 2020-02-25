package com.company.Simulation.Simulation_Base.Data;

import com.company.Enums.Classification;

public class Item {

    private int i_ID;
    private String item_Name;
    private int quantity;
    private float quality;
    private Classification classification;

    public Item(int i_ID, String item_Name, int quantity, float quality, Classification classification) {
        this.i_ID = i_ID;
        this.item_Name = item_Name;
        this.quantity = quantity;
        this.quality = quality;
        this.classification = classification;
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

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public boolean item_equals_list(Item i) {
        return this.getI_ID() == i.getI_ID();
    }
}
