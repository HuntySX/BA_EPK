package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.External_XOR_Instance_Lock;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;

public class External_Function extends Function implements Printable_Node {

    private External_XOR_Split External_XOR;
    private Workingtime Min_External_Time;
    private Workingtime Max_External_Time;
    private Workingtime Mean_External_Time;
    private Workingtime Deviation_External_Time;

    public External_Function(int ID) {
        super(null, ID, null, false, null, null, 0, 0, 0);
        External_XOR = null;
        Min_External_Time = new Workingtime();
        Max_External_Time = new Workingtime();
        Mean_External_Time = new Workingtime();
        Deviation_External_Time = new Workingtime();
    }

    public External_Function(List<EPK_Node> next_Elem, int id, String function_tag,
                             Workingtime min_external_time, Workingtime max_external_time, Workingtime mean_external_time,
                             Workingtime deviation_external_time) {

        super(next_Elem, id, function_tag, false, null, null, 0, 0, 0);
        External_XOR = null;
        Min_External_Time = min_external_time;
        Max_External_Time = max_external_time;
        Mean_External_Time = mean_external_time;
        Deviation_External_Time = deviation_external_time;
    }

    public External_XOR_Instance_Lock calculate_External_Event(Event_Instance instance) {
        NormalDistribution Distribution = new NormalDistribution(Mean_External_Time.get_Duration_to_Seconds(), Deviation_External_Time.get_Duration_to_Seconds());
        Workingtime sample = new Workingtime((int) Distribution.sample());
        if (sample.isBefore(Min_External_Time)) {
            sample = Min_External_Time;
        }
        if (sample.isAfter_Equal(Max_External_Time)) {
            sample = Max_External_Time;
        }

        return External_XOR.newExternal_Event(instance, sample);
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
}
