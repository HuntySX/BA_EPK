package com.company.Print.EventDriven;

import com.company.Enums.Decide_Activation_Type;
import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

import java.util.List;

public class Print_Activating_Function extends Print_Function {
    private Connected_Elem_Print Activating_Start_Event;
    private Workingtime Instantiate_Time;
    private Decide_Activation_Type Decisiontype;

    public Print_Activating_Function(int ID, List<Connected_Elem_Print> Next_elements, String tag, Function_Type function_type, boolean concurrently, List<Connected_Resource_Print> resources, List<Connected_Workforce_Print> workforces, Workingtime workingTime, Connected_Elem_Print activating_Start_Event, Workingtime instantiate_Time, Decide_Activation_Type decisiontype) {
        super(ID, Next_elements, tag, function_type, concurrently, resources, workforces, workingTime);
        super.setNode_Type(Node_Type.Activating_Function);
        Activating_Start_Event = activating_Start_Event;
        Instantiate_Time = instantiate_Time;
        Decisiontype = decisiontype;
    }

    public Connected_Elem_Print getActivating_Start_Event() {
        return Activating_Start_Event;
    }

    public void setActivating_Start_Event(Connected_Elem_Print activating_Start_Event) {
        Activating_Start_Event = activating_Start_Event;
    }

    public Workingtime getInstantiate_Time() {
        return Instantiate_Time;
    }

    public void setInstantiate_Time(Workingtime instantiate_Time) {
        Instantiate_Time = instantiate_Time;
    }

    public Decide_Activation_Type getDecisiontype() {
        return Decisiontype;
    }

    public void setDecisiontype(Decide_Activation_Type decisiontype) {
        Decisiontype = decisiontype;
    }
}
