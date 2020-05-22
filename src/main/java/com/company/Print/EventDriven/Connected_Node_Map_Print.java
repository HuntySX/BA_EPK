package com.company.Print.EventDriven;

public class Connected_Node_Map_Print {
    private Connected_Elem_Print Start;
    private Connected_Elem_Print End;

    public Connected_Node_Map_Print(Connected_Elem_Print start, Connected_Elem_Print end) {
        Start = start;
        End = end;
    }

    public Connected_Elem_Print getStart() {
        return Start;
    }

    public void setStart(Connected_Elem_Print start) {
        Start = start;
    }

    public Connected_Elem_Print getEnd() {
        return End;
    }

    public void setEnd(Connected_Elem_Print end) {
        End = end;
    }
}
