package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.Node;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public class Event_Instance extends Simulation_Instance {

    private List<Node> Scheduled_Work;
    private List<Node> Finished_Work;

    public Event_Instance(int case_ID) {
        super(case_ID);
        Scheduled_Work = new ArrayList<>();
        Finished_Work = new ArrayList<>();
    }

    public List<Node> getScheduled_Work() {
        return Scheduled_Work;
    }

    public void setScheduled_Work(List<Node> scheduled_Work) {
        Scheduled_Work = scheduled_Work;
    }

    public void add_To_Finished_Work(Node node) {
        if (node != null) {
            {
                Finished_Work.add(node);
                Scheduled_Work.remove(node);
            }
        }
    }

    public void add_To_Scheduled_Work(Node node) {
        if (node != null) {
            Scheduled_Work.add(node);
        }
    }

    public void remove_from_Finished_Work(Node node) {
        if (node != null) {
            Finished_Work.remove(node);
        }
    }

    public void remove_from_Scheduled_Work(Node node) {
        if (node != null) {
            Finished_Work.add(node);
            Scheduled_Work.remove(node);
        }
    }

    public List<Node> getFinished_Work() {
        return Finished_Work;
    }

    public void setFinished_Work(List<Node> finished_Work) {
        Finished_Work = finished_Work;
    }
}
