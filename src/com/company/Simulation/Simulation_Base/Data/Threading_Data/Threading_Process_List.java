package com.company.Simulation.Simulation_Base.Data.Threading_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public class Threading_Process_List {
    private List<Simulation_Instance> Working_List;

    public Threading_Process_List(List<Simulation_Instance> working_List) {
        Working_List = working_List;
    }

    public Threading_Process_List() {
        Working_List = new ArrayList<>();
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
