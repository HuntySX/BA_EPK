package com.company.Simulation;

import java.util.List;

public class Stock_Instance extends Simulation_Instance {

    private List<Item> stock_Items;
    private int stock_ID;

    public Stock_Instance(int case_ID, List<Item> stock_Items, int stock_ID) {
        super(case_ID);
        this.stock_Items = stock_Items;
        this.stock_ID = stock_ID;
    }

    public List<Item> getStock_Items() {
        return stock_Items;
    }

    public void setStock_Items(List<Item> stock_Items) {
        this.stock_Items = stock_Items;
    }

    public int getStock_ID() {
        return stock_ID;
    }

    public void setStock_ID(int stock_ID) {
        this.stock_ID = stock_ID;
    }
}
