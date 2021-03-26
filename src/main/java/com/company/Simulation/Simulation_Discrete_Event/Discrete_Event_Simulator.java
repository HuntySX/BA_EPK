package com.company.Simulation.Simulation_Discrete_Event;

import com.company.EPK.*;
import com.company.Enums.Contype;
import com.company.Enums.Gate_Check_Status;
import com.company.Print.EventDriven.*;
import com.company.Run.Discrete_Event_Generator;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.*;
import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Bib.Event_Decider;
import com.company.Simulation.Simulation_Base.Data.Instance_Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Gate;
import com.company.Simulation.Simulation_Base.Data.Printer_Queue;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Settings;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.company.Enums.Gate_Check_Status.*;

public class Discrete_Event_Simulator {
    private final EPK EPK;
    private final Settings Settings;
    private final Printer_Gate printer_gate;
    private final Printer_Queue printer_queue;
    private final Instance_Printer_Gate instance_printer_gate;
    private final Event_Calendar event_Calendar;
    private final Event_Decider event_Decider;
    private final List<Resource> resources;
    private final List<User> users;
    private final int runtimeDays;

    public Discrete_Event_Simulator(Discrete_Event_Generator Generator) {

        this.EPK = Generator.getEPK();
        this.instance_printer_gate = Instance_Printer_Gate.getInstance_Printer_Gate();
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

    }

    //Main simulation Method. Checks the Goal of the instance_Workflow object and, decided by its type, handles the
    //needed simulation for this Event.
    //Runs as Long as there are Events to handle in a second, then it gives the calendar with the push() Method the order
    //to Jump to the next Time.
    //If there are no more Events to handle, the Simulator stops with the isFinishedCycle() boolean.
    //The Simulator also pushes LazyGates, as soon as there are no more Events to Handle.
    //For Functions, there are two outsourced Methods (activateFunction / deactivateFunction) wich both take/free Resources
    //and users and instantiate a new Event in the calendar.
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

                    //if Goal is an Event, Check if its an End Event. Else instantiate a new
                    // instance_Workflow for the Successor of this Node
                    if (to_Run.getEPKNode() instanceof Event) {

                        to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                        if (!(((Event) to_Run.getEPKNode()).is_End_Event())) {

                            List<EPK_Node> Next_Elem = to_Run.getEPKNode().getNext_Elem();
                            System.out.println("Event Finished: " + ((Event) to_Run.getEPKNode()).getEvent_Tag() + "for: " +
                                    to_Run.getInstance().getCase_ID() +
                                    " At: [" + event_Calendar.getRuntime().toString() + "] Should be: [" + to_Run.getTo_Start().toString() + "]");

                            if (!Settings.getPrint_Only_Function()) {
                                Instance_Print_File print_this = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Finished, Node_Type.Event, to_Run.getEPKNode().getID(), ((Event) to_Run.getEPKNode()).getEvent_Tag(), null, null);

                                synchronized (instance_printer_gate.getI_printer_Lock()) {
                                    instance_printer_gate.getInstancePrintList().add(print_this);
                                }
                            }

                            for (EPK_Node n : Next_Elem) {
                                to_Run.getInstance().add_To_Scheduled_Work(n);
                                Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
                                if (n instanceof Event_Con_Join) {
                                    new_Instance.setComing_From(to_Run.getEPKNode());
                                }
                                event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());

                                if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                    Instance_Print_File next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);

                                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                                        instance_printer_gate.getInstancePrintList().add(next_print);
                                    }
                                } else if (!Settings.getPrint_Only_Function()) {

                                    Instance_Print_File next_print = null;
                                    if (n instanceof Function && !(n instanceof Activating_Function) && !(n instanceof External_Function)) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);
                                    } else if (n instanceof Event && !(n instanceof Activating_Start_Event)) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Event, n.getID(), ((Event) n).getEvent_Tag(), null, null);
                                    } else if (n instanceof Event_Con_Split) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.E_Con_Split, n.getID(), ((Event_Con_Split) n).getContype() + "-Split-Gate" + n.getID(), null, null);
                                    } else if (n instanceof Event_Con_Join) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.E_Con_Join, n.getID(), ((Event_Con_Join) n).getContype() + "-Join-Gate" + n.getID(), null, null);
                                    } else if (n instanceof Activating_Function) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Activating_Function, n.getID(), ((Activating_Function) n).getFunction_tag(), null, null);
                                    } else if (n instanceof Activating_Start_Event) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Activating_Start_Event, n.getID(), ((Activating_Start_Event) n).getEvent_Tag(), null, null);
                                    } else if (n instanceof External_Function) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.External_Function, n.getID(), ((External_Function) n).getFunction_tag(), null, null);
                                    } else if (n instanceof External_XOR_Split) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.External_XOR_Split, n.getID(), "External-XOR-Split " + n.getID(), null, null);
                                    }
                                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                                        instance_printer_gate.getInstancePrintList().add(next_print);
                                    }
                                }
                            }
                        } else {
                            System.out.println("Instance finished at: " + ((Event) to_Run.getEPKNode()).getEvent_Tag() + "for: " +
                                    to_Run.getInstance().getCase_ID() +
                                    " At: [" + event_Calendar.getRuntime().toString() + "] Should be: [" + to_Run.getTo_Start().toString() + "]");

                            if (to_Run.getEPKNode() instanceof Function) {
                                Instance_Print_File next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Complete, Node_Type.Function, to_Run.getEPKNode().getID(),
                                        ((Function) to_Run.getEPKNode()).getFunction_tag(), null, null);

                                synchronized (instance_printer_gate.getI_printer_Lock()) {
                                    instance_printer_gate.getInstancePrintList().add(next_print);
                                }
                            } else if (!Settings.getPrint_Only_Function()) { //TODO PRINT COMPLETE
                                Instance_Print_File next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Complete, Node_Type.Event, to_Run.getEPKNode().getID(),
                                        ((Event) to_Run.getEPKNode()).getEvent_Tag(), null, null);

                                synchronized (instance_printer_gate.getI_printer_Lock()) {
                                    instance_printer_gate.getInstancePrintList().add(next_print);

                                }
                            }
                        }
                    }
                    //if Goal is an Event_Con_Split, get with choose_Next(), based on Split Type, a list of successors.
                    //instantiates a new instance_Workflow for each successor and adds them to the calendar.
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
                                Instance_Print_File next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);

                                synchronized (instance_printer_gate.getI_printer_Lock()) {
                                    instance_printer_gate.getInstancePrintList().add(next_print);
                                }
                            } else if (!Settings.getPrint_Only_Function()) {

                                Instance_Print_File next_print = null;
                                if (n instanceof Function && !(n instanceof Activating_Function)) {
                                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);
                                } else if (n instanceof Event && !(n instanceof Activating_Start_Event)) {
                                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.Event, n.getID(), ((Event) n).getEvent_Tag(), null, null);
                                } else if (n instanceof Event_Con_Split) {
                                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.E_Con_Split, n.getID(), ((Event_Con_Split) n).getContype() + "-Split-Gate" + n.getID(), null, null);
                                } else if (n instanceof Event_Con_Join) {
                                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.E_Con_Join, n.getID(), ((Event_Con_Join) n).getContype() + "-Join-Gate" + n.getID(), null, null);

                                } else if (n instanceof Activating_Function) {
                                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.Activating_Function, n.getID(), ((Activating_Function) n).getFunction_tag(), null, null);

                                } else if (n instanceof Activating_Start_Event) {
                                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.Activating_Start_Event, n.getID(), ((Activating_Start_Event) n).getEvent_Tag(), null, null);
                                }
                                synchronized (instance_printer_gate.getI_printer_Lock()) {
                                    instance_printer_gate.getInstancePrintList().add(next_print);
                                }
                            }
                        }

                    }
                    //If Goal is a Event_Con_Join, calculate the Status of the Gate for this Instance, i.e. if the Gate
                    //says the instance can move on, it generates a new instance_Workflow for the successor.
                    //if it is blocked (through wrong Requirements) the instance_Workflow is dropped. If it is Delayed
                    //it checks if this Instance is already waiting at this gate and block it, or it is putting a new
                    //Object on the Waiting List of the Gate.
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
                                    Instance_Print_File next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);

                                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                                        instance_printer_gate.getInstancePrintList().add(next_print);
                                    }
                                } else if (!Settings.getPrint_Only_Function()) {

                                    Instance_Print_File next_print = null;
                                    if (n instanceof Function && !(n instanceof Activating_Function)) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);
                                    } else if (n instanceof Event && !(n instanceof Activating_Start_Event)) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Event, n.getID(), ((Event) n).getEvent_Tag(), null, null);
                                    } else if (n instanceof Event_Con_Split) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.E_Con_Split, n.getID(), ((Event_Con_Split) n).getContype() + "-Split-Gate" + n.getID(), null, null);
                                    } else if (n instanceof Event_Con_Join) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.E_Con_Join, n.getID(), ((Event_Con_Join) n).getContype() + "-Join-Gate" + n.getID(), null, null);

                                    } else if (n instanceof Activating_Function) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Activating_Function, n.getID(), ((Activating_Function) n).getFunction_tag(), null, null);

                                    } else if (n instanceof Activating_Start_Event) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Activating_Start_Event, n.getID(), ((Activating_Start_Event) n).getEvent_Tag(), null, null);
                                    } else if (n instanceof External_Function) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.External_Function, n.getID(), ((External_Function) n).getFunction_tag(), null, null);
                                    } else if (n instanceof External_XOR_Split) {
                                        next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.External_XOR_Split, n.getID(), "External-XOR-Split " + n.getID(), null, null);
                                    }
                                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                                        instance_printer_gate.getInstancePrintList().add(next_print);
                                    }
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
                    //If the Goal is an External_XOR_Split, Calculate, based on the Runtime of the Previous Function, the
                    //successor the new instance_Workflow should take (Yes,No,Timeout).
                    if (to_Run.getEPKNode() instanceof External_XOR_Split && to_Run instanceof Instance_Workflow_XOR) {

                        EPK_Node next = ((External_XOR_Split) to_Run.getEPKNode()).getPath((Instance_Workflow_XOR) to_Run);
                        to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                        to_Run.getInstance().add_To_Scheduled_Work(next);
                        Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), next);
                        if (next instanceof Event_Con_Join) {
                            new_Instance.setComing_From(to_Run.getEPKNode());
                        }
                        event_Calendar.Add_To_Upcoming_List(new_Instance, event_Calendar.getAct_runtimeDay());

                        if (Settings.getPrint_Only_Function() && next instanceof Function) {
                            Instance_Print_File next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                    Workflow_Status.Scheduled, Node_Type.Function, next.getID(), ((Function) next).getFunction_tag(), null, null);

                            synchronized (instance_printer_gate.getI_printer_Lock()) {
                                instance_printer_gate.getInstancePrintList().add(next_print);
                            }
                        } else if (!Settings.getPrint_Only_Function()) {

                            Instance_Print_File next_print = null;
                            if (next instanceof Function && !(next instanceof Activating_Function)) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.Function, next.getID(), ((Function) next).getFunction_tag(), null, null);
                            } else if (next instanceof Event && !(next instanceof Activating_Start_Event)) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.Event, next.getID(), ((Event) next).getEvent_Tag(), null, null);
                            } else if (next instanceof Event_Con_Split) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.E_Con_Split, next.getID(), ((Event_Con_Split) next).getContype() + "-Split-Gate" + next.getID(), null, null);
                            } else if (next instanceof Event_Con_Join) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.E_Con_Join, next.getID(), ((Event_Con_Join) next).getContype() + "-Join-Gate" + next.getID(), null, null);

                            } else if (next instanceof Activating_Function) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.Activating_Function, next.getID(), ((Activating_Function) next).getFunction_tag(), null, null);

                            } else if (next instanceof Activating_Start_Event) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.Activating_Start_Event, next.getID(), ((Activating_Start_Event) next).getEvent_Tag(), null, null);
                            } else if (next instanceof External_Function) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.External_Function, next.getID(), ((External_Function) next).getFunction_tag(), null, null);
                            } else if (next instanceof External_XOR_Split) {
                                next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                        Workflow_Status.Scheduled, Node_Type.External_XOR_Split, next.getID(), "External-XOR-Split " + next.getID(), null, null);
                            }

                            synchronized (instance_printer_gate.getI_printer_Lock()) {
                                instance_printer_gate.getInstancePrintList().add(next_print);
                            }
                        }
                    }
                    //If the Goal is an Activating Function, calculate Chance for new instance on the second EPK.
                    //If it instantiates a new instance, the original one is put on a waiting_list until the arrival of
                    //the new one. Else, if its a newly generated instance that visites this goal it reactives the original
                    //instance_Workflow for the next successor. if the Chance didn´t roll for it, the original instance_Workflow
                    // just gets pushed forward.
                    if (to_Run.getEPKNode() instanceof Activating_Function) {
                        if (to_Run.getInstance() instanceof Activating_Event_Instance && ((Activating_Event_Instance) to_Run.getInstance()).getEnd_Function() == to_Run.getEPKNode()) {
                            Instance_Workflow for_Workflow = ((Activating_Event_Instance) to_Run.getInstance()).getFor_case_ID();
                            List<Instance_Workflow> Activation_List = ((Activating_Function) to_Run.getEPKNode()).getWaiting_For_Activation_Instances();
                            for (Instance_Workflow workflow : Activation_List) {
                                if (workflow.equals(for_Workflow)) {
                                    workflow.setIs_Waiting(true);
                                    event_Calendar.Add_To_Waiting_List(workflow);
                                    System.out.println("Reactivating Instance: " + workflow.getInstance().getCase_ID());
                                    Activation_List.remove(workflow);
                                    break;
                                }
                            }
                            to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                        } else {
                            if ((to_Run.getInstance() instanceof Event_Instance && !(to_Run.getInstance() instanceof Activating_Event_Instance)) && !(to_Run.getInstance() instanceof Activating_Event_Instance) && to_Run.isWorking()) {
                                DeactivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                                System.out.println("Finishing Instance: " + to_Run.getInstance().getCase_ID());
                            } else if ((to_Run.getInstance() instanceof Event_Instance && !(to_Run.getInstance() instanceof Activating_Event_Instance)) && !(to_Run.getInstance() instanceof Activating_Event_Instance) && to_Run.Is_Waiting()) {
                                to_Run.setIs_Waiting(false);
                                ActivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                                System.out.println("Starting Function for Instance after Instantiation: " + to_Run.getInstance().getCase_ID());
                            } else if ((to_Run.getInstance() instanceof Event_Instance && !(to_Run.getInstance() instanceof Activating_Event_Instance)) && !(to_Run.getInstance() instanceof Activating_Event_Instance) && !to_Run.Is_Waiting()) {

                                boolean decide = ((Activating_Function) to_Run.getEPKNode()).Decide();
                                if (!decide) {
                                    ActivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                                    System.out.println("Starting Function for Instance without Instantiation: " + to_Run.getInstance().getCase_ID());
                                } else {
                                    ((Activating_Function) to_Run.getEPKNode()).Instantiate_Activation(to_Run);
                                    System.out.println("Pausing Function for Instance with Instantiation: " + to_Run.getInstance().getCase_ID());

                                    if (to_Run.getEPKNode() instanceof Activating_Function) {
                                        Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                                                event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(), Workflow_Status.Waiting, Node_Type.Activating_Function, to_Run.getEPKNode().getID(),
                                                ((Activating_Function) to_Run.getEPKNode()).getFunction_tag(), null, null);

                                        synchronized (instance_printer_gate.getI_printer_Lock()) {
                                            instance_printer_gate.getInstancePrintList().add(activate_print);
                                        }
                                    }

                                }
                            }
                        }
                    }
                    //If the Goal is a External Function, Calculate a Runtime for the instance and save it for the Simulation_Instance
                    //on the External_XOR_Split.
                    if (to_Run.getEPKNode() instanceof External_Function) {

                        if (to_Run instanceof Instance_Workflow_XOR) {
                            to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());
                            Instance_Workflow_XOR instance = new Instance_Workflow_XOR(to_Run.getInstance(), event_Calendar.getRuntime(),
                                    ((External_Function) to_Run.getEPKNode()).getExternal_XOR(), ((Instance_Workflow_XOR) to_Run).getXOR_ID());
                            to_Run.getInstance().add_To_Scheduled_Work(((External_Function) to_Run.getEPKNode()).getExternal_XOR());
                            event_Calendar.Add_To_Upcoming_List(instance, event_Calendar.getAct_runtimeDay());
                        } else {
                            External_XOR_Instance_Lock newLock = ((External_Function) to_Run.getEPKNode()).calculate_External_Event(to_Run.getInstance());
                            LocalTime Duration = event_Calendar.getRuntime();
                            Duration = Duration.plusSeconds(newLock.getTime().get_Duration_to_Seconds());
                            Instance_Workflow_XOR instance = new Instance_Workflow_XOR(to_Run.getInstance(), Duration, to_Run.getEPKNode(), newLock.getXOR_Lock());
                            event_Calendar.Add_To_Upcoming_List(instance, event_Calendar.getAct_runtimeDay());
                            System.out.println("External Function started: " + ((External_Function) to_Run.getEPKNode()).getFunction_tag() + "for: " +
                                    to_Run.getInstance().getCase_ID() +
                                    " At: [" + event_Calendar.getRuntime().toString() + "]  Should be: [" + to_Run.getTo_Start().toString() + "]");
                            if (event_Calendar.getRuntime().toNanoOfDay() - to_Run.getTo_Start().toNanoOfDay() != 0) {
                                System.out.println("External Function is Late: " + (event_Calendar.getRuntime().toNanoOfDay() - to_Run.getTo_Start().toNanoOfDay()));
                            }
                            Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                                    event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(), Workflow_Status.Working, Node_Type.External_Function, to_Run.getEPKNode().getID(),
                                    ((External_Function) to_Run.getEPKNode()).getFunction_tag(), null, null);

                            synchronized (instance_printer_gate.getI_printer_Lock()) {
                                instance_printer_gate.getInstancePrintList().add(activate_print);
                            }
                        }

                    }
                    //If the Goal is a Basic Function, depending on the Transitiontype of the instance (isWorking()) if it
                    //has to be activated or deactivated for this function. This Code uses the Methods
                    // activateFunction() / deactivateFunction() described further down
                    if (to_Run.getEPKNode() instanceof Function && !(to_Run.getEPKNode() instanceof Activating_Function) && !(to_Run.getEPKNode() instanceof External_Function)) {

                        boolean firstactivation = false;
                        //FALL1: To_Run Arbeitet noch nicht an Function
                        if (!to_Run.isWorking()) {
                            ActivateFunction(to_Run, event_Calendar.getAct_runtimeDay());
                            System.out.println("Function started: " + ((Function) to_Run.getEPKNode()).getFunction_tag() + "for: " +
                                    to_Run.getInstance().getCase_ID() +
                                    " At: [" + event_Calendar.getRuntime().toString() + "]  Should be: [" + to_Run.getTo_Start().toString() + "]");
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
                    //Get next toRun, if empty, go back to while, end it, and start event_Calendar.jump()
                    upcoming_Events = event_Calendar.get_Single_Upcoming_List(event_Calendar.getAct_runtimeDay());
                    waiting_list = event_Calendar.getWaiting_List();
                    latest_Instances.addAll(upcoming_Events.getByTime(event_Calendar.getRuntime()));
                    to_Run = event_Decider.Decide_Event(latest_Instances, waiting_list);

                    if (to_Run == null) {
                        //pushLazyGateInstances to generate possible new Simulation_events in this Second.
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
                    //Add all unhandled instance_Workflow objects to the Waiting_list for Delayed Workflows
                    if (!latest_Instances.isEmpty()) {
                        for (Instance_Workflow Instance : latest_Instances) {
                            event_Calendar.Add_To_Waiting_List(Instance);
                            upcoming_Events.remove_from_EventList(Instance);
                        }
                    }
                }
                event_Calendar.jump();
                event_Decider.updateWithExternalEvents();
            }
            event_Calendar.jump();
            event_Decider.updateWithExternalEvents();
        }
    }


    //Pushes Lazygates. This Method is called at the end of one Timeunit. This guarantees that all instance_Workflows
    // that reach a Gate in the same second are handled in the Same gate Check.
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

                                if (Settings.getPrint_Only_Function() && n instanceof Function) {
                                    Instance_Print_File next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                            Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);

                                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                                        instance_printer_gate.getInstancePrintList().add(next_print);
                                    }
                                } else if (!Settings.getPrint_Only_Function()) {

                                    Instance_Print_File next_print = null;
                                    if (n instanceof Function && !(n instanceof Activating_Function)) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);
                                    } else if (n instanceof Event && !(n instanceof Activating_Start_Event)) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Event, n.getID(), ((Event) n).getEvent_Tag(), null, null);
                                    } else if (n instanceof Event_Con_Split) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.E_Con_Split, n.getID(), ((Event_Con_Split) n).getContype() + "-Split-Gate" + n.getID(), null, null);
                                    } else if (n instanceof Event_Con_Join) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.E_Con_Join, n.getID(), ((Event_Con_Join) n).getContype() + "-Join-Gate" + n.getID(), null, null);

                                    } else if (n instanceof Activating_Function) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Activating_Function, n.getID(), ((Activating_Function) n).getFunction_tag(), null, null);

                                    } else if (n instanceof Activating_Start_Event) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.Activating_Start_Event, n.getID(), ((Activating_Start_Event) n).getEvent_Tag(), null, null);
                                    } else if (n instanceof External_Function) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.External_Function, n.getID(), ((External_Function) n).getFunction_tag(), null, null);
                                    } else if (n instanceof External_XOR_Split) {
                                        next_print = new Instance_Print_File(next_Step.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                                                Workflow_Status.Scheduled, Node_Type.External_XOR_Split, n.getID(), "External-XOR-Split " + n.getID(), null, null);
                                    }
                                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                                        instance_printer_gate.getInstancePrintList().add(next_print);
                                    }
                                }

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

    //Gets all Users and Resources from a Used Function an frees them.
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

        List<EPK_Node> Next_Elem = to_Run.getEPKNode().getNext_Elem();
        to_Run.getInstance().add_To_Finished_Work(to_Run.getEPKNode());


        List<Print_User> Print_User_List = new ArrayList<>();
        List<Print_Resources> Print_Resource_list = new ArrayList<>();

        for (User to_print_user : SetUsersFree) {
            Print_User print_used_user = new Print_User(to_print_user.getP_ID(), to_print_user.getFirst_Name(), to_print_user.getLast_Name());
            Print_User_List.add(print_used_user);
        }
        for (Resource to_print_resource : SetResourceFree) {
            Print_Resources print_used_resource = new Print_Resources(to_print_resource.getID(), to_print_resource.getName(), to_print_resource.getCount());
            Print_Resource_list.add(print_used_resource);
        }

        if (to_Run.getEPKNode() instanceof Function &&
                !(to_Run.getEPKNode() instanceof Activating_Function)) {
            Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                    event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(), Workflow_Status.Finished, Node_Type.Function, to_Run.getEPKNode().getID(),
                    ((Function) to_Run.getEPKNode()).getFunction_tag(), Print_User_List, Print_Resource_list);

            synchronized (instance_printer_gate.getI_printer_Lock()) {
                instance_printer_gate.getInstancePrintList().add(activate_print);
            }
        } else if (to_Run.getEPKNode() instanceof Activating_Function) {
            Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                    event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(), Workflow_Status.Finished, Node_Type.Activating_Function, to_Run.getEPKNode().getID(),
                    ((Activating_Function) to_Run.getEPKNode()).getFunction_tag(), Print_User_List, Print_Resource_list);

            synchronized (instance_printer_gate.getI_printer_Lock()) {
                instance_printer_gate.getInstancePrintList().add(activate_print);
            }
        }

        SetUsersFree.clear();
        SetResourceFree.clear();

        //Instantiate new Workflow for each sucessor of the deactivated Function Node
        for (EPK_Node n : Next_Elem) {
            to_Run.getInstance().add_To_Scheduled_Work(n);

            Instance_Workflow new_Instance = new Instance_Workflow(to_Run.getInstance(), event_Calendar.getRuntime(), n);
            if (n instanceof Event_Con_Join) {
                new_Instance.setComing_From(to_Run.getEPKNode());
            }
            event_Calendar.Add_To_Upcoming_List(new_Instance, day);

            if (Settings.getPrint_Only_Function() && n instanceof Function) {
                Instance_Print_File next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                        Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);

                synchronized (instance_printer_gate.getI_printer_Lock()) {
                    instance_printer_gate.getInstancePrintList().add(next_print);
                }
            } else if (!Settings.getPrint_Only_Function()) {

                Instance_Print_File next_print = null;
                if (n instanceof Function && !(n instanceof Activating_Function)) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.Function, n.getID(), ((Function) n).getFunction_tag(), null, null);
                } else if (n instanceof Event && !(n instanceof Activating_Start_Event)) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.Event, n.getID(), ((Event) n).getEvent_Tag(), null, null);
                } else if (n instanceof Event_Con_Split) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.E_Con_Split, n.getID(), ((Event_Con_Split) n).getContype() + "-Split-Gate" + n.getID(), null, null);
                } else if (n instanceof Event_Con_Join) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.E_Con_Join, n.getID(), ((Event_Con_Join) n).getContype() + "-Join-Gate" + n.getID(), null, null);

                } else if (n instanceof Activating_Function) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.Activating_Function, n.getID(), ((Activating_Function) n).getFunction_tag(), null, null);

                } else if (n instanceof Activating_Start_Event) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.Activating_Start_Event, n.getID(), ((Activating_Start_Event) n).getEvent_Tag(), null, null);
                } else if (n instanceof External_Function) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.External_Function, n.getID(), ((External_Function) n).getFunction_tag(), null, null);
                } else if (n instanceof External_XOR_Split) {
                    next_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(), event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(),
                            Workflow_Status.Scheduled, Node_Type.External_XOR_Split, n.getID(), "External-XOR-Split " + n.getID(), null, null);
                }

                synchronized (instance_printer_gate.getI_printer_Lock()) {
                    instance_printer_gate.getInstancePrintList().add(next_print);
                }
            }
        }
    }


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    //Like deactivatingFunction(), but here it reserves Resources and Users. A new instance_Workflow object is called
    //with the same Function as a Goal but with isWorking() = true.
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
                if (!u.isActive() && !u.isDisabled()) {
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
        //get the Duration of the Simulation and check if it is simulateable within the same day or if it needs to be
        //started at the next day (optionally start it irrelevant of the remaining Daytime if the simulation
        // is setup this way
        if (!error) {
            LocalTime Duration = event_Calendar.getRuntime();
            int lasting_Shifttime_in_Seconds = event_Calendar.getEnd_Time().toSecondOfDay() - event_Calendar.getRuntime().toSecondOfDay();
            int Workingtime_in_Seconds = ((Function) to_Run.getEPKNode()).getWorkingTime().get_Duration_to_Seconds();
            System.out.println("Duration for Next Instance: " + new Workingtime(Workingtime_in_Seconds).toString());
            Duration = Duration.plusSeconds(Workingtime_in_Seconds);
            to_Run.setWorking(true);
            event_Calendar.Remove_From_Upcoming_List(to_Run, day); //fraglich
            for (User u : CalculateUsers) {
                u.setActive(true);
            }


            if (Workingtime_in_Seconds <= lasting_Shifttime_in_Seconds) {
                // add new instance Workflow to Upcoming List, reserve all used Users and Resources
                Instance_Workflow Running_Instance = new Instance_Workflow(to_Run.getInstance(), Duration, to_Run.getEPKNode(), to_Run.isWorking());
                Running_Instance.Add_Active_Users(CalculateUsers);
                Running_Instance.Add_Active_Resources(CalculateResource);
                event_Calendar.Add_To_Upcoming_List(Running_Instance, day);

                List<Print_User> Print_User_List = new ArrayList<>();
                List<Print_Resources> Print_Resource_list = new ArrayList<>();

                for (User to_print_user : CalculateUsers) {
                    Print_User print_used_user = new Print_User(to_print_user.getP_ID(), to_print_user.getFirst_Name(), to_print_user.getLast_Name());
                    Print_User_List.add(print_used_user);
                }
                for (Resource to_print_resource : CalculateResource) {
                    Print_Resources print_used_resource = new Print_Resources(to_print_resource.getID(), to_print_resource.getName(), to_print_resource.getCount());
                    Print_Resource_list.add(print_used_resource);
                }

                if (to_Run.getEPKNode() instanceof Function &&
                        !(to_Run.getEPKNode() instanceof Activating_Function)) {
                    Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                            event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(), Workflow_Status.Working, Node_Type.Function, to_Run.getEPKNode().getID(),
                            ((Function) to_Run.getEPKNode()).getFunction_tag(), Print_User_List, Print_Resource_list);

                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                        instance_printer_gate.getInstancePrintList().add(activate_print);
                    }
                } else if (to_Run.getEPKNode() instanceof Activating_Function) {
                    Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                            event_Calendar.getRuntime(), event_Calendar.getAct_runtimeDay(), Workflow_Status.Working, Node_Type.Activating_Function, to_Run.getEPKNode().getID(),
                            ((Activating_Function) to_Run.getEPKNode()).getFunction_tag(), Print_User_List, Print_Resource_list);

                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                        instance_printer_gate.getInstancePrintList().add(activate_print);
                    }
                }
            } else {
                //Calculate each Day that needs to pass until this simulation is finished, then add the instance_Workflow
                //into that Upcoming list.
                Workingtime_in_Seconds = Workingtime_in_Seconds - lasting_Shifttime_in_Seconds;
                int Shifttime_in_Seconds = event_Calendar.getEnd_Time().toSecondOfDay() - event_Calendar.getBegin_Time().toSecondOfDay();
                int advanceday = 1;
                Duration = event_Calendar.getBegin_Time();

                while (Workingtime_in_Seconds > Shifttime_in_Seconds) {
                    Workingtime_in_Seconds = Workingtime_in_Seconds - Shifttime_in_Seconds;
                    advanceday++;
                }


                //TODO Nächster Code Fragment fragt nach ob die Funktion überhaupt beendet werden kann.
                // Zum fixen wird dieser dann aber eine sekunde später hinzugefügt. da sollte es eigentlich auch nichtmehr lösbar sein.
                // trotzdem startet er es im ersten Fall nicht, (Verspätung -1s) im zweiten dafür schon.
                // Jenachdem welches Setting gewählt wurde (starten oder nicht) sollte die Instanz gedroppt werden (oder nicht).
                if (advanceday >= event_Calendar.getRuntimeDays() - event_Calendar.getAct_runtimeDay()) {
                    System.out.println("DEBUG: Time not provided but should be");
                    System.out.println("Fixing: Adding Instance back to Waiting List");
                    to_Run.setTo_Start(to_Run.getTo_Start().plusSeconds(1));
                    event_Calendar.getWaiting_List().addTimedEvent(to_Run);
                }

                Duration = Duration.plusSeconds(Workingtime_in_Seconds);
                to_Run.setWorking(true);
                event_Calendar.Remove_From_Upcoming_List(to_Run, day);
                for (User u : CalculateUsers) {
                    u.setActive(true);
                }

                Instance_Workflow Running_Instance = new Instance_Workflow(to_Run.getInstance(), Duration, to_Run.getEPKNode(), to_Run.isWorking());
                Running_Instance.Add_Active_Users(CalculateUsers);
                Running_Instance.Add_Active_Resources(CalculateResource);

                if (day + advanceday < Settings.getMax_RuntimeDays()) {
                    event_Calendar.Add_To_Upcoming_List(Running_Instance, day + advanceday);
                }

                List<Print_User> Print_User_List = new ArrayList<>();
                List<Print_Resources> Print_Resource_list = new ArrayList<>();
                for (User to_print_user : CalculateUsers) {
                    Print_User print_used_user = new Print_User(to_print_user.getP_ID(), to_print_user.getFirst_Name(), to_print_user.getLast_Name());
                    Print_User_List.add(print_used_user);
                }
                for (Resource to_print_resource : CalculateResource) {
                    Print_Resources print_used_resource = new Print_Resources(to_print_resource.getID(), to_print_resource.getName(), to_print_resource.getCount());
                    Print_Resource_list.add(print_used_resource);
                }

                if (to_Run.getEPKNode() instanceof Function &&
                        !(to_Run.getEPKNode() instanceof Activating_Function)) {
                    Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                            event_Calendar.getRuntime(), day + advanceday, Workflow_Status.Working, Node_Type.Function, to_Run.getEPKNode().getID(),
                            ((Function) to_Run.getEPKNode()).getFunction_tag(), Print_User_List, Print_Resource_list);

                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                        instance_printer_gate.getInstancePrintList().add(activate_print);
                    }
                } else if (to_Run.getEPKNode() instanceof Activating_Function) {
                    Instance_Print_File activate_print = new Instance_Print_File(to_Run.getInstance().getCase_ID(),
                            event_Calendar.getRuntime(), day + advanceday, Workflow_Status.Working, Node_Type.Activating_Function, to_Run.getEPKNode().getID(),
                            ((Activating_Function) to_Run.getEPKNode()).getFunction_tag(), Print_User_List, Print_Resource_list);

                    synchronized (instance_printer_gate.getI_printer_Lock()) {
                        instance_printer_gate.getInstancePrintList().add(activate_print);
                    }
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
        // Print Scheduled for n;
        } else {
        // Print every Node Scheduled;
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