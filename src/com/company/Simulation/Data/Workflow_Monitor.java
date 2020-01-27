package com.company.Simulation.Data;

import com.company.EPK.Node;
import com.company.Enums.Status;

import java.util.List;

public class Workflow_Monitor {
    private List<Node> Elements;
    private List<Status> Status;

    public Workflow_Monitor(List<Node> next_Elem, List<Status> status) {
        this.Elements = next_Elem;
        this.Status = status;
    }

    public Workflow_Monitor() {
        this.Elements = null;
        this.Status = null;
    }

    public List<Node> get_Elements() {
        return Elements;
    }

    public void set_Elements(List<Node> next_Elem) {
        this.Elements = next_Elem;
    }

    public List<Status> getStatus() {
        return Status;
    }

    public void setStatus(List<Status> status) {
        this.Status = status;
    }

    public void add_Workflow(Node node, Status status) {
        Elements.add(node);
        Status.add(Elements.indexOf(node), status);
    }

    public void change_Workflow_status(Node n, Status status) {
        for (int i = 0; i < Elements.size(); i++) {
            if (Elements.get(i).getID() == n.getID()) {
                Status.set(i, status);
            }
        }
    }
}
