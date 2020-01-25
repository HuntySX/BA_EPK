package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Data.Event_List;

public class Event_Gate {
    private static Event_Gate event_gate;
    private Event_List event_List;
    private java.util.concurrent.locks.Lock event_Lock;

    public static Event_Gate get_Event_Gate() {
        if (event_gate == null) {
            event_gate = new Event_Gate();
        }
        return event_gate;
    }

    public synchronized Event_List getEvent_List() {
        return event_List;
    }

    public java.util.concurrent.locks.Lock getEvent_Lock() {
        return event_Lock;
    }

    public synchronized void setEvent_List(Event_List event_List) {
        this.event_List = event_List;
    }
}
