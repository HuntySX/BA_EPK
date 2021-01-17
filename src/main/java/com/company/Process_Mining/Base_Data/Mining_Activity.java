package com.company.Process_Mining.Base_Data;

public class Mining_Activity {

    private String Activity_Name;
    private String Type_of_Activity;
    private int Node_ID;

    public Mining_Activity(String activity_Name, String type_of_Activity, int node_ID) {
        Activity_Name = activity_Name;
        Type_of_Activity = type_of_Activity;
        Node_ID = node_ID;
    }

    public String getActivity_Name() {
        return Activity_Name;
    }

    public void setActivity_Name(String activity_Name) {
        Activity_Name = activity_Name;
    }

    public String getType_of_Activity() {
        return Type_of_Activity;
    }


    public void setType_of_Activity(String type_of_Activity) {
        Type_of_Activity = type_of_Activity;
    }

    public int getNode_ID() {
        return Node_ID;
    }

    public void setNode_ID(int node_ID) {
        Node_ID = node_ID;
    }

    @Override
    public String toString() {
        return "Node: " + Node_ID +
                "| " + Activity_Name +
                ", Type: " + Type_of_Activity;
    }
}
