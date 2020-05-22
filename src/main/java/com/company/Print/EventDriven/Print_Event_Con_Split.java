package com.company.Print.EventDriven;

import com.company.Enums.Contype;
import com.company.Enums.Split_Decide_Type;

import java.util.List;

public class Print_Event_Con_Split extends Print_Event_Driven_File {

    private Split_Decide_Type Decide_Type;
    private boolean is_Event_Driven;
    private Contype contype;

    public Print_Event_Con_Split(int ID, List<Connected_Elem_Print> next_Elements, Split_Decide_Type decide_Type, boolean is_Event_Driven, Contype contype) {
        super(Node_Type.E_Con_Split, ID, next_Elements);
        Decide_Type = decide_Type;
        this.is_Event_Driven = is_Event_Driven;
        this.contype = contype;
    }

    public Split_Decide_Type getDecide_Type() {
        return Decide_Type;
    }

    public void setDecide_Type(Split_Decide_Type decide_Type) {
        Decide_Type = decide_Type;
    }

    public boolean isIs_Event_Driven() {
        return is_Event_Driven;
    }

    public void setIs_Event_Driven(boolean is_Event_Driven) {
        this.is_Event_Driven = is_Event_Driven;
    }

    public Contype getContype() {
        return contype;
    }

    public void setContype(Contype contype) {
        this.contype = contype;
    }
}
