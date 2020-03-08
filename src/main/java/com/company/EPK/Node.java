package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {

    private List<Node> Next_Elem;
    private int ID;

    public Node(List<Node> Next_Elem, int ID) {
        this.ID = ID;
        if (Next_Elem == null) {
            this.Next_Elem = new ArrayList<>();
        } else {
            this.Next_Elem = Next_Elem;
        }
    }

    public Node() {
        this.ID = 1;
        this.Next_Elem = new ArrayList<>();
    }

    public Node(int ID) {
        this.ID = ID;
        this.Next_Elem = new ArrayList<>();
    }

    public List<Node> getNext_Elem() {
        return Next_Elem;
    }

    public void add_Next_Elem(Node n) {
        Next_Elem.add(n);
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

    public void assing_Next_Elements(Simulation_Instance instance) {
        instance.add_Next_Elements(this.Next_Elem);
    }

}
