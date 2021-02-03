package com.company.Run;

import com.company.EPK.Activating_Function;
import com.company.EPK.EPK;
import com.company.EPK.EPK_Node;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib.Event_Decider;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Loader.EPK_Loader;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Loader.Resource_Loader;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Loader.Settings_Loader;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Loader.User_Loader;
import com.company.Simulation.Simulation_Base.Data.Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;
import com.company.Simulation.Simulation_Discrete_Event.Discrete_Event_Simulator;

import java.util.List;

public class Discrete_Event_Generator {

    private EPK EPK;
    private Settings Settings;
    private EPK_Loader EPK_loader;
    private Settings_Loader Settings_loader;
    private Resource_Loader Resource_loader;
    private User_Loader User_loader;
    private final Printer_Gate printer_Gate;
    private final Printer_Queue printer_Queue;
    private List<User> Users;
    private List<Resource> Resources;
    private Discrete_Event_Simulator Simulation;
    private Event_Calendar event_Calendar;
    private Event_Decider event_decider;
    private int case_ID;

    public Discrete_Event_Generator(String EPK_file, String Setting_file, String User_file, String Resource_file) {
        this.EPK_loader = new EPK_Loader(EPK_file);
        this.Settings_loader = new Settings_Loader(Setting_file);
        this.User_loader = new User_Loader(User_file);
        this.Resource_loader = new Resource_Loader(Resource_file);
        this.EPK = null;
        this.Simulation = null;
        this.printer_Queue = new Printer_Queue();
        this.printer_Gate = Printer_Gate.get_Printer_Gate();
        this.event_decider = null;
        this.case_ID = 0;
        Thread T = new Thread();
        printer_Queue.setT(T);
    }

    public Discrete_Event_Generator(EPK epk, Settings settings, List<User> Users, List<Resource> Resources, List<List<External_Event>> external_events) {
        this.EPK = epk;
        this.Settings = settings;
        this.Users = Users;
        this.Resources = Resources;
        this.event_Calendar = new Event_Calendar(Settings, EPK, this);
        this.event_Calendar.setExternal_Events(external_events);
        this.event_decider = new Event_Decider(Settings, Users, Resources, event_Calendar);
        this.Simulation = new Discrete_Event_Simulator(this);
        this.printer_Queue = new Printer_Queue();
        this.printer_Gate = Printer_Gate.get_Printer_Gate();
        this.case_ID = 1;
        Thread T = new Thread();
        printer_Queue.setT(T);
    }


    public void generate() {
        this.EPK = EPK_loader.generate_EPK();
        this.Settings = Settings_loader.generate_Settings();
        this.Users = User_loader.generate_User_List();
        this.Resources = Resource_loader.generate_Resources();
        this.event_Calendar = new Event_Calendar(Settings, EPK, this);
        this.event_decider = new Event_Decider(Settings, Users, Resources, event_Calendar);
        this.Simulation = new Discrete_Event_Simulator(this);
    }

    public void run() {
        event_Calendar.fillCalendar();
        System.out.println("Calendar filled");
        List<Simulation_Event_List> Events = event_Calendar.getUpcoming_List();
        for (Simulation_Event_List List : Events) {
            System.out.println("List for Day: " + Events.indexOf(List) + 1);
            for (Instance_Workflow Instance : List.getWorkflows()) {
                System.out.println("Time to Start for Instance " + Instance.getInstance().getCase_ID() + ": " + Instance.getTo_Start());
            }
        }
        EPK.generateMapping();
        EPK.generateGateMapping();
        for (EPK_Node n : EPK.getElements()) {
            System.out.println(n.ReachabletoString());
        }
        giveCalToActivatings();
        Simulation.run();
    }

    private void giveCalToActivatings() {
        for (EPK_Node Node : EPK.getElements()) {
            if (Node instanceof Activating_Function) {
                ((Activating_Function) Node).setCalendar(event_Calendar);
            }
        }
    }

    public EPK getEPK() {
        return EPK;
    }

    public Settings getSettings() {
        return Settings;
    }

    public Printer_Queue getPrinter_Queue() {
        return printer_Queue;
    }

    public List<User> getUsers() {
        return Users;
    }

    public List<Resource> getResources() {
        return Resources;
    }

    public Event_Calendar getEvent_Calendar() {
        return event_Calendar;
    }

    public Event_Decider getEvent_Decider() {
        return event_decider;
    }

    public int get_Unique_case_ID() {
        int id = case_ID;
        case_ID = case_ID + 1;
        return id;
    }

    //TODO Graphische Visualisierung mit Graphviz
    //TODO PROCESS MINING!
}
