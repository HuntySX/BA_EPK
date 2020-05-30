package com.company.Simulation.Simulation_Base.Data.Discrete_Data;

import com.company.EPK.EPK_Node;
import com.company.Simulation.Simulation_Base.Data.Shared_Data.User;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Instance_Workflow {

    private Event_Instance Instance;
    private LocalTime to_Start;
    private EPK_Node EPKNode;
    private List<User> Active_User;
    private List<Resource> Active_Resource;
    private boolean Working;
    private boolean Waiting;
    private int waiting_Ticket;

    public Instance_Workflow(Event_Instance instance, LocalTime to_Start, EPK_Node EPKNode) {
        Instance = instance;
        this.to_Start = to_Start;
        this.EPKNode = EPKNode;
        this.Active_Resource = new ArrayList<>();
        this.Active_User = new ArrayList<>();
        Waiting = false;
        Working = false;
    }

    public Instance_Workflow(Event_Instance instance, LocalTime to_Start, EPK_Node EPKNode, boolean working) {
        Instance = instance;
        this.to_Start = to_Start;
        this.EPKNode = EPKNode;
        this.Active_Resource = new ArrayList<>();
        this.Active_User = new ArrayList<>();
        Waiting = false;
        Working = working;
    }

    public boolean Is_Waiting() {
        return Waiting;
    }

    public void setIs_Waiting(boolean is_Waiting) {
        this.Waiting = is_Waiting;
    }

    public int getWaiting_Ticket() {
        return waiting_Ticket;
    }

    public void setWaiting_Ticket(int waiting_Ticket) {
        this.waiting_Ticket = waiting_Ticket;
    }

    public Event_Instance getInstance() {
        return Instance;
    }

    public LocalTime getTo_Start() {
        return to_Start;
    }

    public EPK_Node getEPKNode() {
        return EPKNode;
    }

    public void addActiveUser(User u) {
        if (!Active_User.contains(u)) {
            Active_User.add(u);
        }
    }

    public void addActiveResource(Resource res) {
        if (!Active_Resource.contains(res)) {
            Active_Resource.add(res);
        }
    }

    public void setTo_Start(LocalTime to_Start) {
        this.to_Start = to_Start;
    }

    public void removeActiveUser(User u) {
        Active_User.remove(u);
    }

    public void removeActiveResource(Resource res) {
        //TODO Stückzahl rückgeben.
        Active_Resource.remove(res);
    }

    public void Add_Active_Users(List<User> Users) {
        Active_User.addAll(Users);
    }

    public void Add_Active_Resources(List<Resource> Resources) {
        Active_Resource.addAll(Resources);
    }

    public List<User> getActive_User() {
        return Active_User;
    }

    public List<Resource> getActive_Resource() {
        return Active_Resource;
    }

    public boolean isWorking() {
        return Working;
    }

    public void setWorking(boolean working) {
        Working = working;
    }

    @Override
    public String toString() {
        return "Instance_Workflow{" +
                "to_Start=" + to_Start +
                ", node=" + EPKNode +
                '}';
    }
}

