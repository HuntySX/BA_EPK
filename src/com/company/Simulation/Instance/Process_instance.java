package com.company.Simulation.Instance;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.Function_Type;
import com.company.Enums.Process_Status;
import com.company.Simulation.Data.User;
import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Queues_Gates.Printer_Gate;
import com.company.Simulation.Queues_Gates.Warehouse_Gate;

import java.time.LocalTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Process_instance implements Runnable {

    private Node process;
    private Function_Type type;
    private Thread t;
    private Lock mother;
    private boolean order_fullfilled;
    private boolean finished;
    private boolean concurrency;
    private Warehouse_Gate warehouse;
    private Simulation_Instance instance;
    private User user;
    private Consumer<Process_instance> consumable;
    private Lock lock;

    public Process_instance(Lock mother, Function_Type type, Simulation_Instance instance, User user, Consumer<Process_instance> consumable, boolean concurrency, Node process) {
        this.t = null;
        this.instance = instance;
        this.type = type;
        this.user = user;
        this.order_fullfilled = false;
        this.finished = false;
        this.consumable = consumable;
        this.mother = mother;
        this.process = process;
        this.concurrency = concurrency;
        this.warehouse = Warehouse_Gate.get_Warehouse_Gate();
        lock = new ReentrantLock(true);
    }

    public Thread getT() {
        return this.t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public Lock getLock() {
        return lock;
    }

    public Simulation_Instance getInstance() {
        return instance;
    }

    public void setInstance(Simulation_Instance instance) {
        this.instance = instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Consumer<Process_instance> getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumer<Process_instance> consumable) {
        this.consumable = consumable;
    }

    public Node getProcess() {
        return process;
    }

    public boolean isOrder_fullfilled() {
        return order_fullfilled;
    }

    public void setOrder_fullfilled(boolean order_fullfilled) {
        this.order_fullfilled = order_fullfilled;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public synchronized void run() {
        synchronized (Printer_Gate.get_Printer_Gate()) {
            LocalTime time = LocalTime.now();
            Printer_Gate.get_Printer_Gate().PrintActivate(instance.getCase_ID(), (Function) process, time, Process_Status.Active, user.getFirst_Name(), user.getLast_Name());
        }
        if (type == Function_Type.Waiting) {
            consumable.accept(this);
        } else if (type == Function_Type.Buying) {
            consumable.accept(this);
            user.setActive(false);
            synchronized (this.getLock()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (Printer_Gate.get_Printer_Gate()) {
                LocalTime time = LocalTime.now();
                Printer_Gate.get_Printer_Gate().PrintStorage(instance.getCase_ID(), (Function) process, time, Process_Status.Active, ((Buy_Instance) instance).getBuy_Items());
            }

            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            consumable.accept(this);
        }

        synchronized (Printer_Gate.get_Printer_Gate()) {
            LocalTime time = LocalTime.now();
            Printer_Gate.get_Printer_Gate().PrintActivate(instance.getCase_ID(), (Function) process, time, Process_Status.Finished, user.getFirst_Name(), user.getLast_Name());
        }
        synchronized (this.getUser()) {
            user.setActive(false);
        }
        finished = true;
        // mother.notify();

    }
}
