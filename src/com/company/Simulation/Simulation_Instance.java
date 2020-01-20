package com.company.Simulation;

import com.company.EPK.Function;

import java.util.List;

import static com.company.Simulation.Status.Starting;

public abstract class Simulation_Instance {

    private Status act_Status;
    private List<Function> scheduled_Processes;
    private int case_ID;

    public Simulation_Instance(int case_ID) {
        this.act_Status = Starting;
        scheduled_Processes = null;
        this.case_ID = case_ID;
    }

    public Status getAct_Status() {
        return act_Status;
    }

    public void setAct_Status(Status act_Status) {
        this.act_Status = act_Status;
    }

    public List<Function> getScheduled_Processes() {
        return scheduled_Processes;
    }

    public void setScheduled_Processes(List<Function> scheduled_Processes) {
        this.scheduled_Processes = scheduled_Processes;
    }

    public int getCase_ID() {
        return case_ID;
    }

    public void setCase_ID(int case_ID) {
        this.case_ID = case_ID;
    }
}
