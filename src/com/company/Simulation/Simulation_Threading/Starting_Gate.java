package com.company.Simulation.Simulation_Threading;

import com.company.EPK.Event;
import com.company.Simulation.Simulation_Base.Data.Starting_List;

public class Starting_Gate {

    private static Starting_Gate starting_gate;
    private static Event starting_Event;
    private static Starting_List starting_list;
    private static Starting_List starting_order;

    public static Starting_Gate getStarting_gate() {
        if (starting_gate == null) {
            starting_gate = new Starting_Gate();
            starting_list = new Starting_List();
            starting_order = new Starting_List();
            starting_Event = null;
        }
        return starting_gate;
    }

    public synchronized Starting_List getStarting_list() {
        return starting_list;
    }

    public synchronized void setStarting_list(Starting_List starting_list) {
        Starting_Gate.starting_list = starting_list;
    }

    public Event getStarting_Event() {
        return starting_Event;
    }

    public void setStarting_Event(Event starting_Event) {
        Starting_Gate.starting_Event = starting_Event;
    }

    public synchronized Starting_List getStarting_order() {
        return starting_order;
    }

    public synchronized void setStarting_order(Starting_List starting_order) {
        Starting_Gate.starting_order = starting_order;
    }
}
