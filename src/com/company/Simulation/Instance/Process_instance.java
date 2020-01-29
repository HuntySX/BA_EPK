package com.company.Simulation.Instance;

import com.company.EPK.Node;
import com.company.Simulation.Data.User;
import com.company.Simulation.Data.Warehouse;
import com.company.Simulation.Queues_Gates.Warehouse_Gate;

import java.util.function.Consumer;

public class Process_instance implements Runnable {

    private Node process;
    private Thread t;
    private Thread mother;
    private boolean order_fullfilled;
    private boolean finished;
    private Warehouse_Gate warehouse;
    private Simulation_Instance instance;
    private User user;
    private Consumer<Void> consumable;

    public Process_instance(Thread mother, Thread t, Simulation_Instance instance, User user, Consumer<Void> consumable, Node process) {
        this.t = t;
        this.instance = instance;
        this.user = user;
        this.order_fullfilled = false;
        this.finished = false;
        this.consumable = consumable;
        this.mother = mother;
        this.process = process;
        this.warehouse = Warehouse_Gate.get_Warehouse_Gate();
    }

    public Thread getT() {
        return this.t;
    }

    public void setT(Thread t) {
        this.t = t;
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

    public Consumer<Void> getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumer<Void> consumable) {
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
    public void run() {

        //TODO consumable starten + instanz bearbeiten (evtl Lager).

    }
}
