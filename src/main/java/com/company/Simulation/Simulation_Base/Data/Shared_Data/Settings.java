package com.company.Simulation.Simulation_Base.Data.Shared_Data;

import com.company.Enums.Option_Event_Choosing;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Settings {

    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Option_Event_Choosing Decide_Event_choosing;
    private boolean Optimal_User_Layout;

    public Settings() {

    }

    public Option_Event_Choosing getDecide_Event() {
        return Decide_Event_choosing;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean get_Optimal_Loadout() {
        return Optimal_User_Layout;
    }

    //TODO
    //Hier Globale Settings instanzieren

}
