package com.company.Process_Mining;

import java.time.Duration;

public class Time_Log_Data {

    private Integer Instance_ID;
    private Duration Delay;
    private Duration Workingtime;

    public Time_Log_Data() {
    }

    public Time_Log_Data(Integer instance_ID, Duration delay, Duration workingtime) {
        Instance_ID = instance_ID;
        Delay = delay;
        Workingtime = workingtime;
    }

    public Integer getInstance_ID() {
        return Instance_ID;
    }

    public void setInstance_ID(Integer instance_ID) {
        Instance_ID = instance_ID;
    }

    public Duration getDelay() {
        return Delay;
    }

    public void setDelay(Duration delay) {
        Delay = delay;
    }

    public Duration getWorkingtime() {
        return Workingtime;
    }

    public void setWorkingtime(Duration workingtime) {
        Workingtime = workingtime;
    }
}
