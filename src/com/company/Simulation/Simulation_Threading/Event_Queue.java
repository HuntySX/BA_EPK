package com.company.Simulation.Simulation_Threading;

import com.company.EPK.*;
import com.company.Enums.Process_Status;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Workflow_Monitor;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;


import java.time.LocalTime;
import java.util.List;

import static com.company.Enums.Process_Status.*;

public class Event_Queue implements Runnable {

    private Event_Gate event_gate;
    private Process_Gate process_gate;
    private Thread EQ;
    private boolean not_killed;

    public Event_Queue() {
        event_gate = Event_Gate.get_Event_Gate();
        process_gate = Process_Gate.getProcess_gate();
        this.EQ = null;
        not_killed = true;
    }

    public void setEQ(Thread EQ) {
        this.EQ = EQ;
    }

    public void to_append(List<Node> Nodes, Simulation_Instance transport_event) {

        for (Node a : Nodes) {
            if (a instanceof Function) {
                transport_event.getWorkflowMonitor().add_Workflow(a, Scheduled);
                synchronized (Process_Gate.getProcess_gate()) {
                    Process_Gate.getProcess_gate().getProcess_list().Schedule_Process(transport_event);
                    LocalTime time = LocalTime.now();
                    Printer_Gate.get_Printer_Gate().PrintProcess(transport_event.getCase_ID(), (Function) a, time, Scheduled);
                }
            } else {
                transport_event.getWorkflowMonitor().add_Workflow(a, Pending);
                synchronized (Event_Gate.get_Event_Gate().getEvent_Lock()) {
                    Event_Gate.get_Event_Gate().getEvent_List().add_transport_Process(transport_event);
                }
            }
        }

    }

    public boolean isNot_killed() {
        return not_killed;
    }

    public void setNot_killed(boolean not_killed) {
        this.not_killed = not_killed;
    }

    public synchronized void run() {

        while (not_killed) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Simulation_Instance transport_event = null;
            synchronized (Event_Gate.get_Event_Gate().getEvent_Lock()) {
                List<Simulation_Instance> transport_list = Event_Gate.get_Event_Gate().getEvent_List().getTransport_List();
                if (!transport_list.isEmpty()) {
                    synchronized (transport_list.get(0).getInstance_lock()) {
                        transport_event = transport_list.get(0);
                        transport_list.remove(transport_event);
                    }
                }
            }

            if (transport_event != null) {
                boolean add_to_list_again = false;
                synchronized (transport_event.getInstance_lock()) {
                    {
                        Workflow_Monitor Workflow = transport_event.getWorkflowMonitor();
                        List<Node> Elements = Workflow.get_Elements();
                        for (Node n : Elements) {
                            Process_Status processStatus = Workflow.getProcess_Status().get(Workflow.get_Elements().indexOf(n));

                            if (processStatus == Pending) {

                                if (n instanceof Con_Split) {
                                    Workflow.getProcess_Status().set(Workflow.get_Elements().indexOf(n), Finished);
                                    List<Node> to_append = ((Con_Split) n).choose_Next(transport_event);
                                    to_append(to_append, transport_event);
                                    break;
                                } else if (n instanceof Con_Join) {
                                    if (((Con_Join) n).check_Previous_Elem(transport_event)) {
                                        Workflow.getProcess_Status().set(Workflow.get_Elements().indexOf(n), Finished);
                                        List<Node> to_append = (n.getNext_Elem());
                                        to_append(to_append, transport_event);
                                        break;
                                    }
                                } else {
                                    Workflow.getProcess_Status().set(Workflow.get_Elements().indexOf(n), Finished);
                                    List<Node> to_append = (n.getNext_Elem());
                                    to_append(to_append, transport_event);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
