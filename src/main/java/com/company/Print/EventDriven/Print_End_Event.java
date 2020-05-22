package com.company.Print.EventDriven;

import java.util.List;

public class Print_End_Event extends Print_Event {

    public Print_End_Event(int ID, List<Connected_Elem_Print> next_Elements, String Tag) {
        super(ID, next_Elements, Tag, false, true);
        super.setNode_Type(Node_Type.End_Event);
    }


}
