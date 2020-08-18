package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

public abstract class External_Event {
    public Workingtime Time;
    public int day;
    public int EEV_ID;

    public External_Event(Workingtime time, int day, int ID) {
        Time = time;
        this.day = day;
        EEV_ID = ID;
    }

    public Workingtime getTime() {
        return Time;
    }

    public void setTime(Workingtime time) {
        Time = time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return Time +
                "//Day: " + day;
    }
}
