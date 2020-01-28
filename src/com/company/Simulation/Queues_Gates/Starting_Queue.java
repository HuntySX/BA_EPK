package com.company.Simulation.Queues_Gates;

import com.company.Simulation.Data.Item;
import com.company.Simulation.Instance.Buy_Instance;
import com.company.Simulation.Instance.Order_Instance;
import com.company.Simulation.Instance.Rep_Instance;
import com.company.Simulation.Instance.Simulation_Instance;
import com.company.Simulation.Simulator;

import java.time.LocalTime;
import java.util.*;

import static com.company.Enums.Process_Status.Pending;

public class Starting_Queue implements Runnable {

    private Starting_Gate starting_gate;
    private Event_Gate event_gate;
    private Random random;
    private Timer timer;
    private Simulator simulator;

    public Starting_Queue(Starting_Gate starting_gate, Timer timer) {
        this.event_gate = Event_Gate.get_Event_Gate();
        this.starting_gate = Starting_Gate.getStarting_gate();
        this.timer = timer;
        this.random = new Random();
        this.simulator = Simulator.get_Simulator();
    }

    public void run() {
        List<Simulation_Instance> order_List;
        synchronized (starting_gate) {
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
                            instance.getWorkflowMonitor().add_Workflow(starting_gate.getStarting_Event(), Pending);
                            synchronized (event_gate) {
                                event_gate.getEvent_List().add_transport_Process(instance);
                            }
                        }
                        o.remove();
                    }
                }
            }
        }

        //TODO Timing vom Instance chooser.
        else {
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

        int iD;
        Item rep_Item;

        synchronized (simulator) {
            iD = simulator.get_unique_caseID();
            rep_Item = simulator.generate_singleRandomItem();
        }
        Rep_Instance rep = new Rep_Instance(iD, rep_Item);
        synchronized (starting_gate) {
            rep.getWorkflowMonitor().add_Workflow(starting_gate.getStarting_Event(), Pending);
        }
        synchronized (event_gate) {
            event_gate.getEvent_List().add_transport_Process(rep);
        }
    }

    private void generateBuy() {

        int iD;
        List<Item> buy_Items;

        synchronized (simulator) {
            iD = simulator.get_unique_caseID();
            buy_Items = simulator.generate_severalRandomItems();
        }
        Buy_Instance buy = new Buy_Instance(iD, buy_Items);
        synchronized (starting_gate) {
            buy.getWorkflowMonitor().add_Workflow(starting_gate.getStarting_Event(), Pending);
        }
        synchronized (event_gate) {
            event_gate.getEvent_List().add_transport_Process(buy);
        }

    }
}


