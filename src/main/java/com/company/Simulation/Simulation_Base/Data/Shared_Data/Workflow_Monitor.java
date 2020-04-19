package com.company.Simulation.Simulation_Base.Data.Shared_Data;

import com.company.EPK.EPK_Node;
import com.company.Enums.Process_Status;

import java.util.ArrayList;
import java.util.List;

public class Workflow_Monitor {
    private List<EPK_Node> Elements;
    private List<Process_Status> Process_Status;

    public Workflow_Monitor(List<EPK_Node> next_Elem, List<Process_Status> processStatuses) {
        this.Elements = next_Elem;
        this.Process_Status = processStatuses;
    }

    public Workflow_Monitor() {
        this.Elements = new ArrayList<>();
        this.Process_Status = new ArrayList<>();
    }

    public List<EPK_Node> get_Elements() {
        return Elements;
    }

    public void set_Elements(List<EPK_Node> next_Elem) {
        this.Elements = next_Elem;
    }

    public List<Process_Status> getProcess_Status() {
        return Process_Status;
    }

    public void setProcess_Status(List<Process_Status> process_Status) {
        this.Process_Status = process_Status;
    }

    public void add_Workflow(EPK_Node EPKNode, Process_Status processStatus) {
        Elements.add(EPKNode);
        Process_Status.add(Elements.indexOf(EPKNode), processStatus);
    }

    public void change_Workflow_status(EPK_Node n, Process_Status processStatus) {
        for (int i = 0; i < Elements.size(); i++) {
            if (Elements.get(i).getID() == n.getID()) {
                Process_Status.set(i, processStatus);
            }
        }
    }
}
