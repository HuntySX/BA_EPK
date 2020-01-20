package com.company.Simulation;

import java.util.List;
import java.util.Timer;

public class Order_Instance extends Simulation_Instance {

    private List<Item> ordered_Items;
    private Timer

    public Order_Instance(int case_ID, List<Item> ordered_Items) {
        super(case_ID);
        this.ordered_Items = ordered_Items;
    }

    public List<Item> get_Ordered_Items() {
        return ordered_Items;
    }

    public void set_Ordered_Items(List<Item> Items) {
        this.ordered_Items = Items;
    }

}
