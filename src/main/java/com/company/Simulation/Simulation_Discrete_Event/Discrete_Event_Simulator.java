package com.company.Simulation.Simulation_Discrete_Event;

import com.company.EPK.*;
import com.company.Run.Discrete_Event_Generator;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib.Event_Decider;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Discrete_Event_Simulator {
    private EPK EPK;
    private Settings Settings;
    private Printer_Gate printer_gate;
    private Printer_Queue printer_queue;
    private Event_Calendar event_Calendar;
    private Event_Decider event_Decider;
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
        this.event_Decider = Generator.getEvent_Decider();
        LocalTime begin = Settings.getBeginTime();
        LocalTime end = Settings.getEndTime();

        //TODO Instantiate EPK,Users;Resources,Settings from File;
        //TODO Events generieren, EPK aufnehmen, Run starten, Alle Settings verteilen,

    }

    public void run() throws Exception {

        while (not_stopped) { //TODO BY TIME
            List<Instance_Workflow> latest_Instances = new ArrayList<>();
            Simulation_Event_List upcoming_Events = event_Calendar.getUpcoming_List();
            Simulation_Waiting_List waiting_list = event_Calendar.getWaiting_List();

            latest_Instances = upcoming_Events.getByTime(event_Calendar.getRuntime());

            Instance_Workflow to_Run = event_Decider.Decide_Event(latest_Instances, waiting_list);

            while (to_Run != null) {
                if (to_Run.getNode() instanceof Event) {

                    List<Node> Next_Elem = to_Run.getNode().getNext_Elem();
                    for (Node n : Next_Elem) {
                        event_Calendar.Add_To_Upcoming_List(to_Run, event_Calendar.getRuntime(), n);

                        //TODO Print Scheduled for n;
                    }

                }
                if (to_Run.getNode() instanceof Con_Split) {

                }
                if (to_Run.getNode() instanceof Con_Join) {

                }
                if (to_Run.getNode() instanceof Function) {

                    //FALL1: To_Run Arbeitet noch nicht an Function
                    if (!to_Run.isWorking()) {
                        //FALL Optimum nicht erforderlich
                        if (!Settings.get_Optimal_Loadout()) {

                            List<Workforce> workforces = ((Function) to_Run.getNode()).getNeeded_Workforce();
                            List<Resource> resources = ((Function) to_Run.getNode()).getNeeded_Resources();
                            List<Resource> CalculateResource = new ArrayList<>();
                            List<User> CalculateUsers = new ArrayList<>();

                            //CHECK FOR USER WORKFORCE AVAILABLE
                            for (User u : users) {
                                if (!u.isActive()) {
                                    List<Workforce> capable = u.getWorkforce();
                                    for (Workforce cap : capable) {
                                        if (workforces.contains(cap)) {
                                            if (CalculateUsers.contains(u)) {
                                                CalculateUsers.add(u);
                                            }
                                            workforces.remove(cap);
                                        }
                                    }
                                    if (workforces.isEmpty()) {
                                        break;
                                    }
                                }
                            }
                            if (!workforces.isEmpty()) {
                                throw new Exception();
                                //TODO Exception oder Auf Waiting List?
                            }

                            //CHECK RESOURCE LIMITS
                            else {

                                boolean not_fullfillable = false;
                                for (Resource res : ((Function) to_Run.getNode()).getNeeded_Resources()) {
                                    if (not_fullfillable) {
                                        break;
                                    }
                                    for (Resource r : resources) {
                                        if (r.getID() == res.getID() && r.getCount() >= res.getCount()) {
                                            Resource countres = new Resource(r.getName(), res.getCount(), r.getID());
                                            CalculateResource.add(countres);
                                        } else if (r.getID() == res.getID() && r.getCount() < res.getCount()) {
                                            not_fullfillable = true;
                                            break;
                                        }
                                    }
                                }
                                if (not_fullfillable) {
                                    //TODO Exceoption oder auf Waiting List?
                                } else {
                                    if (!CalculateResource.isEmpty()) {
                                        for (Resource res : CalculateResource) {
                                            for (Resource r : resources) {
                                                if (res.getID() == r.getID()) {
                                                    r.setCount(r.getCount() - res.getCount());
                                                }
                                            }
                                        }
                                    }
                                    to_Run.setWorking(true);
                                    LocalTime Duration = event_Calendar.getRuntime().
                                            Instance_Workflow
                                    new_Instance = new Instance_Workflow(to_Run.getInstance(), to_Run.getTo_Start().plus(((Function) to_Run.getNode()).getWborkingTime()), to_Run.getNode());
                                }
                                //TODO Activate Instance
                            }
                        }

                    if (to_Run.isWorking()) {
                        //TODO Deactivate Instance, Add Next Elem as new Instance to Upcoming List
                    }
                }

                upcoming_Events = event_Calendar.getUpcoming_List();
                waiting_list = event_Calendar.getWaiting_List();
                latest_Instances = upcoming_Events.getByTime(event_Calendar.getRuntime());
                to_Run = event_Decider.Decide_Event(latest_Instances, waiting_list);
                if (to_Run == null) {
                    if (!latest_Instances.isEmpty()) {
                        for (Instance_Workflow Instance : latest_Instances) {
                            event_Calendar.Add_To_Waiting_List(Instance);
                            upcoming_Events.remove_from_EventList(Instance);
                        }
                    }
                    event_Calendar.jump();
                }


            }

        }
        //TODO Events durchlaufen.
    }
}
