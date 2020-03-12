package com.company.Simulation.Simulation_Discrete_Event;

import com.company.EPK.*;
import com.company.Enums.Gate_Check_Status;
import com.company.Run.Discrete_Event_Generator;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib.Check_Condition_For_Event;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib.Event_Decider;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Gate_Check_Status.ADVANCE;
import static com.company.Enums.Gate_Check_Status.BLOCK;

public class Discrete_Event_Simulator {
    private EPK EPK;
    private Settings Settings;
    private Printer_Gate printer_gate;
    private Printer_Queue printer_queue;
    private Event_Calendar event_Calendar;
    private Event_Decider event_Decider;
    private List<Resource> resources;
    private List<User> users;
    private int runtimeDays;

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
        this.runtimeDays = Settings.getRuntimeDays();
        LocalTime begin = Settings.getBeginTime();
        LocalTime end = Settings.getEndTime();

        //TODO Instantiate EPK,Users;Resources,Settings from File;
        //TODO Events generieren, EPK aufnehmen, Run starten, Alle Settings verteilen,

    }

    public void run() throws Exception {

        for (int day = 0; day < runtimeDays; day++) { //TODO BY TIME

            while (event_Calendar.getRuntime().isBefore(event_Calendar.getEnd_Time())) {

                List<Instance_Workflow> latest_Instances = new ArrayList<>();
                Simulation_Event_List upcoming_Events = event_Calendar.get_Single_Upcoming_List(day);
                Simulation_Waiting_List waiting_list = event_Calendar.getWaiting_List();
                latest_Instances = upcoming_Events.getByTime(event_Calendar.getRuntime());
                Instance_Workflow to_Run = event_Decider.Decide_Event(latest_Instances, waiting_list);
                while (to_Run != null) {

                    if (to_Run.getNode() instanceof Event) {
                        to_Run.getInstance().add_To_Finished_Work(to_Run.getNode());
                        List<Node> Next_Elem = to_Run.getNode().getNext_Elem();
                        for (Node n : Next_Elem) {
                            to_Run.getInstance().add_To_Scheduled_Work(n);
                            Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                            event_Calendar.Add_To_Upcoming_List(new_Instance, day);

                            if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                //TODO Print Scheduled for n;
                            } else {
                                //TODO Print every Node Scheduled;
                            }
                        }
                    }

                    if (to_Run.getNode() instanceof Event_Con_Split) {
                        List<Node> Next_Elem = ((Event_Con_Split) to_Run.getNode()).choose_Next();
                        for (Node n : Next_Elem) {
                            to_Run.getInstance().add_To_Scheduled_Work(n);
                            Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                            event_Calendar.Add_To_Upcoming_List(new_Instance, day);
                            if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                //TODO Print Scheduled for n;
                            } else {
                                //TODO Print every Node Scheduled;
                            }
                        }

                    }
                    if (to_Run.getNode() instanceof Event_Con_Join) {

                        Gate_Check_Status check = ((Event_Con_Join) to_Run.getNode()).check_Previous_Elem(to_Run);
                        if (check == BLOCK) {
                            System.out.println("Instanz mit ID: " + to_Run.getInstance().getCase_ID() + "Besaß einen ungültigen" +
                                    "Teilbereich (Uhrzeit/Gate): " + to_Run.getNode().toString());
                        } else if (check == ADVANCE) {
                            List<Node> Next_Elem = to_Run.getNode().getNext_Elem();
                            for (Node n : Next_Elem) {
                                to_Run.getInstance().add_To_Scheduled_Work(n);
                                Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                                event_Calendar.Add_To_Upcoming_List(new_Instance, day);
                                if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                    //TODO Print Scheduled for n;
                                } else {
                                    //TODO Print every Node Scheduled;
                                }
                            }
                        } else {
                            //GATE NOT FULLFILLED; WAIT
                            LocalTime Actualize = event_Calendar.getRuntime();
                            Actualize.plusSeconds(1);
                            Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), Actualize, to_Run.getNode());
                            event_Calendar.Add_To_Upcoming_List(new_Instance, day);

                        }
                    }
                    if (to_Run.getNode() instanceof Function) {

                        //FALL1: To_Run Arbeitet noch nicht an Function
                        if (!to_Run.isWorking()) {
                            ActivateFunction(to_Run, day);
                        }

                        //FALL2: To_Run hat arbeit beendet:
                        if (to_Run.isWorking() && (to_Run.getTo_Start() == (event_Calendar.getRuntime()) || to_Run.getTo_Start().isBefore(event_Calendar.getRuntime()))) {
                            DeactivateFunction(to_Run, day);
                        }

                        //Actualize new Working Upcoming and Working List for Every Type of Node
                        //Get next torun, if empty, go back to while, end it, and start event_Calendar.jump()
                        upcoming_Events = event_Calendar.get_Single_Upcoming_List(day);
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

                        }

                    }
                }
                event_Calendar.jump();

            }
            event_Calendar.setRuntime(event_Calendar.getBegin_Time());
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    private void DeactivateFunction(Instance_Workflow to_Run, int day) {
        to_Run.setWorking(false);
        List<User> SetUsersFree = to_Run.getActive_User();
        List<Resource> SetResourceFree = to_Run.getActive_Resource();

        //Set Users Free
        for (User u : SetUsersFree) {
            if (u.isActive()) {
                u.setActive(false);
            }
        }
        //Set Resources Free
        for (Resource res : SetResourceFree) {
            for (Resource r : resources) {
                if (res.getID() == r.getID()) {
                    r.setCount(res.getCount() + r.getCount());
                    break;
                }
            }

            //Add Function as Finished and get next Elements
            List<Node> Next_Elem = to_Run.getNode().getNext_Elem();
            to_Run.getInstance().add_To_Finished_Work(to_Run.getNode());

            //TODO Print Finished to_Run.getInstance().getNode;

            //Instantiate new Workflow for each Element in GetNext_Elem
            for (Node n : Next_Elem) {
                to_Run.getInstance().add_To_Scheduled_Work(n);
                Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                event_Calendar.Add_To_Upcoming_List(new_Instance, day);

                if (Settings.getPrint_Only_Function() && n instanceof Function) {
                    //TODO Print Function Scheduled for n
                } else {
                    //TODO Print Scheduled everynode for n
                }
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    private void ActivateFunction(Instance_Workflow to_Run, int day) {
        boolean error = false;
        List<Resource> CalculateResource = new ArrayList<>();
        List<User> CalculateUsers = new ArrayList<>();
        //FALL Optimum nicht erforderlich
        if (!Settings.get_Optimal_Loadout()) {

            List<Workforce> workforces = ((Function) to_Run.getNode()).getNeeded_Workforce();
            List<Resource> resources = ((Function) to_Run.getNode()).getNeeded_Resources();

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
                System.out.println("DEBUG: Workforce not Empty but should be");
                System.out.println("Fixing: Adding Instance back to Waiting List");
                event_Calendar.getWaiting_List().addTimedEvent(to_Run);
                error = true;

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
                    System.out.println("DEBUG: Resources not provided but should be");
                    System.out.println("Fixing: Adding Instance back to Waiting List");
                    event_Calendar.getWaiting_List().addTimedEvent(to_Run);
                    error = true;
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
                }
            }
        } else {
            //TODO generate OPTIMAL LAYOUT
        }
        if (!error) {
            LocalTime Duration = event_Calendar.getRuntime();
            Duration.plusHours(((Function) to_Run.getNode()).getWorkingTime().getHour());
            Duration.plusMinutes(((Function) to_Run.getNode()).getWorkingTime().getMinute());
            Duration.plusSeconds(((Function) to_Run.getNode()).getWorkingTime().getSecond());
            to_Run.setWorking(true);
            event_Calendar.Remove_From_Upcoming_List(to_Run, day);
            for (User u : CalculateUsers) {
                u.setActive(true);
            }

            Instance_Workflow Running_Instance = new Instance_Workflow(to_Run.getInstance(), Duration, to_Run.getNode());
            Running_Instance.Add_Active_Users(CalculateUsers);
            Running_Instance.Add_Active_Resources(CalculateResource);
            event_Calendar.Add_To_Upcoming_List(Running_Instance, day);
        }
    }
}


