package com.company.Simulation.Simulation_Discrete_Event;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
import java.util.List;

public class Discrete_Event_Simulator {
    private Event_Calendar event_Calendar;
    private List<Resource> resources;
    private List<User> users;

    public Discrete_Event_Simulator(List<Resource> resources, List<User> users) {
        //TODO Erhalte relevante Settings aus Generator. speicher diese hier.
        this.resources = resources;
        this.users = users;
        LocalTime begin = LocalTime.of(8, 0, 0);
        LocalTime end = LocalTime.of(18, 0, 0);
        event_Calendar = new Event_Calendar(begin, end);
        //TODO Instantiate EPK,Users;Resources,Settings from File;
        //TODO Events generieren, EPK aufnehmen, Run starten, Alle Settings verteilen,

    }

}
