package com.company.Simulation.Data;

import com.company.Simulation.Instance.Simulation_Instance;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class Event_List {
    private List<Simulation_Instance> transport_List;
    public java.util.concurrent.locks.Lock Lock;

    public Event_List(List<Simulation_Instance> transport_List) {
        this.transport_List = transport_List;
    }

    public Event_List() {
        this.transport_List = null;
    }

    public Lock getLock() {
        return Lock;
    }

    public synchronized List<Simulation_Instance> getTransport_List() {
        return transport_List;
    }

    public synchronized void setTransport_List(List<Simulation_Instance> transport_List) {
        this.transport_List = transport_List;
    }

    public synchronized void add_transport_Process(Simulation_Instance Process) {
        transport_List.add(Process);
    }


}
