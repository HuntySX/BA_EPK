package com.company.Print.EventDriven;

import com.company.Enums.Contype;

import java.util.List;

public class Print_Event_Con_Join extends Print_Event_Driven_File {

    private List<Connected_Node_Map_Print> Mapped_Branched_Elements;
    private List<Connected_Elem_Print> Mapped_Branched_Elements_AND;
    private Contype contype;

    public Print_Event_Con_Join(int ID, List<Connected_Elem_Print> next_Elements, List<Connected_Node_Map_Print> mapped_Branched_Elements, List<Connected_Elem_Print> mapped_Branched_Elements_AND, Contype contype) {
        super(Node_Type.E_Con_Join, ID, next_Elements);
        Mapped_Branched_Elements = mapped_Branched_Elements;
        Mapped_Branched_Elements_AND = mapped_Branched_Elements_AND;
        this.contype = contype;
    }

    public List<Connected_Node_Map_Print> getMapped_Branched_Elements() {
        return Mapped_Branched_Elements;
    }

    public void setMapped_Branched_Elements(List<Connected_Node_Map_Print> mapped_Branched_Elements) {
        Mapped_Branched_Elements = mapped_Branched_Elements;
    }

    public List<Connected_Elem_Print> getMapped_Branched_Elements_AND() {
        return Mapped_Branched_Elements_AND;
    }

    public void setMapped_Branched_Elements_AND(List<Connected_Elem_Print> mapped_Branched_Elements_AND) {
        Mapped_Branched_Elements_AND = mapped_Branched_Elements_AND;
    }

    public Contype getContype() {
        return contype;
    }

    public void setContype(Contype contype) {
        this.contype = contype;
    }
}
