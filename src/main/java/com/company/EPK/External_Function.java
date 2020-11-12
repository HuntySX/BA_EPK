package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

public class External_Function extends Function implements Printable_Node {

    private External_XOR_Split External_XOR;
    private Workingtime Min_External_Time;
    private Workingtime Max_External_Time;
    private Workingtime Mean_External_Time;
    private Workingtime Deviation_External_Time;
    private Workingtime Timeout_External_Time;

    public External_Function(int ID) {
        super(null, ID, null, false, null, null, 0, 0, 0);
        External_XOR = null;
        Min_External_Time = new Workingtime();
        Max_External_Time = new Workingtime();
        Mean_External_Time = new Workingtime();
        Deviation_External_Time = new Workingtime();
        Timeout_External_Time = new Workingtime();
    }

    private void calculate_External_Event() {
        
    }

    public External_XOR_Split getExternal_XOR() {
        return External_XOR;
    }

    public void setExternal_XOR(External_XOR_Split external_XOR) {
        External_XOR = external_XOR;
    }

    public Workingtime getMin_External_Time() {
        return Min_External_Time;
    }

    public void setMin_External_Time(Workingtime min_External_Time) {
        Min_External_Time = min_External_Time;
    }

    public Workingtime getMax_External_Time() {
        return Max_External_Time;
    }

    public void setMax_External_Time(Workingtime max_External_Time) {
        Max_External_Time = max_External_Time;
    }

    public Workingtime getMean_External_Time() {
        return Mean_External_Time;
    }

    public void setMean_External_Time(Workingtime mean_External_Time) {
        Mean_External_Time = mean_External_Time;
    }

    public Workingtime getDeviation_External_Time() {
        return Deviation_External_Time;
    }

    public void setDeviation_External_Time(Workingtime deviation_External_Time) {
        Deviation_External_Time = deviation_External_Time;
    }

    public Workingtime getTimeout_External_Time() {
        return Timeout_External_Time;
    }

    public void setTimeout_External_Time(Workingtime timeout_External_Time) {
        Timeout_External_Time = timeout_External_Time;
    }
}
