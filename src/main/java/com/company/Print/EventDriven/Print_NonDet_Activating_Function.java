package com.company.Print.EventDriven;

import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

import java.util.List;

public class Print_NonDet_Activating_Function extends Print_NonDet_Function {

    private Connected_Elem_Print Activating_Start_Event;
    private Decide_Activation_Type Decisiontype;
    private Workingtime Min_Instantiate_time;
    private Workingtime Max_Instantiate_time;
    private Workingtime Mean_Instantiate_time;
    private Workingtime Deviation_Instantiate_time;

    public Print_NonDet_Activating_Function(int ID, List<Connected_Elem_Print> Next_elements, String tag,
                                            Function_Type function_type, boolean concurrently,
                                            List<Connected_Resource_Print> resources, List<Connected_Workforce_Print> workforces,
                                            Workingtime Min_Workingtime,
                                            Workingtime Max_Workingtime, Workingtime Mean_Workingtime, Workingtime Deviation_Workingtime,
                                            Workingtime Min_Instantiate_time, Workingtime Max_Instantiate_time,
                                            Workingtime Mean_Instantiate_time, Workingtime Deviation_Instantiate_time,
                                            Connected_Elem_Print activating_Start_Event, Decide_Activation_Type decisiontype) {
        super(ID, Next_elements, tag, function_type, concurrently, resources, workforces, Min_Workingtime, Max_Workingtime, Mean_Workingtime, Deviation_Workingtime);
        super.setNode_Type(Node_Type.Activating_Function);
        Activating_Start_Event = activating_Start_Event;
        this.Min_Instantiate_time = Min_Instantiate_time;
        this.Max_Instantiate_time = Max_Instantiate_time;
        this.Mean_Instantiate_time = Mean_Instantiate_time;
        this.Deviation_Instantiate_time = Deviation_Instantiate_time;
        Decisiontype = decisiontype;
    }

    public Connected_Elem_Print getActivating_Start_Event() {
        return Activating_Start_Event;
    }

    public void setActivating_Start_Event(Connected_Elem_Print activating_Start_Event) {
        Activating_Start_Event = activating_Start_Event;
    }

    public Workingtime getMin_Instantiate_time() {
        return Min_Instantiate_time;
    }

    public void setMin_Instantiate_time(Workingtime min_Instantiate_time) {
        Min_Instantiate_time = min_Instantiate_time;
    }

    public Workingtime getMax_Instantiate_time() {
        return Max_Instantiate_time;
    }

    public void setMax_Instantiate_time(Workingtime max_Instantiate_time) {
        Max_Instantiate_time = max_Instantiate_time;
    }

    public Workingtime getMean_Instantiate_time() {
        return Mean_Instantiate_time;
    }

    public void setMean_Instantiate_time(Workingtime mean_Instantiate_time) {
        Mean_Instantiate_time = mean_Instantiate_time;
    }

    public Workingtime getDeviation_Instantiate_time() {
        return Deviation_Instantiate_time;
    }

    public void setDeviation_Instantiate_time(Workingtime deviation_Instantiate_time) {
        Deviation_Instantiate_time = deviation_Instantiate_time;
    }

    public Decide_Activation_Type getDecisiontype() {
        return Decisiontype;
    }

    public void setDecisiontype(Decide_Activation_Type decisiontype) {
        Decisiontype = decisiontype;
    }
}
