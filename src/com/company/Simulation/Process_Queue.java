package com.company.Simulation;

import com.company.EPK.EPK;

import java.util.List;

public class Process_Queue {

    private List<Simulation_Instance> instances;
    private EPK epk;


    public Process_Queue(List<Simulation_Instance> instances, EPK epk) {
        this.instances = instances;
        this.epk = epk;
    }

    public void run() {
        //TODO Threading
    }
}
