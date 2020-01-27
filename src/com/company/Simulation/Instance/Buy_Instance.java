package com.company.Simulation.Instance;

import com.company.Simulation.Data.Item;

import java.util.List;

public class Buy_Instance extends Simulation_Instance {
    private List<Item> Buy_Items;
    private float price;
    private boolean fullfilled;

    public Buy_Instance(int case_ID, List<Item> buy_Items) {
        super(case_ID);
        Buy_Items = buy_Items;
        fullfilled = false;
    }

    public List<Item> getBuy_Items() {
        return Buy_Items;
    }

    public void setBuy_Items(List<Item> buy_Items) {
        Buy_Items = buy_Items;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isFullfilled() {
        return fullfilled;
    }

    public void setFullfilled(boolean fullfilled) {
        this.fullfilled = fullfilled;
    }
}
