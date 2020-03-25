package com.company.Simulation.Simulation_Base.Data.Shared_Data;

import com.company.Enums.Start_Event_Type;
import com.company.Enums.Option_Event_Choosing;

import java.time.LocalTime;

public class Settings {

    private boolean get_Only_Start_Finishable_Functions;
    private LocalTime beginTime;
    private LocalTime endTime;
    private Option_Event_Choosing Decide_Event_choosing;
    private boolean Optimal_User_Layout;
    private boolean Print_Only_Function;
    private Start_Event_Type startEventType;
    private int max_RuntimeDays;
    private int number_Instances_Per_Day;

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

    public boolean get_Optimal_Loadout() {
        return Optimal_User_Layout;
    }

    public boolean getPrint_Only_Function() {
        return Print_Only_Function;
    }

    public Start_Event_Type getfillingType() {
        return startEventType;
    }

    public int getMax_RuntimeDays() {
        return max_RuntimeDays;
    }

    public boolean isGet_Only_Start_Finishable_Functions() {
        return get_Only_Start_Finishable_Functions;
    }

    public int getNumber_Instances_Per_Day() {
        return number_Instances_Per_Day;
    }

    //TODO
    //Hier Globale Settings instanzieren

}
