package com.company.Process_Mining.Base_Data;

import com.company.Process_Mining.Time_Log_Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Complete_Time_Activity {
    private Duration Complete_Delay;
    private Duration Complete_WorkingTime;
    private Duration Complete_Time;
    private List<Time_Log_Data> Single_Instance_Activity_Time;

    public Complete_Time_Activity() {
        Complete_Delay = Duration.ZERO;
        Complete_WorkingTime = Duration.ZERO;
        Complete_Time = Duration.ZERO;
        Single_Instance_Activity_Time = new ArrayList<>();
    }

    public Duration getComplete_Delay() {
        return Complete_Delay;
    }

    public void setComplete_Delay(Duration complete_Delay) {
        Complete_Delay = complete_Delay;
    }

    public Duration getComplete_WorkingTime() {
        return Complete_WorkingTime;
    }

    public void setComplete_WorkingTime(Duration complete_WorkingTime) {
        Complete_WorkingTime = complete_WorkingTime;
    }

    public Duration getComplete_Time() {
        return Complete_Time;
    }

    public void setComplete_Time(Duration complete_Time) {
        Complete_Time = complete_Time;
    }

    public List<Time_Log_Data> getSingle_Instance_Activity_Time() {
        return Single_Instance_Activity_Time;
    }

    public void setSingle_Instance_Activity_Time(List<Time_Log_Data> single_Instance_Activity_Time) {
        Single_Instance_Activity_Time = single_Instance_Activity_Time;
    }
}
