package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow;

import java.util.ArrayList;
import java.util.List;

public class Gate_Waiting_Instance {
    List<EPK_Node> arrived;
    EPK_Node At_Gate;
    Instance_Workflow First_Instance;
    int Waiting_ID;

    public Gate_Waiting_Instance(Instance_Workflow First_Instance, EPK_Node Arrived_At, int Waiting_ID, EPK_Node Gate) {
        this.First_Instance = First_Instance;
        arrived = new ArrayList<>();
        arrived.add(Arrived_At);
        this.Waiting_ID = Waiting_ID;
        At_Gate = Gate;
    }

    public List<EPK_Node> getArrived() {
        return arrived;
    }

    public int getWaiting_ID() {
        return Waiting_ID;
    }

    public void setWaiting_ID(int waiting_ID) {
        Waiting_ID = waiting_ID;
    }

    public Instance_Workflow getFirst_Instance() {
        return First_Instance;
    }

    public void setFirst_Instance(Instance_Workflow first_Instance) {
        First_Instance = first_Instance;
    }

    public EPK_Node getAt_Gate() {
        return At_Gate;
    }

    public void setAt_Gate(EPK_Node at_Gate) {
        At_Gate = at_Gate;
    }

    public void setArrived(List<EPK_Node> arrived) {
        this.arrived = arrived;
    }
}
