package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public class EPK {

    private final List<EPK_Node> elements;
    private final List<Event> events;
    private final List<Function> functions;
    private final List<Con_Join> connector_Join;
    private final List<Con_Split> connector_Split;
    private List<Event> start_Event;
    private List<Event> end_Event;
    private final List<Simulation_Instance> Instances;
    private List<Start_Event> discrete_start_Events;

    public EPK(List<EPK_Node> elements, List<Event> events, List<Function> functions, List<Con_Join> connector_Join, List<Con_Split> connector_Split) {
        this.elements = elements;
        this.events = events;
        this.functions = functions;
        this.connector_Join = connector_Join;
        this.connector_Split = connector_Split;
        this.Instances = new ArrayList<>();
    }

    public EPK(List<EPK_Node> elements, List<Event> events, List<Function> functions, List<Con_Join> connector_Join, List<Con_Split> connector_Split, List<Start_Event> discrete_start_Events) {
        this.elements = elements;
        this.events = events;
        this.functions = functions;
        this.connector_Join = connector_Join;
        this.connector_Split = connector_Split;
        this.Instances = new ArrayList<>();
        this.discrete_start_Events = discrete_start_Events;
    }

    public EPK(List<EPK_Node> Nodes, List<Start_Event> Start_Events) {
        this.elements = Nodes;
        this.events = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.connector_Join = new ArrayList<>();
        this.connector_Split = new ArrayList<>();
        this.discrete_start_Events = Start_Events;
        this.end_Event = new ArrayList<>();
        this.Instances = new ArrayList<>();
        this.start_Event = new ArrayList<>();
    }

    public EPK() {
        this.elements = new ArrayList<>();
        this.events = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.connector_Join = new ArrayList<>();
        this.connector_Split = new ArrayList<>();
        this.start_Event = null;
        this.end_Event = null;
        this.Instances = new ArrayList<>();
        this.discrete_start_Events = new ArrayList<>();
    }

    public void setDiscrete_start_Events(List<Start_Event> discrete_start_Events) {
        this.discrete_start_Events = discrete_start_Events;
    }

    public void add_Discrete_start_Events(Start_Event Event) {
        if (Event != null && !discrete_start_Events.contains(Event)) {
            discrete_start_Events.add(Event);
        }
    }

    public List<Start_Event> get_Discrete_Start_Events() {
        return discrete_start_Events;
    }

    public List<Event> getStart_Events() {
        return start_Event;
    }

    public void setStart_Events(List<Event> start_Event) {
        this.start_Event = start_Event;
    }

    public List<Event> getEnd_Events() {
        return end_Event;
    }

    public void setEnd_Events(List<Event> end_Event) {
        this.end_Event = end_Event;
    }

    public void add_Instance(Simulation_Instance i) {
        if (i != null) {
            Instances.add(i);
        }
    }

    public List<EPK_Node> getElements() {
        return elements;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public List<Con_Join> getConnector_Join() {
        return connector_Join;
    }

    public List<Con_Split> getConnector_Split() {
        return connector_Split;
    }

    public void add_Function(Function f) {
        functions.add(f);
        elements.add(f);
    }

    public void add_Event(Event e) {
        events.add(e);
        elements.add(e);
    }

    public void add_con_Join(Con_Join cj) {
        connector_Join.add(cj);
        elements.add(cj);
    }

    public void add_con_Split(Con_Split cs) {
        connector_Split.add(cs);
        elements.add(cs);
    }

    public void add_Start_Event(Event ev) {
        if (!start_Event.contains(ev)) {
            start_Event.add(ev);
        }
    }

    public void remove_Start_Event(Event ev) {
        start_Event.remove(ev);
    }

    public void add_End_Event(Event ev) {
        if (!end_Event.contains(ev)) {
            end_Event.add(ev);
        }
    }

    public void remove_End_Event(Event ev) {
        if (!end_Event.contains(ev)) {
            end_Event.remove(ev);
        }
    }

    public void generateMapping() {
        for (EPK_Node n : elements) {
            List<EPK_Node> Reachable = new ArrayList<>();
            List<EPK_Node> Workinglist = new ArrayList<>();
            Workinglist.addAll(n.getNext_Elem());
            while (!Workinglist.isEmpty()) {
                EPK_Node to_reach = Workinglist.get(0);
                for (EPK_Node next_Node : to_reach.getNext_Elem()) {
                    if (!(Workinglist.contains(next_Node) || Reachable.contains(next_Node))) {
                        Workinglist.add(next_Node);
                    }
                }
                Workinglist.remove(0);
                Reachable.add(to_reach);
            }
            n.setReachable_Elements(Reachable);
        }
    }

    public void generateGateMapping() {
        for (EPK_Node n : elements) {
            for (EPK_Node m : n.getNext_Elem()) {
                if (m instanceof Event_Con_Join) {
                    ((Event_Con_Join) m).getPrevious_Elements().add(n);
                }
            }
        }
        for (EPK_Node n : elements) {
            if (n instanceof Event_Con_Join) {
                System.out.println(((Event_Con_Join) n).getPrevious_Elements().toString());
            }
        }
    }
}
