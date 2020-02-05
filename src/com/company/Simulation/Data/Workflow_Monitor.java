package com.company.Simulation.Data;

import com.company.EPK.Node;
import com.company.Enums.Process_Status;

import java.util.ArrayList;
import java.util.List;

public class Workflow_Monitor {
    private List<Node> Elements;
    private List<Process_Status> Process_Status;

    public Workflow_Monitor(List<Node> next_Elem, List<Process_Status> processStatuses) {
        this.Elements = next_Elem;
        this.Process_Status = processStatuses;
    }

    public Workflow_Monitor() {
        this.Elements = new ArrayList<>();
        this.Process_Status = new ArrayList<>();
    }

    public List<Node> get_Elements() {
        return Elements;
    }

    public void set_Elements(List<Node> next_Elem) {
        this.Elements = next_Elem;
    }

    public List<Process_Status> getProcess_Status() {
        return Process_Status;
    }

    public void setProcess_Status(List<Process_Status> process_Status) {
        this.Process_Status = process_Status;
    }

    public void add_Workflow(Node node, Process_Status processStatus) {
        Elements.add(node);
        Process_Status.add(Elements.indexOf(node), processStatus);
    }

    public void change_Workflow_status(Node n, Process_Status processStatus) {
        for (int i = 0; i < Elements.size(); i++) {
            if (Elements.get(i).getID() == n.getID()) {
                Process_Status.set(i, processStatus);
            }
        }
    }
}
