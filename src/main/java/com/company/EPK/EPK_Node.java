package com.company.EPK;

import com.company.Simulation.Simulation_Base.Data.Shared_Data.Simulation_Instance;

import java.util.ArrayList;
import java.util.List;

public abstract class EPK_Node implements UI_Check_Settings {

    private List<EPK_Node> Next_Elem;
    private int ID;

    public EPK_Node(List<EPK_Node> Next_Elem, int ID) {
        this.ID = ID;
        if (Next_Elem == null) {
            this.Next_Elem = new ArrayList<>();
        } else {
            this.Next_Elem = Next_Elem;
        }
    }

    public EPK_Node() {
        this.ID = 1;
        this.Next_Elem = new ArrayList<>();
    }

    public EPK_Node(int ID) {
        this.ID = ID;
        this.Next_Elem = new ArrayList<>();
    }

    public List<EPK_Node> getNext_Elem() {
        return Next_Elem;
    }

    public void setNext_Elem(List<EPK_Node> next_Elem) {
        Next_Elem = next_Elem;
    }

    public void add_Next_Elem(EPK_Node n) {
        Next_Elem.add(n);
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

    @Override
    public boolean CheckSettings() {
        boolean Check = true;
        if (Next_Elem == null || Next_Elem.isEmpty()) {
            Check = false;
        }
        return Check;
    }
}
