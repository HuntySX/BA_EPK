package com.company.Simulation.Simulation_Base.Data.Shared_Data;

import com.company.Enums.Option_Event_Choosing;
import com.company.Enums.Start_Event_Type;

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

    public void setGet_Only_Start_Finishable_Functions(boolean get_Only_Start_Finishable_Functions) {
        this.get_Only_Start_Finishable_Functions = get_Only_Start_Finishable_Functions;
    }

    public Option_Event_Choosing getDecide_Event_choosing() {
        return Decide_Event_choosing;
    }

    public void setDecide_Event_choosing(Option_Event_Choosing decide_Event_choosing) {
        Decide_Event_choosing = decide_Event_choosing;
    }

    public boolean isOptimal_User_Layout() {
        return Optimal_User_Layout;
    }

    public void setOptimal_User_Layout(boolean optimal_User_Layout) {
        Optimal_User_Layout = optimal_User_Layout;
    }

    public boolean isPrint_Only_Function() {
        return Print_Only_Function;
    }

    public void setPrint_Only_Function(boolean print_Only_Function) {
        Print_Only_Function = print_Only_Function;
    }

    public Start_Event_Type getStartEventType() {
        return startEventType;
    }

    public void setStartEventType(Start_Event_Type startEventType) {
        this.startEventType = startEventType;
    }

    public void setMax_RuntimeDays(int max_RuntimeDays) {
        this.max_RuntimeDays = max_RuntimeDays;
    }

    public void setNumber_Instances_Per_Day(int number_Instances_Per_Day) {
        this.number_Instances_Per_Day = number_Instances_Per_Day;
    }

    //TODO
    //Hier Globale Settings instanzieren

}
