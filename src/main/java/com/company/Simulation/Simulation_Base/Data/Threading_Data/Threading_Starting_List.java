package com.company.Simulation.Simulation_Base.Data.Threading_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public class Threading_Starting_List {
    private List<Simulation_Instance> Starting_Instances;

    public Threading_Starting_List(List<Simulation_Instance> starting_Instances) {
        Starting_Instances = starting_Instances;
    }

    public Threading_Starting_List() {
        Starting_Instances = new ArrayList<>();
    }

    public synchronized List<Simulation_Instance> getStarting_Instances() {
        return Starting_Instances;
    }

    public synchronized void setStarting_Instances(List<Simulation_Instance> starting_Instances) {
        Starting_Instances = starting_Instances;
    }

    public synchronized void add_Starting_Instance(Simulation_Instance starting_Instance) {
        Starting_Instances.add(starting_Instance);
    }
}
