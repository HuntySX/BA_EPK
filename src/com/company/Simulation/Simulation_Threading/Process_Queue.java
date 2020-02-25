package com.company.Simulation.Simulation_Threading;

import com.company.EPK.EPK;
import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Simulation.Simulation_Base.Data.User;
import com.company.Simulation.Simulation_Base.Threading_Instance.Buy_Instance;
import com.company.Simulation.Simulation_Base.Threading_Instance.Process_instance;
import com.company.Simulation.Simulation_Base.Threading_Instance.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import static com.company.Enums.Process_Status.*;

public class Process_Queue implements Runnable {

    private final Process_Gate process_gate;
    private final User_Gate user_gate;
    private Thread Process_Queue;
    private EPK epk;
    private List<Process_instance> active_Processes;
    private boolean not_killed = true;
    private Lock lock;

    public Process_Queue(EPK epk) {
        process_gate = Process_Gate.getProcess_gate();
        user_gate = User_Gate.get_User_Gate();
        this.epk = epk;
        this.Process_Queue = null;
        active_Processes = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void setProcess_Queue(Thread process_Queue) {
        Process_Queue = process_Queue;
    }

    public void setNot_killed(boolean not_killed) {
        this.not_killed = not_killed;
    }

    //TODO Event Listener Wenn Bestellung eingeht. Welche Reihenfolge? Siehe Notepad ++ Bzgl Fifo

    public synchronized void run() {
        while (not_killed) {
            synchronized (lock) {
                try {
                    lock.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            User user = null;
            List<Process_instance> to_delete = new ArrayList<>();
            synchronized (this) {
                if (!active_Processes.isEmpty()) {
                    for (Process_instance p : active_Processes) {
                        if (p.isFinished()) {
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
                        } else if (p.getInstance() instanceof Buy_Instance
                                && p.getT().getState() == Thread.State.WAITING
                                && p.getInstance().getWorkflowMonitor().getProcess_Status().get(p.getInstance().getWorkflowMonitor().get_Elements().indexOf(p.getProcess())) == Active
                                && ((Buy_Instance) p.getInstance()).isFullfilled()) {

                            synchronized (User_Gate.get_User_Gate()) {
                                List<User> users = User_Gate.get_User_Gate().getUser_List();

                                for (User u : users) {
                                    if (!u.isActive() && u.getAllowed_Processes().contains(p.getProcess())) {
                                        u.setActive(true);
                                        p.setUser(u);
                                        synchronized (p.getLock()) {
                                            p.getLock().notify();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }


            synchronized (User_Gate.get_User_Gate()) {
                if (User_Gate.get_User_Gate().check_inactivity()) {
                    List<User> users = User_Gate.get_User_Gate().getUser_List();
                    for (User u : users) {
                        if (!u.isActive()) {
                            user = u;
                        }
                        if (user != null) {
                            Simulation_Instance instance = null;
                            synchronized (Process_Gate.getProcess_gate()) {
                                if (!Process_Gate.getProcess_gate().getProcess_list().getWorking_List().isEmpty()) {
                                    instance = Process_Gate.getProcess_gate().getProcess_list().getWorking_List().get(0);
                                }
                                //}

                                if (instance != null) {
                                    Consumer<Process_instance> consumable = null;
                                    boolean concurrency = false;
                                    Node process = null;
                                    // synchronized (instance) {
                                    for (Node n : instance.getWorkflowMonitor().get_Elements()) {
                                        if (n instanceof Function &&
                                                instance.getWorkflowMonitor().getProcess_Status().get(instance.getWorkflowMonitor().get_Elements().indexOf(n)) == Scheduled &&
                                                user.getAllowed_Processes().contains(n)) {
                                            Process_Gate.getProcess_gate().getProcess_list().getWorking_List().remove(0);
                                            consumable = ((Function) n).getConsumableMethod();
                                            concurrency = ((Function) n).isConcurrently();
                                            process = n;
                                            break;
                                        }
                                    }
                                    //}

                                    if (consumable != null) {
                                        Process_instance process_instance = new Process_instance(lock, ((Function) process).getFunction_type(), instance, user, consumable, concurrency, process);
                                        Thread process_thread = new Thread(process_instance);
                                        process_instance.setT(process_thread);

                                        //synchronized (instance) {
                                        instance.getWorkflowMonitor().getProcess_Status().set(instance.getWorkflowMonitor().get_Elements().indexOf(process), Active);
                                        // }
                                        //synchronized (user) {
                                        user.setActive(true);
                                        //}

                                        //synchronized (process_instance) {
                                        active_Processes.add(process_instance);
                                        process_instance.getT().start();
                                        //}

                                        //synchronized (instance) {
                                        for (Node n : instance.getWorkflowMonitor().get_Elements()) {
                                            if (instance.getWorkflowMonitor().getProcess_Status().get(instance.getWorkflowMonitor().get_Elements().indexOf(n)) == Scheduled) {
                                                //synchronized (process_gate) {
                                                Process_Gate.getProcess_gate().getProcess_list().Schedule_Process(instance);
                                            }
                                            break;
                                        }
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
}

