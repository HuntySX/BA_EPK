package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Simulation_Waiting_List {

    private final List<Instance_Workflow> Workflows;

    public Simulation_Waiting_List() {
        Workflows = new ArrayList<>();
    }

    public List<Instance_Workflow> getEvent_List() {
        return Workflows;
    }

    public void addTimedEvent(Instance_Workflow workflow) {
        if (Workflows.isEmpty()) {
            Workflows.add(workflow);
        } else {
            ListIterator iter = Workflows.listIterator();
            boolean added = false;
            while (iter.hasNext()) {
                Instance_Workflow Instance = (Instance_Workflow) iter.next();
                if (Instance.getTo_Start().isAfter(workflow.getTo_Start())) {
                    iter.previous();
                    iter.add(workflow);
                    added = true;
                    break;
                }
            }
            if (!added) {
                iter.add(workflow);
            }
        }
    }

    public void remove_from_WaitingList(Instance_Workflow Instance) {
        Workflows.remove(Instance);
    }

    public boolean containsInstance(Instance_Workflow to_run) {
        for (Instance_Workflow Instance : Workflows) {
            if (to_run.getInstance().equals(Instance.getInstance())) {
                return true;
            }
        }
        return false;
    }
}
