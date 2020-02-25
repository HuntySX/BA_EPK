package com.company.EPK;

import com.company.Simulation.Simulation_Base.Threading_Instance.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public class EPK {

    private List<Node> elements;
    private List<Event> events;
    private List<Function> functions;
    private List<Con_Join> connector_Join;
    private List<Con_Split> connector_Split;
    private Event start_Event;
    private Event end_Event;
    private List<Simulation_Instance> Instances;

    public EPK(List<Node> elements, List<Event> events, List<Function> functions, List<Con_Join> connector_Join, List<Con_Split> connector_Split) {
        this.elements = elements;
        this.events = events;
        this.functions = functions;
        this.connector_Join = connector_Join;
        this.connector_Split = connector_Split;
        this.Instances = new ArrayList<>();
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
    }

    public Event getStart_Event() {
        return start_Event;
    }

    public void setStart_Event(Event start_Event) {
        this.start_Event = start_Event;
    }

    public Event getEnd_Event() {
        return end_Event;
    }

    public void setEnd_Event(Event end_Event) {
        this.end_Event = end_Event;
    }

    public void add_Instance(Simulation_Instance i) {
        if (i != null) {
            Instances.add(i);
        }
    }

    public List<Node> getElements() {
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
}
