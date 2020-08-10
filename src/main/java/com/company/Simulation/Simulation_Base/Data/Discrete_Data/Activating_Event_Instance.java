package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.Activating_Function;

public class Activating_Event_Instance extends Event_Instance {

    private Activating_Function End_Function;
    private Instance_Workflow for_Workflow;

    public Activating_Event_Instance(int case_ID, Activating_Function end_Function, Instance_Workflow for_Workflow) {
        super(case_ID);
        End_Function = end_Function;
        this.for_Workflow = for_Workflow;
    }

    public Activating_Function getEnd_Function() {
        return End_Function;
    }

    public void setEnd_Function(Activating_Function end_Function) {
        End_Function = end_Function;
    }

    public Instance_Workflow getFor_case_ID() {
        return for_Workflow;
    }

    public void setFor_case_ID(Instance_Workflow for_Workflow) {
        this.for_Workflow = for_Workflow;
    }
}
