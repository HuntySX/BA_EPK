package com.company.Simulation.Data;

import com.company.Simulation.Instance.Simulation_Instance;

import java.util.List;

public class Starting_List {
    private List<Simulation_Instance> Starting_Instances;

    public Starting_List(List<Simulation_Instance> starting_Instances) {
        Starting_Instances = starting_Instances;
    }

    public Starting_List() {
        Starting_Instances = null;
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
