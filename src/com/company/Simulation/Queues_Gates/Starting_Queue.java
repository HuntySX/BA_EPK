package com.company.Simulation.Queues_Gates;

import com.company.Enums.Status;
import com.company.Simulation.Data.Process_List;
import com.company.Simulation.Instance.Order_Instance;
import com.company.Simulation.Instance.Simulation_Instance;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class Starting_Queue implements Runnable {

    private Starting_Gate starting_gate;
    private Event_Gate event_gate;
    private Random random;
    private Timer timer;

    public Starting_Queue(Starting_Gate starting_gate, Timer timer) {
        this.event_gate = Event_Gate.get_Event_Gate();
        this.starting_gate = Starting_Gate.getStarting_gate();
        this.timer = timer;
        this.random = new Random();
    }

    public void run() {

        List<Simulation_Instance> starting_List;
        List<Simulation_Instance> order_List;

        synchronized (starting_gate) {
            starting_List = Starting_Gate.getStarting_gate().getStarting_list().getStarting_Instances();
            order_List = Starting_Gate.getStarting_gate().getStarting_order().getStarting_Instances();
        }

        if (order_List != null && !order_List.isEmpty()) {
            synchronized (order_List) {
                Iterator<Simulation_Instance> o = order_List.iterator();
                while (o.hasNext()) {
                    //TODO Iterator check
                    Simulation_Instance instance = o.next();
                    if (((Order_Instance) instance).getTime().isBefore(LocalTime.now())) {
                        synchronized (starting_gate) {
                            instance.getWorkflowMonitor().add_Workflow(starting_gate.getStarting_Event(), Status.Pending);
                            synchronized (event_gate) {
                                event_gate.getEvent_List().add_transport_Process(instance);
                            }
                        }
                        o.remove();
                    }
                }
            }
        } else {
            int instance_chooser = random.nextInt(100);

            if (instance_chooser <= 50) {
                generateBuy();
            } else {
                generateRep();
            }
            System.out.println("hi");
        }


    }

    private void generateRep() {
    }

    private void generateBuy() {
    }
}


