package com.company.Print.EventDriven;

import com.company.Enums.Start_Event_Type;

import java.util.List;

public class Print_Start_Event extends Print_Event {

    private Start_Event_Type start_event_type;
    private int to_Instantiate;

    public Print_Start_Event(int ID, List<Connected_Elem_Print> next_Elements, String Tag, Start_Event_Type start_event_type, int to_Instantiate) {
        super(ID, next_Elements, Tag, true, false);
        super.setNode_Type(Node_Type.Start_Event);
        this.start_event_type = start_event_type;
        this.to_Instantiate = to_Instantiate;
    }

    public Start_Event_Type getStart_event_type() {
        return start_event_type;
    }

    public void setStart_event_type(Start_Event_Type start_event_type) {
        this.start_event_type = start_event_type;
    }

    public int getTo_Instantiate() {
        return to_Instantiate;
    }

    public void setTo_Instantiate(int to_Instantiate) {
        this.to_Instantiate = to_Instantiate;
    }
}
