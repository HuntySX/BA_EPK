package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Simulation_Waiting_List {

    private List<Event_Instance> Event_List;

    public Simulation_Waiting_List() {
        Event_List = new ArrayList<>();
    }

    public void addTimedEvent(Event_Instance event) {
        if (Event_List.isEmpty()) {
            Event_List.add(event);
        } else {
            ListIterator iter = Event_List.listIterator();
            while (iter.hasNext()) {
                Event_Instance List_Event = (Event_Instance) iter.next();
                if (List_Event.getTime().isAfter(event.getTime())) {
                    iter.previous();
                    iter.add(event);
                    break;
                }
            }
        }
    }
}
