package com.company.UI.EPKUI;

import com.company.Enums.Option_Event_Choosing;

import java.time.LocalTime;

import static com.company.Enums.Option_Event_Choosing.FIFO;

public class UI_Settings {

    private boolean Only_Start_Finishable_Functions;
    private LocalTime beginTime;
    private LocalTime endTime;
    private Option_Event_Choosing Decide_Event_choosing;
    private boolean Optimal_User_Layout;
    private boolean Print_Only_Function;
    private int max_RuntimeDays;

    public UI_Settings() {
        this.Only_Start_Finishable_Functions = false;
        this.beginTime = LocalTime.of(10, 0, 0, 0);
        this.endTime = LocalTime.of(18, 0, 0, 0);
        Decide_Event_choosing = FIFO;
        Optimal_User_Layout = false;
        Print_Only_Function = true;
        this.max_RuntimeDays = 1;
    }

    public boolean isOnly_Start_Finishable_Functions() {
        return Only_Start_Finishable_Functions;
    }

    public void setOnly_Start_Finishable_Functions(boolean only_Start_Finishable_Functions) {
        Only_Start_Finishable_Functions = only_Start_Finishable_Functions;
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

    public int getMax_RuntimeDays() {
        return max_RuntimeDays;
    }

    public void setMax_RuntimeDays(int max_RuntimeDays) {
        this.max_RuntimeDays = max_RuntimeDays;
    }
}

