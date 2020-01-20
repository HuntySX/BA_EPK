package com.company.EPK;

import java.util.List;

public abstract class Connector extends Node {

    private Contype type;


    public Connector(List<Node> Next_Elem, int ID, Contype type) {
        super(Next_Elem, ID);
        this.type = type;

    }

    public Connector(Contype type) {
        this.type = type;
    }

    public Contype getType() {
        return type;
    }

    public void setType(Contype type) {
        this.type = type;
    }
}
