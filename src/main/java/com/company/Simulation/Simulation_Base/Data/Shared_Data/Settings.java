package com.company.Simulation.Simulation_Base.Data.Shared_Data;

import com.company.Enums.Option_Event_Choosing;

import java.time.LocalTime;

public class Settings {

    private LocalTime beginTime;
    private LocalTime endTime;
    private Option_Event_Choosing Decide_Event_choosing;

    public Settings() {

    }

    public Option_Event_Choosing getDecide_Event() {
        return Decide_Event_choosing;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    //TODO
    //Hier Globale Settings instanzieren

}
