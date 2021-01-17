package com.company.Process_Mining;

import com.company.Simulation.Simulation_Base.Data.Discrete_Data.Resource;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Process_Mining_Settings {

    private int Mining_Resolution_in_Seconds;
    private int Days;
    private LocalTime beginTime;
    private LocalTime endTime;
    private List<Resource> Original_Resource_List;
    private List<User> Original_User_List;

    public Process_Mining_Settings() {
        Mining_Resolution_in_Seconds = 300;
        Days = 1;
        beginTime = LocalTime.of(12, 0, 0);
        endTime = LocalTime.of(18, 0, 0);
        Original_Resource_List = new ArrayList<>();
        Original_User_List = new ArrayList<>();
    }

    public int getMining_Resolution_in_Seconds() {
        return Mining_Resolution_in_Seconds;
    }

    public void setMining_Resolution_in_Seconds(int mining_Resolution_in_Seconds) {
        Mining_Resolution_in_Seconds = mining_Resolution_in_Seconds;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
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

    public List<Resource> getOriginal_Resource_List() {
        return Original_Resource_List;
    }

    public void setOriginal_Resource_List(List<Resource> original_Resource_List) {
        Original_Resource_List = original_Resource_List;
    }

    public List<User> getOriginal_User_List() {
        return Original_User_List;
    }

    public void setOriginal_User_List(List<User> original_User_List) {
        Original_User_List = original_User_List;
    }
}
