package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Simulation_Event_List {
    private List<Event_Instance> Event_List;

    public Simulation_Event_List() {
        Event_List = new ArrayList<>();
    }

    public void addTimedEvent(Event_Instance event) {
        if (Event_List.isEmpty()) {
            Event_List.add(event);
        } else {
            ListIterator iter = Event_List.listIterator();
            while (iter.hasNext()) {
                Event_Instance List_Event = (Event_Instance) iter.next();
                if (List_Event.getTime().isAfter(event.getTime())) {
                    iter.previous();
                    iter.add(event);
                    break;
                }
            }
        }
    }

    public List<Event_Instance> getByTime(LocalTime time) {

        ListIterator iter = Event_List.listIterator();
        List<Event_Instance> result = new ArrayList<>();
        while (iter.hasNext()) {
            Event_Instance List_Event = (Event_Instance) iter.next();
            if (List_Event.getTime().isAfter(time)) {
                break;
            } else {
                result.add(List_Event);
            }
        }
        return result;
    }

    public boolean TimeEquals(LocalTime event_Time, LocalTime time) {
        return event_Time.getHour() == time.getHour() && event_Time.getMinute() == time.getMinute() && event_Time.getSecond() == time.getSecond();
    }

//    public boolean TimeGreaterThan(LocalTime event_Time, LocalTime time) {
//        return event_Time.getHour() > time.getHour() || event_Time.getMinute() > time.getMinute() || event_Time.getSecond() > time.getSecond();
//    }
}
