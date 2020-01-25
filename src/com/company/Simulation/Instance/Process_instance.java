package com.company.Simulation.Instance;

import com.company.EPK.Node;
import com.company.Simulation.Data.User;

import java.util.function.Consumer;

public class Process_instance implements Runnable {

    private Node process;
    private Thread t;
    private Thread mother;
    private Simulation_Instance instance;
    private User user;
    private Consumer<Void> consumable;

    public Process_instance(Thread mother, Thread t, Simulation_Instance instance, User user, Consumer<Void> consumable, Node process) {
        this.t = t;
        this.instance = instance;
        this.user = user;
        this.consumable = consumable;
        this.mother = mother;
        this.process = process;
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

    @Override
    public void run() {

        //TODO consumable starten + instanz bearbeiten (evtl Lager).

    }
}
