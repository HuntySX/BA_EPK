package com.company.Process_Mining.Base_Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Mining_Instance {

    String Activity_Status;
    private LocalTime Duration;
    private int Instance_ID;
    private List<Mining_Resource_Count> Used_Resources;
    private List<Mining_User> Used_Users;
    private Mining_Activity Activity;

    public Mining_Instance(LocalTime duration, int instance_ID, String activity_Status,
                           List<Mining_Resource_Count> used_Resources, List<Mining_User> used_Users,
                           Mining_Activity activity) {
        Duration = duration;
        Instance_ID = instance_ID;
        Activity_Status = activity_Status;
        Used_Resources = used_Resources;
        Used_Users = used_Users;
        Activity = activity;
    }

    public Mining_Instance(LocalTime duration, int instance_ID, String activity_Status, Mining_Activity activity) {
        Duration = duration;
        Instance_ID = instance_ID;
        Activity_Status = activity_Status;
        Activity = activity;
        Used_Users = new ArrayList<>();
        Used_Resources = new ArrayList<>();
    }

    public LocalTime getDuration() {
        return Duration;
    }

    public void setDuration(LocalTime duration) {
        Duration = duration;
    }

    public int getInstance_ID() {
        return Instance_ID;
    }

    public void setInstance_ID(int instance_ID) {
        Instance_ID = instance_ID;
    }

    public String getActivity_Status() {
        return Activity_Status;
    }

    public void setActivity_Status(String activity_Status) {
        Activity_Status = activity_Status;
    }

    public List<Mining_Resource_Count> getUsed_Resources() {
        return Used_Resources;
    }

    public void setUsed_Resources(List<Mining_Resource_Count> used_Resources) {
        Used_Resources = used_Resources;
    }

    public List<Mining_User> getUsed_Users() {
        return Used_Users;
    }

    public void setUsed_Users(List<Mining_User> used_Users) {
        Used_Users = used_Users;
    }

    public Mining_Activity getActivity() {
        return Activity;
    }

    public void setActivity(Mining_Activity activity) {
        Activity = activity;
    }

    @Override
    public String toString() {
        return "ID=" + Instance_ID +
                ", Activity= " + Activity +
                ", Timestamp= " + Duration +
                ", Activity_Status= " + Activity_Status +
                ", Used_Users=" + Used_Users +
                ", Used_Resources= " + Used_Resources
                ;
    }
}
