package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Event_Instance;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.External_XOR_Instance_Lock;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Instance_Workflow_XOR;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Workingtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class External_XOR_Split extends EPK_Node implements Printable_Node {

    private List<External_XOR_Instance_Lock> external_XOR_InstanceLocks;
    private Workingtime Timeout_Time;
    private EPK_Node Timeout;
    private EPK_Node Positive;
    private EPK_Node Negative;

    private int Chance_Pos_Neg;

    public External_XOR_Split(int ID, EPK_Node timeout, EPK_Node positive, EPK_Node negative, int chance_Pos_Neg) {
        super(ID);
        external_XOR_InstanceLocks = new ArrayList<>();
        Timeout = timeout;
        Positive = positive;
        Negative = negative;
        Chance_Pos_Neg = chance_Pos_Neg;
    }

    public External_XOR_Split(int ID) {
        super(ID);
        external_XOR_InstanceLocks = new ArrayList<>();
        Timeout_Time = new Workingtime();
        Chance_Pos_Neg = 0;
    }

    public External_XOR_Split(int id, int chance_pos_neg) {
        super(id);
        external_XOR_InstanceLocks = new ArrayList<>();
        Timeout = null;
        Positive = null;
        Negative = null;
        Chance_Pos_Neg = chance_pos_neg;
    }

    public External_XOR_Instance_Lock newExternal_Event(Event_Instance instance, Workingtime Runtime) {
        External_XOR_Instance_Lock Instance_Lock = new External_XOR_Instance_Lock(instance, Runtime);
        external_XOR_InstanceLocks.add(Instance_Lock);
        return Instance_Lock;
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
        return next_Elem;
    }

    private EPK_Node decideNextElem(Instance_Workflow_XOR instance, External_XOR_Instance_Lock to_unlock) {
        if (Timeout_Time.isAfter_Equal(to_unlock.getTime())) {
            return Timeout;
        } else {
            Random rand = new Random();
            int chance = rand.nextInt(100);
            if (chance < Chance_Pos_Neg) {
                return Positive;
            } else {
                return Negative;
            }
        }
    }

    public EPK_Node getTimeout() {
        return Timeout;
    }

    public void setTimeout(EPK_Node timeout) {
        Timeout = timeout;
    }

    public EPK_Node getPositive() {
        return Positive;
    }

    public void setPositive(EPK_Node positive) {
        Positive = positive;
    }

    public EPK_Node getNegative() {
        return Negative;
    }

    public void setNegative(EPK_Node negative) {
        Negative = negative;
    }

    public int getChance_Pos_Neg() {
        return Chance_Pos_Neg;
    }

    public void setChance_Pos_Neg(int chance_Pos_Neg) {
        Chance_Pos_Neg = chance_Pos_Neg;
    }
}
