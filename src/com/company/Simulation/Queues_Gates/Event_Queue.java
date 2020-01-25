package com.company.Simulation.Queues_Gates;

import com.company.EPK.*;
import com.company.Enums.Status;
import com.company.Simulation.Data.Workflow_Monitor;
import com.company.Simulation.Instance.Simulation_Instance;

import java.util.List;
import java.util.Timer;

import static com.company.Enums.Status.*;

public class Event_Queue implements Runnable {

    private Event_Gate event_gate;
    private Process_Gate process_gate;
    private Timer timer;

    public Event_Queue() {
        event_gate = Event_Gate.get_Event_Gate();
        process_gate = Process_Gate.getProcess_gate();
        timer = new Timer();
    }

    public void to_append(List<Node> Nodes, Simulation_Instance transport_event) {

        for (Node a : Nodes) {
            if (a instanceof Function) {
                transport_event.getWorkflowMonitor().add_Workflow(a, Scheduled);
                synchronized (process_gate.getLock()) {
                    process_gate.getProcess_list().getWorking_List().add(transport_event);
                    //TODO call Printer
                }
            } else {
                transport_event.getWorkflowMonitor().add_Workflow(a, Pending);
                synchronized (event_gate.getEvent_Lock()) {
                    Event_Gate.get_Event_Gate().getEvent_List().add_transport_Process(transport_event);
                }
            }
        }

    }

    public void run() {

        //TODO Instantiate Timed Thread
        try {
            timer.wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Simulation_Instance transport_event = null;
        synchronized (event_gate.getEvent_Lock()) {
            List<Simulation_Instance> transport_list = event_gate.getEvent_List().getTransport_List();
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

                    for (Node n : Workflow.get_Elements()) {
                        Status status = Workflow.getStatus().get(Workflow.get_Elements().indexOf(n));

                        if (status == Pending) {

                            if (n instanceof Con_Split) {
                                Workflow.getStatus().set(Workflow.get_Elements().indexOf(n), Finished);
                                List<Node> to_append = ((Con_Split) n).choose_Next(transport_event);
                                to_append(to_append, transport_event);
                            } else if (n instanceof Con_Join) {
                                if (((Con_Join) n).check_Previous_Elem(transport_event)) {
                                    Workflow.getStatus().set(Workflow.get_Elements().indexOf(n), Finished);
                                    List<Node> to_append = (((Con_Join) n).getNext_Elem());
                                    to_append(to_append, transport_event);
                                }
                            } else {
                                Workflow.getStatus().set(Workflow.get_Elements().indexOf(n), Finished);
                                List<Node> to_append = (n.getNext_Elem());
                                to_append(to_append, transport_event);
                            }
                        }
                    }
                }
            }
        }
    }
}
