package com.company.Simulation.Simulation_Discrete_Event;

import com.company.EPK.EPK;
import com.company.Run.Discrete_Event_Generator;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
import java.util.List;

public class Discrete_Event_Simulator {
    private EPK EPK;
    private Settings Settings;
    private Printer_Gate printer_gate;
    private Printer_Queue printer_queue;
    private Event_Calendar event_Calendar;
    private List<Resource> resources;
    private List<User> users;

    public Discrete_Event_Simulator(Discrete_Event_Generator Generator) {

        //TODO Erhalte relevante Settings aus Generator. speicher diese hier.
        this.EPK = Generator.getEPK();
        this.resources = Generator.getResources();
        this.users = Generator.getUsers();
        this.event_Calendar = Generator.getEvent_Calendar();
        this.printer_queue = Generator.getPrinter_Queue();
        this.printer_gate = Printer_Gate.get_Printer_Gate();
        this.Settings = Generator.getSettings();
        LocalTime begin = Settings.getBeginTime();
        LocalTime end = Settings.getEndTime();

        //TODO Instantiate EPK,Users;Resources,Settings from File;
        //TODO Events generieren, EPK aufnehmen, Run starten, Alle Settings verteilen,

    }

    public void run() {

        //TODO Events durchlaufen.
    }
}
