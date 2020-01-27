package com.company.Simulation.Instance;

import com.company.EPK.Function;
import com.company.EPK.Node;
import com.company.Enums.Status;
import com.company.Simulation.Data.Workflow_Monitor;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.List;
import java.util.concurrent.locks.Lock;

import static com.company.Enums.Status.Starting;

public abstract class Simulation_Instance {

    private Status act_Status;
    private Node Last_Elem;
    private Workflow_Monitor workflowMonitor;
    private List<Function> scheduled_Processes;
    private List<Node> Next_Elem;
    private int case_ID;
    private Lock Instance_lock;

    public Simulation_Instance(int case_ID) {
        this.act_Status = Starting;
        this.Last_Elem = null;
        scheduled_Processes = null;
        this.case_ID = case_ID;
        this.workflowMonitor = new Workflow_Monitor();
    }

    public Status getAct_Status() {
        return act_Status;
    }

    public void setAct_Status(Status act_Status) {
        this.act_Status = act_Status;
    }

    public List<Function> getScheduled_Processes() {
        return scheduled_Processes;
    }

    public void setScheduled_Processes(List<Function> scheduled_Processes) {
        this.scheduled_Processes = scheduled_Processes;
    }

    public int getCase_ID() {
        return case_ID;
    }

    public void setCase_ID(int case_ID) {
        this.case_ID = case_ID;
    }

    public Node getLast_Elem() {
        return Last_Elem;
    }

    public void setLast_Elem(Node last_Elem) {
        Last_Elem = last_Elem;
    }

    public List<Node> getNext_Elem() {
        return Next_Elem;
    }

    public void setNext_Elem(List<Node> next_Elem) {
        Next_Elem = next_Elem;
    }

    public Lock getInstance_lock() {
        return Instance_lock;
    }

    public void add_Next_Elements(List<Node> next_elem) {
        this.Next_Elem.addAll(next_elem);
    }

    public Workflow_Monitor getWorkflowMonitor() {
        return workflowMonitor;
    }

    public void setWorkflowMonitor(Workflow_Monitor workflowMonitors) {
        this.workflowMonitor = workflowMonitors;
    }
}
