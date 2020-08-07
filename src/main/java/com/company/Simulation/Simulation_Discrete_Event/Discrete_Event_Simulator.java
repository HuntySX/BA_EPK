package com.company.Simulation.Simulation_Discrete_Event;

import com.company.EPK.*;
import com.company.Enums.Contype;
import com.company.Enums.Gate_Check_Status;
import com.company.Run.Discrete_Event_Generator;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib.Event_Decider;
import com.company.Simulation.Simulation_Base.Data.Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Gate_Check_Status.*;

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
        this.runtimeDays = Settings.getMax_RuntimeDays();
        LocalTime begin = Settings.getBeginTime();
        LocalTime end = Settings.getEndTime();

        //TODO Instantiate EPK,Users;Resources,Settings from File;
        //TODO Events generieren, EPK aufnehmen, Run starten, Alle Settings verteilen,

    }

    public void run() {
        boolean pushed_new_Elements = false;
        while (!event_Calendar.isFinished_cycle()) {
            while (event_Calendar.getRuntime().isBefore(event_Calendar.getEnd_Time()) && !event_Calendar.isFinished_cycle()) {

                List<Instance_Workflow> latest_Instances = new ArrayList<>();
                Simulation_Event_List upcoming_Events = event_Calendar.get_Single_Upcoming_List(event_Calendar.getAct_runtimeDay());
                Simulation_Waiting_List waiting_list = event_Calendar.getWaiting_List();
                latest_Instances.addAll(upcoming_Events.getByTime(event_Calendar.getRuntime()));
                Instance_Workflow to_Run = event_Decider.Decide_Event(latest_Instances, waiting_list);
                while (to_Run != null) {

                    if (to_Run.getEPKNode() instanceof Event) {

                        to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                        if (!(((Event) to_Run.getEPKNode()).is_End_Event())) {

                            List<EPK_Node> Next_Elem = to_Run.getEPKNode().getNext_Elem();
                            for (EPK_Node n : Next_Elem) {
                                to_Run.getInstance().add_To_Scheduled_Work(n);
                                Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                                if (n instanceof Event_Con_Join) {
                                    new_Instance.setComing_From(to_Run.getEPKNode());
                                }
                                event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());
                                System.out.println("Event Finished: " + ((Event) to_Run.getEPKNode()).getEvent_Tag() + "for: " +
                                        to_Run.getInstance().getCase_ID() +
                                        " At: [" + event_Calendar.getRuntime().toString() + "] Should be: [" + to_Run.getTo_Start().toString() + "]");

                                if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                    //TODO Print Scheduled for n;
                                } else {
                                    //TODO Print every Node Scheduled;
                                }
                            }
                        } else {
                            System.out.println("Instance finished at: " + ((Event) to_Run.getEPKNode()).getEvent_Tag() + "for: " +
                                    to_Run.getInstance().getCase_ID() +
                                    " At: [" + event_Calendar.getRuntime().toString() + "] Should be: [" + to_Run.getTo_Start().toString() + "]");

                            if (Settings.getPrint_Only_Function() && to_Run.getEPKNode() instanceof Function) {
                                //TODO Print FINISHED for n;
                            } else {
                                //TODO Print every Node FINISHED;
                            }
                        }
                    }
                    if (to_Run.getEPKNode() instanceof Event_Con_Split) {
                        to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                        List<EPK_Node> Next_Elem = ((Event_Con_Split) to_Run.getEPKNode()).choose_Next();
                        for (EPK_Node n : Next_Elem) {
                            to_Run.getInstance().add_To_Scheduled_Work(n);
                            Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                            if (n instanceof Event_Con_Join) {
                                new_Instance.setComing_From(to_Run.getEPKNode());
                            }
                            event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());
                            if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                //TODO Print Scheduled for n;
                            } else {
                                //TODO Print every Node Scheduled;
                            }
                        }

                    }
                    if (to_Run.getEPKNode() instanceof Event_Con_Join) {

                        Gate_Check_Status check = ((Event_Con_Join) to_Run.getEPKNode()).check_Previous_Elem(to_Run);
                        if (check == BLOCK) {
                            //Instanz wird gelöscht und nicht weiter behandelt
                        } else if (check == ADVANCE) {
                            List<EPK_Node> Next_Elem = to_Run.getEPKNode().getNext_Elem();
                            to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                            for (EPK_Node n : Next_Elem) {
                                to_Run.getInstance().add_To_Scheduled_Work(n);
                                Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                                if (n instanceof Event_Con_Join) {
                                    new_Instance.setComing_From(to_Run.getEPKNode());
                                }
                                event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());
                                if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                    //TODO Print Scheduled for n;
                                } else {
                                    //TODO Print every Node Scheduled;
                                }
                            }
                        } else if (check == DELAY) {
                            if (waiting_list.containsInstance(to_Run)) {
                                to_Run.getInstance().add_To_Scheduled_Work(to_Run.getEPKNode());
                                Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime().plusSeconds(1), to_Run.getEPKNode());
                                new_Instance.setWaiting_At_Gate(to_Run.getWaiting_At_Gate());
                                event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());
                            } else {
                                LocalTime to_check = event_Calendar.getNextInstanceTime(to_Run);
                                if (to_check != null) {
                                    int days = event_Calendar.getNextInstanceDay(to_Run);
                                    if (days != -1) {
                                        to_Run.getInstance().add_To_Scheduled_Work(to_Run.getEPKNode());
                                        Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), to_check.plusSeconds(1), to_Run.getEPKNode());
                                        new_Instance.setWaiting_At_Gate(to_Run.getWaiting_At_Gate());
                                        event_Calendar.Add_To_Upcoming_List(new_Instance, days);
                                    }
                                }
                                if (to_check == null) {
                                    System.out.println("Error at Gate " + to_Run.getEPKNode() + ", Instance was delayed but without Copy to wait for. DROPPED");
                                }
                            }
                        } else {
                            System.out.println("Instancecopy killed at Gate, Check Error");
                        }
                    }
                    if (to_Run.getEPKNode() instanceof Activating_Function) {
                        if (to_Run.getInstance() instanceof Activating_Event_Instance && ((Activating_Event_Instance) to_Run.getInstance()).getEnd_Function() == to_Run.getEPKNode()) {
                            int for_case_ID = ((Activating_Event_Instance) to_Run.getInstance()).getFor_case_ID();
                            List<Instance_Workflow> Activation_List = ((Activating_Function) to_Run.getEPKNode()).getWaiting_For_Activation_Instances();
                            for (Instance_Workflow workflow : Activation_List) {
                                if (workflow.getInstance().getCase_ID() == for_case_ID
                                        && workflow.getWaiting_Ticket() == to_Run.getWaiting_Ticket()) {
                                    workflow.setIs_Waiting(true);
                                    event_Calendar.Add_To_Waiting_List(workflow);
                                    System.out.println("Reactivating Instance: " + workflow.getInstance().getCase_ID());
                                    Activation_List.remove(workflow);
                                    break;
                                }
                            }
                            to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                        } else {
                            if (to_Run.getInstance() instanceof Event_Instance && !(to_Run.getInstance() instanceof Activating_Event_Instance) && to_Run.isWorking()) {
                                DeactivateFunction(to_Run, event_Calendar.getAct_runtimeDay());

                                System.out.println("Finishing Instance: " + to_Run.getInstance().getCase_ID());
                            } else if (to_Run.getInstance() instanceof Event_Instance && !(to_Run.getInstance() instanceof Activating_Event_Instance) && to_Run.Is_Waiting()) {
                                to_Run.setIs_Waiting(false);
                                ActivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                                System.out.println("Starting Function for Instance after Instantiation: " + to_Run.getInstance().getCase_ID());
                            } else if (to_Run.getInstance() instanceof Event_Instance && !(to_Run.getInstance() instanceof Activating_Event_Instance) && !to_Run.Is_Waiting()) {

                                boolean decide = ((Activating_Function) to_Run.getEPKNode()).Decide();
                                if (decide == true) {
                                    ActivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                                    System.out.println("Starting Function for Instance without Instantiation: " + to_Run.getInstance().getCase_ID());
                                } else {
                                    to_Run.setWaiting_Ticket(event_Calendar.getUnique_Waiting_Ticket_ID());
                                    ((Activating_Function) to_Run.getEPKNode()).Instantiate_Activation(to_Run);
                                    System.out.println("Pausing Function for Instance with Instantiation: " + to_Run.getInstance().getCase_ID());
                                }
                            }
                        }
                    }
                    if (to_Run.getEPKNode() instanceof Function && !(to_Run.getEPKNode() instanceof Activating_Function)) {

                        boolean firstactivation = false;
                        //FALL1: To_Run Arbeitet noch nicht an Function
                        if (!to_Run.isWorking()) {
                            ActivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                            System.out.println("Function started: " + ((Function) to_Run.getEPKNode()).getFunction_tag() + "for: " +
                                    to_Run.getInstance().getCase_ID() +
                                    " At: [" + event_Calendar.getRuntime().toString() + "] Should be: [" + to_Run.getTo_Start().toString() + "]");
                            if (event_Calendar.getRuntime().toNanoOfDay() - to_Run.getTo_Start().toNanoOfDay() != 0) {
                                System.out.println("Function is Late: " + (event_Calendar.getRuntime().toNanoOfDay() - to_Run.getTo_Start().toNanoOfDay()));
                            }
                            firstactivation = true;
                        }

                        //FALL2: To_Run hat arbeit beendet:
                        else if (firstactivation == false && to_Run.isWorking() && (to_Run.getTo_Start().equals(event_Calendar.getRuntime()) || to_Run.getTo_Start().isBefore(event_Calendar.getRuntime()))) {
                            DeactivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                            System.out.println("Function finished: " + ((Function) to_Run.getEPKNode()).getFunction_tag() + "for: " +
                                    to_Run.getInstance().getCase_ID() +
                                    " At: [" + event_Calendar.getRuntime().toString() + "] Should be: [" + to_Run.getTo_Start().toString() + "]");
                        }
                    }

                    //Actualize new Working Upcoming and Working List for Every Type of Node
                    //Get next torun, if empty, go back to while, end it, and start event_Calendar.jump()
                    upcoming_Events = event_Calendar.get_Single_Upcoming_List(event_Calendar.getAct_runtimeDay());
                    waiting_list = event_Calendar.getWaiting_List();
                    latest_Instances.addAll(upcoming_Events.getByTime(event_Calendar.getRuntime()));
                    to_Run = event_Decider.Decide_Event(latest_Instances, waiting_list);

                    if (to_Run == null) {
                        pushed_new_Elements = pushLazyGateInstances();
                        if (pushed_new_Elements) {
                            upcoming_Events = event_Calendar.get_Single_Upcoming_List(event_Calendar.getAct_runtimeDay());
                            waiting_list = event_Calendar.getWaiting_List();
                            latest_Instances.addAll(upcoming_Events.getByTime(event_Calendar.getRuntime()));
                            to_Run = event_Decider.Decide_Event(latest_Instances, waiting_list);
                            pushed_new_Elements = false;
                        }
                    }
                }
                if (to_Run == null) {
                    if (!latest_Instances.isEmpty()) {
                        for (Instance_Workflow Instance : latest_Instances) {
                            event_Calendar.Add_To_Waiting_List(Instance);
                            upcoming_Events.remove_from_EventList(Instance);
                        }
                    }
                }
                event_Calendar.jump();
            }
            event_Calendar.jump();
        }
    }

    private boolean pushLazyGateInstances() {
        boolean pushed = false;
        for (EPK_Node Gate : EPK.getElements()) {
            if (Gate instanceof Event_Con_Join) {
                if (((Event_Con_Join) Gate).getContype() == Contype.LAZY_OR || ((Event_Con_Join) Gate).getContype() == Contype.LAZY_XOR) {
                    List<Gate_Waiting_Instance> to_Remove = new ArrayList<>();
                    for (Gate_Waiting_Instance Instance : ((Event_Con_Join) Gate).getWaiting_Instance_List()) {
                        if (((Event_Con_Join) Gate).isCorrectLazyState(Instance)) {
                            Instance_Workflow next_Step = Instance.getFirst_Instance();
                            for (EPK_Node n : Gate.getNext_Elem()) {
                                next_Step.getInstance().add_To_Scheduled_Work(n);
                                Instance_Workflow new_Instance = new Instance_Workflow(next_Step.getInstance(), event_Calendar.getRuntime(), n);
                                if (n instanceof Event_Con_Join) {
                                    new_Instance.setComing_From(next_Step.getEPKNode());
                                }
                                event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());
                                pushed = true;
                            }
                            to_Remove.add(Instance);
                        }
                    }
                    if (!to_Remove.isEmpty()) {
                        ((Event_Con_Join) Gate).getWaiting_Instance_List().removeAll(to_Remove);
                    }
                }
            }
        }
        return pushed;
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
        }
        SetUsersFree.clear();
        SetResourceFree.clear();

        //Add Function as Finished and get next Elements
        List<EPK_Node> Next_Elem = to_Run.getEPKNode().getNext_Elem();
        to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());

        //TODO Print Finished to_Run.getInstance().getNode;

        //Instantiate new Workflow for each Element in GetNext_Elem
        for (EPK_Node n : Next_Elem) {
            to_Run.getInstance().add_To_Scheduled_Work(n);

            Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
            if (n instanceof Event_Con_Join) {
                new_Instance.setComing_From(to_Run.getEPKNode());
            }
            event_Calendar.Add_To_Upcoming_List(new_Instance, day);

            if (Settings.getPrint_Only_Function() && n instanceof Function) {
                //TODO Print Function Scheduled for n
            } else {
                //TODO Print Scheduled everynode for n
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

            List<Workforce> needed_workforces = new ArrayList<>(((Function) to_Run.getEPKNode()).getNeeded_Workforce());
            List<Resource> needed_Resources = new ArrayList<>(((Function) to_Run.getEPKNode()).getNeeded_Resources());

            //CHECK FOR USER WORKFORCE AVAILABLE
            for (User u : users) {
                if (!u.isActive()) {
                    List<Workforce> capable = u.getWorkforces();
                    for (Workforce cap : capable) {
                        if (needed_workforces.contains(cap)) {
                            if (!CalculateUsers.contains(u)) {
                                CalculateUsers.add(u);
                            }
                            needed_workforces.remove(cap);
                        }
                    }
                    if (needed_workforces.isEmpty()) {
                        break;
                    }
                }
            }
            if (!needed_workforces.isEmpty()) {
                System.out.println("DEBUG: Workforce not Empty but should be");
                System.out.println("Fixing: Adding Instance back to Waiting List");
                to_Run.setTo_Start(to_Run.getTo_Start().plusSeconds(1));
                event_Calendar.getWaiting_List().addTimedEvent(to_Run);
                error = true;

            }


            //CHECK RESOURCE LIMITS
            else {

                boolean not_fullfillable = false;
                for (Resource res : needed_Resources) {
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
                    to_Run.setTo_Start(to_Run.getTo_Start().plusSeconds(1));
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
            int lasting_Shifttime_in_Seconds = event_Calendar.getEnd_Time().toSecondOfDay() - event_Calendar.getRuntime().toSecondOfDay();
            int Workingtime_in_Seconds = ((Function) to_Run.getEPKNode()).getWorkingTime().get_Duration_to_Seconds();


            Duration = Duration.plusSeconds(Workingtime_in_Seconds);
            to_Run.setWorking(true);
            event_Calendar.Remove_From_Upcoming_List(to_Run, day); //fraglich
            for (User u : CalculateUsers) {
                u.setActive(true);
            }


            if (Workingtime_in_Seconds <= lasting_Shifttime_in_Seconds) {

                Instance_Workflow Running_Instance = new Instance_Workflow(to_Run.getInstance(), Duration, to_Run.getEPKNode(), to_Run.isWorking());
                Running_Instance.Add_Active_Users(CalculateUsers);
                Running_Instance.Add_Active_Resources(CalculateResource);
                event_Calendar.Add_To_Upcoming_List(Running_Instance, day);
                if (Settings.getPrint_Only_Function() && to_Run.getEPKNode() instanceof Function) {
                    //TODO Print Function Activated for n
                } else {
                    //TODO Print Activated everynode for n
                }
            } else {
                Workingtime_in_Seconds = Workingtime_in_Seconds - lasting_Shifttime_in_Seconds;
                int Shifttime_in_Seconds = event_Calendar.getEnd_Time().toSecondOfDay() - event_Calendar.getBegin_Time().toSecondOfDay();
                int advanceday = 1;
                Duration = event_Calendar.getBegin_Time();

                while (Workingtime_in_Seconds > Shifttime_in_Seconds) {
                    Workingtime_in_Seconds = Workingtime_in_Seconds - Shifttime_in_Seconds;
                    advanceday++;
                }

                if (advanceday >= event_Calendar.getRuntimeDays() - event_Calendar.getAct_runtimeDay()) {
                    System.out.println("DEBUG: Time not provided but should be");
                    System.out.println("Fixing: Adding Instance back to Waiting List");
                    to_Run.setTo_Start(to_Run.getTo_Start().plusSeconds(1));
                    event_Calendar.getWaiting_List().addTimedEvent(to_Run);
                }

                Duration.plusSeconds(Workingtime_in_Seconds);
                to_Run.setWorking(true);
                event_Calendar.Remove_From_Upcoming_List(to_Run, day);
                for (User u : CalculateUsers) {
                    u.setActive(true);
                }

                Instance_Workflow Running_Instance = new Instance_Workflow(to_Run.getInstance(), Duration, to_Run.getEPKNode());
                Running_Instance.Add_Active_Users(CalculateUsers);
                Running_Instance.Add_Active_Resources(CalculateResource);

                if (day + advanceday < Settings.getMax_RuntimeDays()) {
                    event_Calendar.Add_To_Upcoming_List(Running_Instance, day + advanceday);
                }

                if (Settings.getPrint_Only_Function() && to_Run.getEPKNode() instanceof Function) {
                    //TODO Print Function Activated for n
                } else {
                    //TODO Print Activated everynode for n
                }
            }

        }
    }
}

/*

                    if (to_Run.getEPKNode() instanceof Event_Con_Join) {

                            Gate_Check_Status check = ((Event_Con_Join) to_Run.getEPKNode()).check_Previous_Elem(to_Run);
                            if (check == BLOCK) {
                            System.out.println("Instanz mit ID: " + to_Run.getInstance().getCase_ID() + "Besaß einen ungültigen" +
                            "Teilbereich (Uhrzeit/Gate): " + to_Run.getEPKNode().toString());
                            } else if (check == ADVANCE) {
                            List<EPK_Node> Next_Elem = to_Run.getEPKNode().getNext_Elem();
        to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
        for (EPK_Node n : Next_Elem) {
        to_Run.getInstance().add_To_Scheduled_Work(n);
        Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
        event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());
        if (Settings.getPrint_Only_Function() && n instanceof Function) {
        //TODO Print Scheduled for n;
        } else {
        //TODO Print every Node Scheduled;
        }
        }
        }
        else if (check == DELAY) {
        if (waiting_list.containsInstance(to_Run)) {
        to_Run.getInstance().add_To_Scheduled_Work(to_Run.getEPKNode());
        Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime().plusSeconds(1), to_Run.getEPKNode());
        event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());
        } else {
        LocalTime to_check = event_Calendar.getNextInstanceTime(to_Run);

        if (to_check != null) {
        int days = event_Calendar.getNextInstanceDay(to_Run);
        if (days != -1) {
        to_check.plusSeconds(1);
        to_Run.getInstance().add_To_Scheduled_Work(to_Run.getEPKNode());
        Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), to_check, to_Run.getEPKNode());
        event_Calendar.Add_To_Upcoming_List(new_Instance, days);
        }
        }
        }
        } else {
        //GATE NOT FULLFILLED; WAIT
                            /*LocalTime Actualize = event_Calendar.getRuntime();
                            Actualize.plusSeconds(1);
                            Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), Actualize, to_Run.getEPKNode());
                            event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());*/
/*
        System.out.println("Clone killed Through Gate");
        }
        }
        */