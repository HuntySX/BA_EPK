package com.company.Simulation.Simulation_Threading;

import com.company.Simulation.Simulation_Base.Data.Event_List;

import java.util.concurrent.locks.ReentrantLock;

public class Event_Gate {
    private static Event_Gate event_gate;
    private static Event_List event_List;
    private static java.util.concurrent.locks.Lock event_Lock;

    public static Event_Gate get_Event_Gate() {
        if (event_gate == null) {
            event_gate = new Event_Gate();
            event_List = new Event_List();
            event_Lock = new ReentrantLock();
        }
        return event_gate;
    }

    public synchronized Event_List getEvent_List() {
        return event_List;
    }

    public synchronized java.util.concurrent.locks.Lock getEvent_Lock() {
        return event_Lock;
    }

    public synchronized void setEvent_List(Event_List event_List) {
        Event_Gate.event_List = event_List;
    }
}
