package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.time.LocalTime;

public class Event_Instance extends Simulation_Instance {

    private LocalTime next_Time;

    public Event_Instance(int case_ID, LocalTime Time) {
        super(case_ID);
        this.next_Time = Time;
    }

    public LocalTime getTime() {
        return next_Time;
    }

    public void setTime(LocalTime time) {
        next_Time = time;
    }
}
