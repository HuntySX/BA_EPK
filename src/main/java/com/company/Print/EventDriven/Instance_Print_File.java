package com.company.Print.EventDriven;

import java.time.LocalTime;
import java.util.List;

public class Instance_Print_File {
    private Integer Instance_ID;
    private LocalTime Timestamp;
    private Integer Day;
    private Workflow_Status Status;
    private Node_Type Type_of_act_Node;
    private Integer Node_ID;
    private String Nodename;
    private List<Print_User> used_User;
    private List<Print_Resources> used_Resources;

    public Instance_Print_File(Integer instance_ID, LocalTime timestamp, Integer day, Workflow_Status status,
                               Node_Type type_of_act_Node, Integer node_ID, String nodename, List<Print_User> used_User,
                               List<Print_Resources> used_Resources) {
        Instance_ID = instance_ID;
        Timestamp = timestamp;
        Day = day;
        Status = status;
        Type_of_act_Node = type_of_act_Node;
        Node_ID = node_ID;
        Nodename = nodename;
        this.used_User = used_User;
        this.used_Resources = used_Resources;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public Integer getInstance_ID() {
        return Instance_ID;
    }

    public void setInstance_ID(Integer instance_ID) {
        Instance_ID = instance_ID;
    }

    public LocalTime getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        Timestamp = timestamp;
    }

    public Workflow_Status getStatus() {
        return Status;
    }

    public void setStatus(Workflow_Status status) {
        Status = status;
    }

    public Node_Type getType_of_act_Node() {
        return Type_of_act_Node;
    }

    public void setType_of_act_Node(Node_Type type_of_act_Node) {
        Type_of_act_Node = type_of_act_Node;
    }

    public Integer getNode_ID() {
        return Node_ID;
    }

    public void setNode_ID(Integer node_ID) {
        Node_ID = node_ID;
    }

    public String getNodename() {
        return Nodename;
    }

    public void setNodename(String nodename) {
        Nodename = nodename;
    }

    public List<Print_User> getUsed_User() {
        return used_User;
    }

    public void setUsed_User(List<Print_User> used_User) {
        this.used_User = used_User;
    }

    public List<Print_Resources> getUsed_Resources() {
        return used_Resources;
    }

    public void setUsed_Resources(List<Print_Resources> used_Resources) {
        this.used_Resources = used_Resources;
    }
}
