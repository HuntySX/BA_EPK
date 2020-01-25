package com.company.Simulation.Queues_Gates;

import com.company.EPK.Event;
import com.company.Simulation.Data.Starting_List;

public class Starting_Gate {

    private static Starting_Gate starting_gate;
    private Event starting_Event;
    private Starting_List starting_list;
    private Starting_List starting_order;

    public static Starting_Gate getStarting_gate() {
        if (starting_gate == null) {
            starting_gate = new Starting_Gate();
        }
        return starting_gate;
    }

    public synchronized Starting_List getStarting_list() {
        return starting_list;
    }

    public synchronized void setStarting_list(Starting_List starting_list) {
        this.starting_list = starting_list;
    }

    public Event getStarting_Event() {
        return starting_Event;
    }

    public void setStarting_Event(Event starting_Event) {
        this.starting_Event = starting_Event;
    }

    public synchronized Starting_List getStarting_order() {
        return starting_order;
    }

    public synchronized void setStarting_order(Starting_List starting_order) {
        this.starting_order = starting_order;
    }
}
