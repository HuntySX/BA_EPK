package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Simulation_Event_List {

    private List<Instance_Workflow> Workflows;

    public Simulation_Event_List() {
        Workflows = new ArrayList<>();
    }

    public List<Instance_Workflow> getWorkflows() {
        return Workflows;
    }

    public void addTimedEvent(Instance_Workflow instance) {
        if (Workflows.isEmpty()) {
            Workflows.add(instance);
        } else {
            ListIterator iter = Workflows.listIterator();
            boolean added = false;
            while (iter.hasNext()) {
                Instance_Workflow List_Workflow = (Instance_Workflow) iter.next();

                if (List_Workflow.getTo_Start().isAfter(instance.getTo_Start()) || (List_Workflow.getTo_Start() == instance.getTo_Start())) {
                    iter.previous();
                    iter.add(instance);
                    added = true;
                    break;
                }
            }
            if (!iter.hasNext() && !added) {
                iter.add(instance);
            }
        }
    }

    public List<Instance_Workflow> getByTime(LocalTime time) {

        ListIterator iter = Workflows.listIterator();
        List<Instance_Workflow> result = new ArrayList<>();
        while (iter.hasNext()) {
            Instance_Workflow List_Workflow = (Instance_Workflow) iter.next();
            if (List_Workflow.getTo_Start().isAfter(time)) {
                break;
            } else {
                result.add(List_Workflow);
            }
        }
        Workflows.removeAll(result);
        return result;
    }

    public void remove_from_EventList(Instance_Workflow Instance) {
        Workflows.remove(Instance);
    }

    public boolean TimeEquals(LocalTime event_Time, LocalTime time) {
        return event_Time.getHour() == time.getHour() && event_Time.getMinute() == time.getMinute() && event_Time.getSecond() == time.getSecond();
    }

//    public boolean TimeGreaterThan(LocalTime event_Time, LocalTime time) {
//        return event_Time.getHour() > time.getHour() || event_Time.getMinute() > time.getMinute() || event_Time.getSecond() > time.getSecond();
//    }
}
