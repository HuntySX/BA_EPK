package com.company.Simulation;

import com.company.EPK.EPK;

import java.util.List;

public class Warehouse_Queue {

    private Warehouse warehouse;
    private List<Simulation_Instance> instances;


    public Warehouse_Queue(List<Simulation_Instance> instances, Warehouse warehouse) {
        this.instances = instances;
        this.warehouse = warehouse;
    }

    public void run() {
        //TODO Threading
    }

}
