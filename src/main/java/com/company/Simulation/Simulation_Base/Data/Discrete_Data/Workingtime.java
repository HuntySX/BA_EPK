package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

public class Workingtime {

    private int Hours;
    private int Minutes;
    private int Seconds;

    public Workingtime(int hours, int minutes, int seconds) {
        Hours = hours;
        Minutes = minutes;
        Seconds = seconds;
    }

    public Workingtime() {
        Hours = 0;
        Minutes = 0;
        Seconds = 0;
    }

    public int getHours() {
        return Hours;
    }

    public void setHours(int hours) {
        Hours = hours;
    }

    public int getMinutes() {
        return Minutes;
    }

    public void setMinutes(int minutes) {
        Minutes = minutes;
    }

    public int getSeconds() {
        return Seconds;
    }

    public void setSeconds(int seconds) {
        Seconds = seconds;
    }

    public int get_Duration_to_Seconds() {
        return (this.Hours * 3600) + (this.Minutes * 60) + (this.Seconds);
    }
}
