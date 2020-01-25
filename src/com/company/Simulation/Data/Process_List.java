package com.company.Simulation.Data;

import com.company.Simulation.Instance.Simulation_Instance;

import java.util.List;

public class Process_List {
    private List<Simulation_Instance> Working_List;

    public Process_List(List<Simulation_Instance> working_List) {
        Working_List = working_List;
    }

    public Process_List() {
        Working_List = null;
    }

    public synchronized List<Simulation_Instance> getWorking_List() {
        return Working_List;
    }

    public synchronized void setWorking_List(List<Simulation_Instance> working_List) {
        Working_List = working_List;
    }

    public synchronized void Schedule_Process(Simulation_Instance Process) {
        Working_List.add(Process);
    }
}
