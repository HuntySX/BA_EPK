package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.EPK_Node;

import java.time.LocalTime;


public class Instance_Workflow_XOR extends Instance_Workflow {

    private Object XOR_ID;

    public Instance_Workflow_XOR(Event_Instance instance, LocalTime to_Start, EPK_Node EPKNode, Object XOR_ID) {
        super(instance, to_Start, EPKNode);
    }

    public Object getXOR_ID() {
        return XOR_ID;
    }

    public void setXOR_ID(Object XOR_ID) {
        this.XOR_ID = XOR_ID;
    }
}
