package com.company.Print.EventDriven;

import com.company.Enums.Function_Type;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

import java.util.List;

public class Print_Function extends Print_Event_Driven_File {
    private String Tag;
    private Function_Type function_type;
    private boolean concurrently;
    private List<Connected_Resource_Print> Resources;
    private List<Connected_Workforce_Print> Workforces;
    private Workingtime WorkingTime;

    public Print_Function(int ID, List<Connected_Elem_Print> Next_elements, String tag, Function_Type function_type, boolean concurrently, List<Connected_Resource_Print> resources, List<Connected_Workforce_Print> workforces, Workingtime workingTime) {
        super(Node_Type.Function, ID, Next_elements);
        Tag = tag;
        this.function_type = function_type;
        this.concurrently = concurrently;
        Resources = resources;
        Workforces = workforces;
        WorkingTime = workingTime;
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

    public Workingtime getWorkingTime() {
        return WorkingTime;
    }

    public void setWorkingTime(Workingtime workingTime) {
        WorkingTime = workingTime;
    }
}
