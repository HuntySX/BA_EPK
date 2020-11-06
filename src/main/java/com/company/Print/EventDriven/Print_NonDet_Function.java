package com.company.Print.EventDriven;

import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

import java.util.List;

public class Print_NonDet_Function extends Print_Event_Driven_File {
    private String Tag;
    private Function_Type function_type;
    private boolean concurrently;
    private List<Connected_Resource_Print> Resources;
    private List<Connected_Workforce_Print> Workforces;
    private Workingtime Min_Workingtime;
    private Workingtime Max_Workingtime;
    private Workingtime Mean_Workingtime;
    private Workingtime Deviation_Workingtime;

    public Print_NonDet_Function(int ID, List<Connected_Elem_Print> Next_elements, String tag, Function_Type function_type,
                                 boolean concurrently,
                                 List<Connected_Resource_Print> resources, List<Connected_Workforce_Print> workforces,
                                 Workingtime Min_Workingtime, Workingtime Max_Workingtime,
                                 Workingtime Mean_Workingtime, Workingtime Deviation_Workingtime) {
        super(Node_Type.Function, ID, Next_elements);
        Tag = tag;
        this.function_type = function_type;
        this.concurrently = concurrently;
        Resources = resources;
        Workforces = workforces;
        this.Min_Workingtime = Min_Workingtime;
        this.Max_Workingtime = Max_Workingtime;
        this.Mean_Workingtime = Mean_Workingtime;
        this.Deviation_Workingtime = Deviation_Workingtime;
    }


    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public Function_Type getFunction_type() {
        return function_type;
    }

    public void setFunction_type(Function_Type function_type) {
        this.function_type = function_type;
    }

    public boolean isConcurrently() {
        return concurrently;
    }

    public void setConcurrently(boolean concurrently) {
        this.concurrently = concurrently;
    }

    public List<Connected_Resource_Print> getResources() {
        return Resources;
    }

    public void setResources(List<Connected_Resource_Print> resources) {
        Resources = resources;
    }

    public List<Connected_Workforce_Print> getWorkforces() {
        return Workforces;
    }

    public void setWorkforces(List<Connected_Workforce_Print> workforces) {
        Workforces = workforces;
    }

    public Workingtime getMin_Workingtime() {
        return Min_Workingtime;
    }

    public void setMin_Workingtime(Workingtime min_Workingtime) {
        Min_Workingtime = min_Workingtime;
    }

    public Workingtime getMax_Workingtime() {
        return Max_Workingtime;
    }

    public void setMax_Workingtime(Workingtime max_Workingtime) {
        Max_Workingtime = max_Workingtime;
    }

    public Workingtime getMean_Workingtime() {
        return Mean_Workingtime;
    }

    public void setMean_Workingtime(Workingtime mean_Workingtime) {
        Mean_Workingtime = mean_Workingtime;
    }

    public Workingtime getDeviation_Workingtime() {
        return Deviation_Workingtime;
    }

    public void setDeviation_Workingtime(Workingtime deviation_Workingtime) {
        Deviation_Workingtime = deviation_Workingtime;
    }
}
