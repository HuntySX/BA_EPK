package com.company.Print.EventDriven;

import java.util.List;

public abstract class Print_Event_Driven_File {
    private Node_Type Node_Type;
    private int ID;
    private List<Connected_Elem_Print> Next_Elements;

    public Print_Event_Driven_File(Node_Type Nodetype, int ID, List<Connected_Elem_Print> next_Elements) {
        Node_Type = Nodetype;
        this.ID = ID;
        Next_Elements = next_Elements;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Connected_Elem_Print> getNext_Elements() {
        return Next_Elements;
    }

    public void setNext_Elements(List<Connected_Elem_Print> next_Elements) {
        Next_Elements = next_Elements;
    }

    public com.company.Print.EventDriven.Node_Type getNode_Type() {
        return Node_Type;
    }

    public void setNode_Type(com.company.Print.EventDriven.Node_Type node_Type) {
        Node_Type = node_Type;
    }
}
