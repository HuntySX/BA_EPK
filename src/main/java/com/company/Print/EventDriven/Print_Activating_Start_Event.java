package com.company.Print.EventDriven;

import com.company.Enums.Start_Event_Type;

import java.util.List;

public class Print_Activating_Start_Event extends Print_Event {
    private Start_Event_Type start_event_type;
    private Connected_Elem_Print Activating_Function;

    public Print_Activating_Start_Event(int ID, List<Connected_Elem_Print> Next_elements, String event_Tag, boolean is_Start_Event, boolean is_End_Event, Start_Event_Type start_event_type, Connected_Elem_Print activating_Function) {
        super(ID, Next_elements, event_Tag, true, false);
        super.setNode_Type(Node_Type.Activating_Start_Event);
        this.start_event_type = start_event_type;
        Activating_Function = activating_Function;
    }

    public Start_Event_Type getStart_event_type() {
        return start_event_type;
    }

    public void setStart_event_type(Start_Event_Type start_event_type) {
        this.start_event_type = start_event_type;
    }

    public Connected_Elem_Print getActivating_Function() {
        return Activating_Function;
    }

    public void setActivating_Function(Connected_Elem_Print activating_Function) {
        Activating_Function = activating_Function;
    }
}
