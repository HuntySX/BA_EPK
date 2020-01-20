package com.company.EPK;

import java.util.List;

public abstract class Node {

    private List<Node> Next_Elem;
    private int ID;

    public Node(List<Node> Next_Elem, int ID) {
        this.ID = ID;
        this.Next_Elem = Next_Elem;
    }

    public Node() {
        this.ID = 1;
        this.Next_Elem = null;
    }

    public List<Node> getNext_Elem() {
        return Next_Elem;
    }

    public void setNext_Elem(List<Node> next_Elem) {
        Next_Elem = next_Elem;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


}
