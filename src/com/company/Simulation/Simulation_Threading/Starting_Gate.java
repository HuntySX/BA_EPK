package com.company.Simulation.Simulation_Threading;

import com.company.EPK.Event;
import com.company.Simulation.Simulation_Base.Data.Threading_Data.Threading_Starting_List;

public class Starting_Gate {

    private static Starting_Gate starting_gate;
    private static Event starting_Event;
    private static Threading_Starting_List threadingStarting_list;
    private static Threading_Starting_List starting_order;

    public static Starting_Gate getStarting_gate() {
        if (starting_gate == null) {
            starting_gate = new Starting_Gate();
            threadingStarting_list = new Threading_Starting_List();
            starting_order = new Threading_Starting_List();
            starting_Event = null;
        }
        return starting_gate;
    }

    public synchronized Threading_Starting_List getStarting_list() {
        return threadingStarting_list;
    }

    public synchronized void setStarting_list(Threading_Starting_List threadingStarting_list) {
        Starting_Gate.threadingStarting_list = threadingStarting_list;
    }

    public Event getStarting_Event() {
        return starting_Event;
    }

    public void setStarting_Event(Event starting_Event) {
        Starting_Gate.starting_Event = starting_Event;
    }

    public synchronized Threading_Starting_List getStarting_order() {
        return starting_order;
    }

    public synchronized void setStarting_order(Threading_Starting_List starting_order) {
        Starting_Gate.starting_order = starting_order;
    }
}
