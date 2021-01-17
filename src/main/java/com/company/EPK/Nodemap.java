package com.company.EPK;

public class Nodemap {

    private EPK_Node Started_Elem;
    private EPK_Node Finished_Elem;

    public Nodemap(EPK_Node started, EPK_Node finished) {
        this.Started_Elem = started;
        this.Finished_Elem = finished;
    }

    public boolean contains(EPK_Node node) {
        return Started_Elem.getID() == node.getID() || Finished_Elem.getID() == node.getID();
    }

    public boolean containsboth(EPK_Node start, EPK_Node end) {
        return Started_Elem.getID() == start.getID() && Finished_Elem.getID() == end.getID();
    }

    public EPK_Node getStarted_Elem() {
        return Started_Elem;
    }

    public void setStarted_Elem(EPK_Node started_Elem) {
        Started_Elem = started_Elem;
    }

    public EPK_Node getFinished_Elem() {
        return Finished_Elem;
    }

    public void setFinished_Elem(EPK_Node finished_Elem) {
        Finished_Elem = finished_Elem;
    }

    @Override
    public String toString() {
        return
                "[Started_Elem: " + Started_Elem +
                        "/ Finished_Elem: " + Finished_Elem +
                        ']';
    }
}
