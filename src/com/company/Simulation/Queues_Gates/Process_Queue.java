package com.company.Simulation.Queues_Gates;

import com.company.EPK.EPK;
import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Simulation.Data.User;
import com.company.Simulation.Instance.Buy_Instance;
import com.company.Simulation.Instance.Order_Instance;
import com.company.Simulation.Instance.Process_instance;
import com.company.Simulation.Instance.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.function.Consumer;

import static com.company.Enums.Process_Status.*;

public class Process_Queue implements Runnable {

    private final Process_Gate process_gate;
    private final User_Gate user_gate;
    Thread Process_Queue;
    private Timer timer;
    private EPK epk;
    private List<Process_instance> active_Processes;

    public Process_Queue(EPK epk, Thread process_Queue) {
        process_gate = Process_Gate.getProcess_gate();
        user_gate = User_Gate.get_User_Gate();
        this.epk = epk;
        this.timer = new Timer();
        Thread Process_Queue = process_Queue;
        active_Processes = new ArrayList<>();

    }

    //TODO Event Listener Wenn Bestellung eingeht. Welche Reihenfolge? Siehe Notepad ++ Bzgl Fifo


    public void run() {
        try {
            Process_Queue.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = null;
        List<Process_instance> to_delete = new ArrayList<>();
        synchronized (active_Processes) {
            if (!active_Processes.isEmpty()) {
                for (Process_instance p : active_Processes) {
                    if (!p.getT().isAlive() && p.isFinished()) {
                        try {
                            p.getT().join();
                            p.getInstance().getWorkflowMonitor().getProcess_Status().set
                                    (p.getInstance().getWorkflowMonitor().get_Elements().indexOf(p.getProcess()), Pending);
                            Event_Gate.get_Event_Gate().getEvent_List().add_transport_Process(p.getInstance());
                            p.getUser().setActive(false);
                            to_delete.add(p);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (p.getInstance() instanceof Buy_Instance
                            && p.getT().getState() == Thread.State.WAITING
                            && p.getInstance().getWorkflowMonitor().getProcess_Status().get(p.getInstance().getWorkflowMonitor().get_Elements().indexOf(p.getProcess())) == Active
                            && ((Buy_Instance) p.getInstance()).isFullfilled()) {

                        synchronized (user_gate) {
                            List<User> users = User_Gate.get_User_Gate().getUser_List();

                            for (User u : users) {
                                if (!u.isActive()) {
                                    if (user.getAllowed_Processes().contains(p.getProcess())) {
                                        user.setActive(true);
                                        p.getT().notify();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        synchronized (user_gate) {
            while (User_Gate.get_User_Gate().check_inactivity()) {
                List<User> users = User_Gate.get_User_Gate().getUser_List();
                for (User u : users) {
                    if (!u.isActive()) {
                        user = u;
                        break;
                    }
                }

                Simulation_Instance instance = null;
                synchronized (process_gate) {
                    if (!process_gate.getProcess_list().getWorking_List().isEmpty()) {
                        instance = process_gate.getProcess_list().getWorking_List().get(0);
                        process_gate.getProcess_list().getWorking_List().remove(0);
                    }
                }

                if (instance != null) {
                    Consumer<Void> consumable = null;
                    boolean concurrency = false;
                    Node process = null;
                    synchronized (instance) {
                        for (Node n : instance.getWorkflowMonitor().get_Elements()) {
                            if (n instanceof Function &&
                                    instance.getWorkflowMonitor().getProcess_Status().get(instance.getWorkflowMonitor().get_Elements().indexOf(n)) == Scheduled &&
                                    user.getAllowed_Processes().contains(n)) {
                                consumable = ((Function) n).getConsumableMethod();
                                concurrency = ((Function) n).isConcurrently();
                                process = n;
                                break;
                            }
                        }
                    }

                    if (consumable != null && process != null) {
                        Thread process_thread = new Thread();
                        Process_instance process_instance = new Process_instance(Process_Queue, process_thread, instance, user, consumable, process);

                        synchronized (instance) {
                            instance.getWorkflowMonitor().getProcess_Status().set(instance.getWorkflowMonitor().get_Elements().indexOf(instance), Active);
                        }
                        synchronized (user) {
                            user.setActive(true);
                        }

                        synchronized (process_instance) {
                            active_Processes.add(process_instance);
                            process_instance.getT().start();
                        }

                        synchronized (instance) {
                            for (Node n : instance.getWorkflowMonitor().get_Elements()) {
                                if (instance.getWorkflowMonitor().getProcess_Status().get(instance.getWorkflowMonitor().get_Elements().indexOf(n)) == Scheduled) {
                                    synchronized (process_gate) {
                                        process_gate.getProcess_list().Schedule_Process(instance);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!to_delete.isEmpty()) {
            active_Processes.removeAll(to_delete);
        }
    }
}
