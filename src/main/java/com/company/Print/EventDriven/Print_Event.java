package com.company.Print.EventDriven;

import java.util.List;

public class Print_Event extends Print_Event_Driven_File {

    private String Event_Tag;
    private boolean is_Start_Event;
    private boolean is_End_Event;

    public Print_Event(int ID, List<Connected_Elem_Print> Next_elements, String event_Tag, boolean is_Start_Event, boolean is_End_Event) {
        super(Node_Type.Event, ID, Next_elements);
        Event_Tag = event_Tag;
        this.is_Start_Event = is_Start_Event;
        this.is_End_Event = is_End_Event;
    }

    public String getEvent_Tag() {
        return Event_Tag;
    }

    public void setEvent_Tag(String event_Tag) {
        Event_Tag = event_Tag;
    }

    public boolean isIs_Start_Event() {
        return is_Start_Event;
    }

    public void setIs_Start_Event(boolean is_Start_Event) {
        this.is_Start_Event = is_Start_Event;
    }

    public boolean isIs_End_Event() {
        return is_End_Event;
    }

    public void setIs_End_Event(boolean is_End_Event) {
        this.is_End_Event = is_End_Event;
    }
}