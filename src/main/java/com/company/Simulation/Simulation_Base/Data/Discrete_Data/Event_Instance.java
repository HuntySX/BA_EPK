package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.EPK_Node;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public class Event_Instance extends Simulation_Instance {

    private List<EPK_Node> Scheduled_Work;
    private List<EPK_Node> Finished_Work;

    public Event_Instance(int case_ID) {
        super(case_ID);
        Scheduled_Work = new ArrayList<>();
        Finished_Work = new ArrayList<>();
    }

    public List<EPK_Node> getScheduled_Work() {
        return Scheduled_Work;
    }

    public void setScheduled_Work(List<EPK_Node> scheduled_Work) {
        Scheduled_Work = scheduled_Work;
    }

    public void add_To_Finished_Work(EPK_Node EPKNode) {
        if (EPKNode != null) {
            {
                Finished_Work.add(EPKNode);
                Scheduled_Work.remove(EPKNode);
            }
        }
    }

    public void add_To_Scheduled_Work(EPK_Node EPKNode) {
        if (EPKNode != null) {
            Scheduled_Work.add(EPKNode);
        }
    }

    public void remove_from_Finished_Work(EPK_Node EPKNode) {
        if (EPKNode != null) {
            Finished_Work.remove(EPKNode);
        }
    }

    public void remove_from_Scheduled_Work(EPK_Node EPKNode) {
        if (EPKNode != null) {
            Finished_Work.add(EPKNode);
            Scheduled_Work.remove(EPKNode);
        }
    }

    public List<EPK_Node> getFinished_Work() {
        return Finished_Work;
    }

    public void setFinished_Work(List<EPK_Node> finished_Work) {
        Finished_Work = finished_Work;
    }
}
