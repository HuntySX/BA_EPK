package com.company.Process_Mining.Base_Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Activity_Time {
    private Mining_Activity Activity;
    private List<LocalTime> Time;

    public Activity_Time() {
        Activity = null;
        Time = new ArrayList<>();
    }

    public Activity_Time(Mining_Activity activity, List<LocalTime> time) {
        Activity = activity;
        Time = time;
    }

    public Activity_Time(Mining_Activity activity) {
        Activity = activity;
    }

    public Mining_Activity getActivity() {
        return Activity;
    }

    public void setActivity(Mining_Activity activity) {
        Activity = activity;
    }

    public List<LocalTime> getTime() {
        return Time;
    }

    public void setTime(List<LocalTime> time) {
        Time = time;
    }
}
