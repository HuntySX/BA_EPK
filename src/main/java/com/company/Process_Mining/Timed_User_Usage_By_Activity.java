package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Activity;

import java.time.LocalTime;

public class Timed_User_Usage_By_Activity {

    private Mining_Activity Activity;
    private LocalTime Time;
    private Integer day;
    private boolean finishing;

    public Timed_User_Usage_By_Activity(Mining_Activity activity, Integer day, LocalTime time, boolean finishing) {
        Activity = activity;
        Time = time;
        this.day = day;
        this.finishing = finishing;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Mining_Activity getActivity() {
        return Activity;
    }

    public void setActivity(Mining_Activity activity) {
        Activity = activity;
    }

    public LocalTime getTime() {
        return Time;
    }

    public void setTime(LocalTime time) {
        Time = time;
    }

    public boolean isFinishing() {
        return finishing;
    }

    public void setFinishing(boolean finishing) {
        this.finishing = finishing;
    }
}
