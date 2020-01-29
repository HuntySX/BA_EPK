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
    private int for_caseID;

    public Order_Instance(int case_ID, List<Item> ordered_Items, LocalTime time, boolean general_Stock, int for_caseID) {
        super(case_ID);
        this.ordered_Items = ordered_Items;
        this.started = false;
        this.time = time;
        this.general_Stock = general_Stock;
        this.for_caseID = for_caseID;
    }

    public Order_Instance(int unique_caseID, List<Item> orders, LocalTime time, boolean b) {
        super(unique_caseID);
        this.for_caseID = 0;
        this.started = false;
        this.ordered_Items = orders;
        this.general_Stock = b;
        this.time = time;

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

    public int getFor_caseID() {
        return for_caseID;
    }

    public void setFor_caseID(int for_caseID) {
        this.for_caseID = for_caseID;
    }
}
