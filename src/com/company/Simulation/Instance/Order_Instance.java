package com.company.Simulation.Instance;

import com.company.Simulation.Data.Item;

import java.time.LocalTime;
import java.util.List;

public class Order_Instance extends Simulation_Instance {

    private List<Item> ordered_Items;
    private int Order_ID;
    private boolean started;
    private boolean general_Stock;
    private LocalTime time;

    public Order_Instance(int case_ID, List<Item> ordered_Items, LocalTime time, boolean general_Stock) {
        super(case_ID);
        this.ordered_Items = ordered_Items;
        this.started = false;
        this.time = time;
        this.general_Stock = general_Stock;
    }

    public List<Item> get_Ordered_Items() {
        return ordered_Items;
    }

    public LocalTime getTime() {
        return time;
    }

    public boolean isStarted() {
        return started;
    }

    public List<Item> getOrdered_Items() {
        return ordered_Items;
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public boolean isGeneral_Stock() {
        return general_Stock;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
