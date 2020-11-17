package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

public class External_XOR_Instance_Lock {
    private Event_Instance Instance;
    private Workingtime time;
    private Object XOR_Lock;

    public External_XOR_Instance_Lock(Event_Instance instance, Workingtime time) {
        Instance = instance;
        this.time = time;
        this.XOR_Lock = new Object();
    }

    public Workingtime getTime() {
        return time;
    }

    public void setTime(Workingtime time) {
        this.time = time;
    }

    public Event_Instance getInstance() {
        return Instance;
    }

    public void setInstance(Event_Instance instance) {
        Instance = instance;
    }

    public Object getXOR_Lock() {
        return XOR_Lock;
    }

    public void setXOR_Lock(Object XOR_Lock) {
        this.XOR_Lock = XOR_Lock;
    }
}
