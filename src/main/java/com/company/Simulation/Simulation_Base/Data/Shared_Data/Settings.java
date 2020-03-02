package com.company.Simulation.Simulation_Base.Data.Shared_Data;

import java.time.LocalTime;

public class Settings {

    private LocalTime beginTime;
    private LocalTime endTime;

    public Settings() {

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
