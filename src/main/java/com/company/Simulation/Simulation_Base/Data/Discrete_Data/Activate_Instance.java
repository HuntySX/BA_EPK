package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

public class Activate_Instance extends Simulation_Instance {

    private int for_case;

    public Activate_Instance(int case_ID, int for_case) {
        super(case_ID);
        this.for_case = for_case;
    }

    public int getFor_case() {
        return for_case;
    }
}
