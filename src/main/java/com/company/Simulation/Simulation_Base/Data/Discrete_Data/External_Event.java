package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

public abstract class External_Event {
    public Workingtime Time;

    public External_Event(Workingtime time) {
        Time = time;
    }

    public Workingtime getTime() {
        return Time;
    }

    public void setTime(Workingtime time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "External_Event{" +
                "Time=" + Time +
                '}';
    }
}
