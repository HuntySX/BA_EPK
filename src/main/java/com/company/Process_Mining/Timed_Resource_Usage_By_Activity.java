package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Activity;

import java.time.LocalTime;

public class Timed_Resource_Usage_By_Activity {

    private Mining_Activity Activity;
    private LocalTime Time;
    private Integer day;
    private Integer Count;

    public Timed_Resource_Usage_By_Activity(Mining_Activity Activity, Integer day, LocalTime time, Integer count) {
        this.Activity = Activity;
        Time = time;
        this.day = day;
        Count = count;
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

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }
}
