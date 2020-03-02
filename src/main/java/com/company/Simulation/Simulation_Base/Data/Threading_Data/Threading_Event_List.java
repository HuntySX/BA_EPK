package com.company.Simulation.Simulation_Base.Data.Threading_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Threading_Event_List {
    private List<Simulation_Instance> transport_List;
    public java.util.concurrent.locks.Lock Lock;

    public Threading_Event_List(List<Simulation_Instance> transport_List) {
        this.transport_List = transport_List;
    }

    public Threading_Event_List() {
        this.transport_List = new ArrayList<>();
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
