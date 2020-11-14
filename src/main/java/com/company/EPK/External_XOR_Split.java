package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.External_XOR_Instance_Lock;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow_XOR;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

import java.util.List;

public class External_XOR_Split extends EPK_Node implements Printable_Node {

    private List<External_XOR_Instance_Lock> external_XOR_InstanceLocks;
    private Workingtime Timeout_Time;
    private Event Timeout;
    private Event Positive;
    private Event Negative;

    private int Chance_Pos_Neg;

    public External_XOR_Split(int ID, Event timeout, Event positive, Event negative, int chance_Pos_Neg) {
        super(ID);
        Timeout = timeout;
        Positive = positive;
        Negative = negative;
        Chance_Pos_Neg = chance_Pos_Neg;
    }

    public External_XOR_Split(int ID) {
        super(ID);
    }

    public Object newExternal_Event(Event_Instance instance, Workingtime Runtime) {
        return new Object();
    }

    public EPK_Node getPath(Instance_Workflow_XOR instance) {
        External_XOR_Instance_Lock to_delete = null;
        EPK_Node next_Elem = null;
        for (External_XOR_Instance_Lock to_unlock : external_XOR_InstanceLocks) {
            if (to_unlock.getInstance().equals(instance.getInstance()) && instance.getXOR_ID().equals(to_unlock.getXOR_Lock())) {
                to_delete = to_unlock;

                next_Elem = decideNextElem(instance, to_unlock);
            }
        }

    }

    private EPK_Node decideNextElem(Instance_Workflow_XOR instance, External_XOR_Instance_Lock to_unlock) {


    }

    public Event getTimeout() {
        return Timeout;
    }

    public void setTimeout(Event timeout) {
        Timeout = timeout;
    }

    public Event getPositive() {
        return Positive;
    }

    public void setPositive(Event positive) {
        Positive = positive;
    }

    public Event getNegative() {
        return Negative;
    }

    public void setNegative(Event negative) {
        Negative = negative;
    }

    public int getChance_Pos_Neg() {
        return Chance_Pos_Neg;
    }

    public void setChance_Pos_Neg(int chance_Pos_Neg) {
        Chance_Pos_Neg = chance_Pos_Neg;
    }
}
