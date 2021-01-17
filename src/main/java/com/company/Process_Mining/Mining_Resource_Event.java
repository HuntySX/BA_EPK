package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Resource_Count;

public class Mining_Resource_Event extends Mining_Event {
    private final Mining_Resource_Count Resource_Count;

    public Mining_Resource_Event(int eventTimeFromStart, Mining_Resource_Count resource_Count, int Day) {
        super(eventTimeFromStart, Day);
        Resource_Count = resource_Count;
    }

    public Mining_Resource_Count getResource_Count() {
        return Resource_Count;
    }
}
