package com.company.EPK;

public class Nodemap {

    private Node Started_Elem;
    private Node Finished_Elem;

    public Nodemap(Node started, Node finished) {
        this.Started_Elem = started;
        this.Finished_Elem = finished;
    }

    public Node getStarted_Elem() {
        return Started_Elem;
    }

    public void setStarted_Elem(Node started_Elem) {
        Started_Elem = started_Elem;
    }

    public Node getFinished_Elem() {
        return Finished_Elem;
    }

    public void setFinished_Elem(Node finished_Elem) {
        Finished_Elem = finished_Elem;
    }
}
