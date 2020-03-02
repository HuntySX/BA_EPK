package com.company.EPK;

import com.company.Enums.Contype;

import java.util.List;

public abstract class Connector extends Node {

    private Contype contype;


    public Connector(List<Node> Next_Elem, int ID, Contype contype) {
        super(Next_Elem, ID);
        this.contype = contype;

    }

    public Connector(Contype contype) {
        this.contype = contype;
    }

    public Contype getContype() {
        return contype;
    }

    public void setContype(Contype contype) {
        this.contype = contype;
    }
}
