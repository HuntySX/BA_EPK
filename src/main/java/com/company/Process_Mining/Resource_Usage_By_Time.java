package com.company.Process_Mining;

import java.time.LocalTime;

public class Resource_Usage_By_Time {

    LocalTime Time;
    Integer Count;

    public Resource_Usage_By_Time(LocalTime time, Integer count) {
        Time = time;
        Count = count;
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
