package com.company.Process_Mining;

import com.company.Process_Mining.Base_Data.Mining_Activity;

public class Mining_Activity_Event extends Mining_Event {
    private final Mining_Activity Event_Activity;

    public Mining_Activity_Event(int eventTimeFromStart, Mining_Activity Activity, int Day) {
        super(eventTimeFromStart, Day);
        this.Event_Activity = Activity;
    }

    public Mining_Activity getEvent_Activity() {
        return Event_Activity;
    }
}
