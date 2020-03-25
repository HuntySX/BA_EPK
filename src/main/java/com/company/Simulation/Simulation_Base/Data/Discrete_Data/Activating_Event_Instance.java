package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.Activating_Function;

public class Activating_Event_Instance extends Event_Instance {

    private Activating_Function End_Function;
    private int for_case_ID;

    public Activating_Event_Instance(int case_ID, Activating_Function end_Function, int for_case_ID, int waiting_Ticket) {
        super(case_ID);
        End_Function = end_Function;
        this.for_case_ID = for_case_ID;
    }

    public Activating_Function getEnd_Function() {
        return End_Function;
    }

    public void setEnd_Function(Activating_Function end_Function) {
        End_Function = end_Function;
    }

    public int getFor_case_ID() {
        return for_case_ID;
    }

    public void setFor_case_ID(int for_case_ID) {
        this.for_case_ID = for_case_ID;
    }
}
