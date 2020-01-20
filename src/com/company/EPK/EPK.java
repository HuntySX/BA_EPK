package com.company.EPK;

import java.util.List;

public class EPK {

    private List<Node> elements;
    private List<Event> events;
    private List<Function> functions;
    private List<Con_Join> connector_Join;
    private List<Con_Split> connector_Split;
    private Event start_Event;
    private Event end_Event;

    public EPK(List<Node> elements, List<Event> events, List<Function> functions, List<Con_Join> connector_Join, List<Con_Split> connector_Split) {
        this.elements = elements;
        this.events = events;
        this.functions = functions;
        this.connector_Join = connector_Join;
        this.connector_Split = connector_Split;
    }

    public EPK() {
        this.elements = null;
        this.events = null;
        this.functions = null;
        this.connector_Join = null;
        this.connector_Split = null;
    }

    public void Instance_Generator() {
        //TODO Randomizer
    }

}
