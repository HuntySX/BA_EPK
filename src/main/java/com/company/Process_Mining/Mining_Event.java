package com.company.Process_Mining;

public abstract class Mining_Event {
    private int EventTimeFromStart;
    private int Day;

    public Mining_Event(int eventTimeFromStart, int eventDay) {
        Day = eventDay;
        EventTimeFromStart = eventTimeFromStart;
    }

    public int getEventTimeFromStart() {
        return EventTimeFromStart;
    }

    public void setEventTimeFromStart(int eventTimeFromStart) {
        EventTimeFromStart = eventTimeFromStart;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }
}
